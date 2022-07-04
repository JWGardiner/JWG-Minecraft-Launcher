import javax.swing.JFrame
import javax.swing.UIManager

fun settings(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    JFrame().also { settingsMenu ->
        settingsMenu.title = "Settings"
        settingsMenu.setSize(300, 500)
        settingsMenu.layout = null
        settingsMenu.setLocationRelativeTo(null)
        settingsMenu.isVisible = visible
        settingsMenu.isResizable = false;

    }

}