import com.jwg.Main
import com.jwg.jwgapi.errorHandler
import com.jwg.jwgapi.logger
import com.jwg.jwgapi.parseVersion
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import javax.swing.*


fun profiles(visible: Boolean) {
    //Default folder launcher/profiles/

    //TODO LIST:
    //TODO: Make users able to change the default profile folder
    //TODO: Let users have custom icons for their profiles

    //New profile options
    var gameType: String
    var gameName: String
    var gameVer: String

    //Configurable options
    val gameTypes = "launcher/templates"
    val modloaders = "launcher/modloaders"
    val profiles = "launcher/profiles"

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val listOfGameTypes: Array<out File>? = File(modloaders).listFiles(); var j = 0; var k = 0
    for (i in listOfGameTypes!!) {
        j += 1
    }
    val listOfGameTypesF = arrayOfNulls<String>(j)
    while (k < j) {
        listOfGameTypesF[k] = listOfGameTypes[k].name
        k += 1
    }

    val confirm = JButton("Confirm")
    val name = JTextField("Name")

    val versionScrollPane = JScrollPane()
    val versionsModel = DefaultListModel<String>()
    val versionList = JList(versionsModel)
    versionScrollPane.setViewportView(versionList)
    versionScrollPane.setBounds(160, 55, 140, 305)
    versionList.setBounds(160, 55, 140, 305)

    val gameSoftwareScrollPane = JScrollPane()
    val gameSoftwareModel = DefaultListModel<String>()
    val gameSoftwareList = JList(gameSoftwareModel)
    gameSoftwareList.setListData(listOfGameTypesF)
    gameSoftwareScrollPane.setViewportView(gameSoftwareList)
    gameSoftwareScrollPane.setBounds(10, 55, 140, 305)
    gameSoftwareList.setBounds(10, 55, 140, 305)

    confirm.setBounds(10, 10, 139, 35)
    name.setBounds(160, 10, 139, 35)

    JFrame().also { profileMenu ->
        profileMenu.title = "Profiles"
        profileMenu.setSize(310, 400)
        profileMenu.layout = null
        profileMenu.setLocationRelativeTo(null)
        profileMenu.isVisible = visible
        profileMenu.isResizable = false

        profileMenu.add(confirm)
        profileMenu.add(name)
        profileMenu.add(gameSoftwareScrollPane)
        profileMenu.add(versionScrollPane)

        val gsMouseListener: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                gameType = gameSoftwareList.selectedValue.toString()

                val listOfVersionTypes: Array<out File>? = File("$gameTypes/").listFiles(); j = 0; k = 0
                for (i in listOfVersionTypes!!) {
                    j += 1
                }
                val listOfVersionTypesF = arrayOfNulls<String>(j)
                while (k < j) {
                    listOfVersionTypesF[k] = listOfVersionTypes[k].name
                    k += 1
                }

                versionList.setListData(listOfVersionTypesF)
                versionList.updateUI()

            }
        }
        gameSoftwareList.addMouseListener(gsMouseListener)
        val verMouseListener: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                gameVer = versionList.selectedValue.toString()
            }
        }
        confirm.addActionListener {
            val profileName = name.text
            val gameSoftware = gameSoftwareList.selectedValue.toString()
            try {
                val path = Paths.get("$profiles/$profileName/client")
                Files.createDirectories(path)
                logger.log(
                    Main.logFile,
                    parseVersion.versionInt(Main.version),
                    Main.project,
                    0,
                    "Created a new profile directory."
                )
            } catch (e: IOException) {
                errorHandler.handleError(
                    "$e Could not create directory. Try going into the folder and removing everything except jar files.",
                    "JWG MC Init",
                    parseVersion.versionInt(
                        Main.version
                    ),
                    Main.logFile
                )
                System.exit(0)
            }
            val creationTime = LocalDate.now().toString()
            gameVer = versionList.selectedValue.toString()
            val profileInfo = File("$profiles/$profileName/profile.cfg")
            profileInfo.createNewFile()
            try {
                val configWriter = FileWriter("$profiles/$profileName/profile.cfg")
                configWriter.write("Name = $profileName\nVersion = $gameVer\nSoftware = $gameSoftware\nCreated = $creationTime")
                configWriter.close()
            } catch (_: IOException) {}
            try {
                val url: URL = when (gameSoftware) {
                    "Fabric" -> {
                        //Download fabric icon
                        URL("https://i.imgur.com/CqLSMEQ.png")
                    }
                    "Forge" -> {
                        //Download forge icon
                        URL("https://files.minecraftforge.net/static/images/apple-touch-icon.png")
                    }
                    "Quilt" -> {
                        //Download quilt icon
                        URL("https://github.com/QuiltMC/art/raw/master/brand/1024png/quilt_logo_transparent.png")
                    }
                    else -> {
                        //Download pack.png
                        URL("https://packpng.com/static/pack.png")
                    }
                }
                val rbc: ReadableByteChannel = Channels.newChannel(url.openStream())
                val fos = FileOutputStream("$profiles/$profileName/icon.png")
                fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
            } catch (_: IOException){ }
            Files.copy(Path.of("$gameTypes/$gameVer/client.jar"), Path.of("$profiles/$profileName/client/client.jar"), StandardCopyOption.REPLACE_EXISTING)
            JOptionPane.showMessageDialog(null,
                "Created new profile for $gameVer, $gameSoftware.",
                "Created Profile",
                JOptionPane.PLAIN_MESSAGE)
        }

    }
}



