import com.jwg.Main.version
import com.jwg.jwgapi.readFile.fileReadLine
import com.jwg.launcher.contributorlist
import com.jwg.launcher.removeProfile
import com.jwg.launcher.uploadProfile
import java.awt.Color
import java.awt.Image
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.*


fun scanFiles(dir: File): Array<String?> {
    val listOfFiles: Array<File> = dir.listFiles()!!
    var j = 0
    var k = 0
    for (i in listOfFiles.indices) {
        j += 1
    }
    val listOfFileNames = arrayOfNulls<String>(j)
    while (k < j) {
        listOfFileNames[k] = listOfFiles[k].name
        k += 1
    }

    return listOfFileNames
}
fun getConfig(cfg: String): Array<String> {
    val name: String
    val version: String
    val software: String
    val created: String
    if (cfg == "None") {
        name = "Name: "
        version = "Version: "
        software = "Software: "
        created = "Created: "
    } else {
        name = fileReadLine(cfg, 0).replace(" = ", ": ")
        version = fileReadLine(cfg, 1).replace(" = ", ": ")
        software = fileReadLine(cfg, 2).replace(" = ", ": ")
        created = fileReadLine(cfg, 3).replace(" = ", ": ")
    }
    return arrayOf(name, version, software, created)
}
fun mainMenu() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val profileFolder = "launcher/profiles"

    val isLoggedIn = false
    val username = "JWG_"
    var loginText = "\t"

    val profileList = JList(scanFiles(File(profileFolder)))
    val profileCFG = JList(getConfig("None"))
    val infoText = JLabel("JWG Minecraft Launcher: Version $version")


    val contributors = JButton("Contributors")
    val settings = JButton("⚙️")
    val refresh = JButton("\uD83D\uDD04")
    val profiles = JButton("➕")
    val rmProfile = JButton("➖")
    val uploadProfile = JButton("⬆️")


    if (!isLoggedIn) {
        loginText = "login"
    }
    val login = JButton(loginText)
    JFrame().also { window ->

        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.title = "JWG Minecraft Launcher"
        window.setSize(1200, 700)
        window.layout = null
        val icon = JLabel()
        icon.setBounds(335, 190, 245, 245)
        var profIco: ImageIcon
        window.setLocationRelativeTo(null)
        window.add(icon)


        window.isResizable = false
        profileList.setBounds(55, 75,245,550)
        profileCFG.setBounds(335, 75,245,100)

        settings.setBounds(0,0,40,40)
        refresh.setBounds(40,0,40,40)
        profiles.setBounds(80,0,40,40)
        rmProfile.setBounds(120,0,40,40)
        uploadProfile.setBounds(160,0,40,40)

        login.setBounds(1050,0,150,40)
        contributors.setBounds(900,0,150,40)
        infoText.setBounds(15, 625, 350, 40)
        window.add(infoText)
        window.add(profileCFG)

        window.add(settings)
        window.add(refresh)
        window.add(profiles)
        window.add(rmProfile)
        window.add(uploadProfile)

        window.add(contributors)
        window.add(login)
        window.add(profileList)

        settings.foreground = Color.WHITE
        refresh.foreground = Color.WHITE
        profiles.foreground = Color.WHITE
        rmProfile.foreground = Color.WHITE
        uploadProfile.foreground = Color.WHITE
        contributors.foreground = Color.WHITE
        login.foreground = Color.WHITE
        profileList.foreground = Color.WHITE

        window.isVisible = true

        settings.addActionListener {
            settings(visible = true)
            window.run { repaint() }
        }
        refresh.addActionListener {
            profileList.setListData(scanFiles(File(profileFolder)))
            window.run { repaint() }

        }

        profiles.addActionListener {
            profiles(visible = true)
            window.run { repaint() }
        }
        rmProfile.addActionListener {
            removeProfile(visible = true)
            window.run { repaint() }
        }
        uploadProfile.addActionListener {
            uploadProfile()
            window.run { repaint() }
        }

        contributors.addActionListener {
            contributorlist(visible = true)
            window.run { repaint() }
        }

        val profileListMouseListener: MouseListener = object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                if (profileList.selectedValue == null) return

                val profile = profileList.selectedValue.toString()
                profileCFG.setListData(getConfig("launcher/profiles/$profile/profile.cfg"))
                profIco = ImageIcon(ImageIcon("launcher/profiles/$profile/icon.png").image.getScaledInstance(245, 245, Image.SCALE_SMOOTH))
                icon.icon = profIco
                profileCFG.updateUI()
            }
        }
        profileList.addMouseListener(profileListMouseListener);
    }


}