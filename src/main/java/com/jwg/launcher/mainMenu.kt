import com.jwg.launcher.contributorlist
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
fun mainMenu() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val profileFolder = "launcher/profiles"

    val isLoggedIn = false
    val username = "JWG_"
    var loginText = "\t"

    val profileList = JList(scanFiles(File(profileFolder)))
    val contributors = JButton("Contributors")
    val settings = JButton("⚙️")
    val profiles = JButton("+")

    if (!isLoggedIn) {
        loginText = "login"
    }
    val login = JButton(loginText)
    JFrame().also { window ->
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.title = "JWG Minecraft Launcher"
        window.setSize(1200, 700)
        window.layout = null
        window.setLocationRelativeTo(null)

        window.isResizable = false
        profileList.setBounds(55, 55,1090,570)
        settings.setBounds(0,0,40,40)
        profiles.setBounds(40,0,40,40)
        login.setBounds(1050,0,150,40)
        contributors.setBounds(900,0,150,40)
        window.add(settings)
        window.add(profiles)
        window.add(contributors)
        window.add(login)
        window.add(profileList)
        window.isVisible = true

        settings.addActionListener {
            settings(visible = true)
            window.run { repaint() }
        }
        profiles.addActionListener {
            profiles(visible = true)
            window.run { repaint() }
        }
        contributors.addActionListener {
            contributorlist(visible = true)
            window.run { repaint() }
        }
    }


}