import com.jwg.jwgapi.readFile.fileReadLine
import com.jwg.jwgapi.writeFile.overwriteFile
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JTextField
import javax.swing.UIManager


fun settings(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    var isCracked = false
    var customJVM = false
    var startPopupDisabled = false

    val saveToFile = JButton("Save to file")

    val crackedTF = JButton("Is Cracked? False")
    val crackedUsernameINP = JTextField(fileReadLine("settings.cfg", 3).substring(11), 13)

    val minRamAlloc = JTextField(fileReadLine("settings.cfg", 5).substring(12), 15)
    val maxRamAlloc = JTextField(fileReadLine("settings.cfg", 4).substring(12), 15)
    val jrePath = JTextField(System.getProperty("java.home"), 15)
    val enableCustomJREargs = JButton("Custom Args? False")
    val customJREargs = JTextField(fileReadLine("settings.cfg", 7).substring(11), 15)
    val startPopupDisable = JButton("Update Popup? True")

    JFrame().also { settingsMenu ->
        settingsMenu.title = "Settings"
        settingsMenu.setSize(300, 500)
        settingsMenu.layout = null
        settingsMenu.setLocationRelativeTo(null)
        settingsMenu.isVisible = visible
        settingsMenu.isResizable = false

        settingsMenu.add(saveToFile)
        settingsMenu.add(crackedTF)
        settingsMenu.add(crackedUsernameINP)
        settingsMenu.add(minRamAlloc)
        settingsMenu.add(maxRamAlloc)
        settingsMenu.add(jrePath)
        settingsMenu.add(enableCustomJREargs)
        settingsMenu.add(customJREargs)
        settingsMenu.add(startPopupDisable)

        saveToFile.setBounds(150, 435, 150, 35)

        //Cracked Settings
        crackedUsernameINP.setBounds(10,10,150,35)
        crackedTF.setBounds(9,45,152,35)

        //JVM settings
        minRamAlloc.setBounds(10,90,150,35)
        maxRamAlloc.setBounds(10,125,150,35)
        jrePath.setBounds(10,160,150,35)
        enableCustomJREargs.setBounds(9,195,152,35)
        customJREargs.setBounds(10,230,150,35)

        //Disable start popup
        startPopupDisable.setBounds(9,275,152,35)

        saveToFile.addActionListener {
            val crackedIGN = crackedUsernameINP.text
            val minRamAllocated = minRamAlloc.text
            val maxRamAllocated = maxRamAlloc.text
            val customjrePath = jrePath.text
            val customArgs = customJREargs.text
            overwriteFile(
                "settings.cfg",
                "startPopup=$startPopupDisabled\ncracked=$isCracked\nenableCustomJVM=$customJVM\ncrackedIGN=$crackedIGN\nminRamAlloc=$minRamAllocated\nmaxRamAlloc=$maxRamAllocated\njrePath=$customjrePath\ncustomArgs=$customArgs"
            )
            settingsMenu.run { repaint() }
        }
        crackedTF.addActionListener {
            isCracked = isCracked == false
            if (isCracked) {
                crackedTF.text = "Is Cracked? True"
            } else {
                crackedTF.text = "Is Cracked? False"
            }
            settingsMenu.run { repaint() }

        }
        enableCustomJREargs.addActionListener {
            customJVM = customJVM == false
            if (customJVM) {
                enableCustomJREargs.text = "Custom Args? True"
            } else {
                enableCustomJREargs.text = "Custom Args? False"
            }
            settingsMenu.run { repaint() }

        }
        startPopupDisable.addActionListener {
            startPopupDisabled = startPopupDisabled == false
            if (startPopupDisabled) {
                startPopupDisable.text = "Update Popup? True"
            } else {
                startPopupDisable.text = "Update Popup? False"
            }
            settingsMenu.run { repaint() }

        }

    }



}

