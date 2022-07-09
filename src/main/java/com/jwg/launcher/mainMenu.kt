import com.jwg.launcher.contributorlist
import javax.swing.*


fun mainMenu() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val isLoggedIn = false

    val username = "JWG_"

    var loginText = "\t";

    val contributors = JButton("Contributors")
    val settings = JButton("⚙️")
    val profiles = JButton("+")

    loginText = if (isLoggedIn){
        username
    } else {
        "Login"
    }
    val login = JButton(loginText)
    JFrame().also { window ->
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.title = "JWG Minecraft Launcher"
        window.setSize(1200, 700)
        window.layout = null
        window.setLocationRelativeTo(null)

        window.isResizable = false

        settings.setBounds(0,0,35,35)
        profiles.setBounds(35,0,35,35)
        login.setBounds(1050,0,150,35)
        contributors.setBounds(900,0,150,35)
        window.add(settings)
        window.add(profiles)
        window.add(contributors)
        window.add(login)
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