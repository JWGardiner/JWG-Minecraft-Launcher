import com.jwg.Main.*
import com.jwg.jwgapi.logger.log
import com.jwg.jwgapi.parseVersion.versionInt
import com.jwg.jwgapi.writeFile.overwriteFile
import com.jwg.launcher.getInstallations
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import javax.swing.*

fun profiles(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val add = JButton("+")
    val remove = JButton("-")
    val refresh = JButton("⟳")

    var installs = getInstallations.getAllInstalls()
    var profileList = JList(installs)

    JFrame().also { profileMenu ->
        profileMenu.title = "Profiles"
        profileMenu.setSize(300, 500)
        profileMenu.layout = null
        profileMenu.setLocationRelativeTo(null)
        profileMenu.isVisible = visible
        profileMenu.isResizable = false

        profileMenu.add(add)
        profileMenu.add(remove)
        profileMenu.add(
            refresh
        )

        add.setBounds(0, 0, 45, 35)
        remove.setBounds(45, 0, 45, 35)
        refresh.setBounds(90, 0, 45, 35)

        add.addActionListener {
            val installationName: String = JOptionPane.showInputDialog("Installation Name:")
            val installationVersion: String = JOptionPane.showInputDialog("Installation Version (e.g fabric/1.19.1):")
            val installationFolder = File("launcher/profiles/$installationName/client")
            if (!installationFolder.exists()) {
                installationFolder.mkdirs()
                log(logFile, versionInt(version), project, 0, "Created new installation \"$installationName\"")
            } else {
                log(
                    logFile,
                    versionInt(version),
                    project,
                    1,
                    "Unable to create installation \"$installationName\"; It already exists."
                )
            }
            Files.copy(
                Path.of("launcher/templates/$installationVersion/client.jar"),
                Path.of("launcher/profiles/$installationName/client/client.jar"),
                StandardCopyOption.REPLACE_EXISTING
            )
            try {
                val config = File("launcher/profiles/$installationName/installation.cfg")
                if (config.createNewFile()) {
                    log(logFile, versionInt(version), project, 0, "Created installation config.")
                }
            } catch (_: IOException) {
            }
            overwriteFile("launcher/profiles/$installationName/installation.cfg", "isFavourite=false")


            profileMenu.run { repaint() }

        }
        remove.addActionListener() {
            val installationName: String = JOptionPane.showInputDialog("Installation Name:")
            Files.walk(Path.of("launcher/profiles/$installationName"))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        }
        refresh.addActionListener() {
            profiles(visible = true)
            profileMenu.isVisible = false
        }

        profileMenu.add(profileList)
        profileList.setBounds(10, 45, 280, 415)

        profileList.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    val row: Int = profileList.locationToIndex(e.point)
                    profileList.selectedIndex = row

                    val toRun = installs[profileList.selectedIndex]
                    //TODO: Launch the game
                }
            }
        })
    }
}



