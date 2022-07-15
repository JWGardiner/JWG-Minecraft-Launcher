package com.jwg.launcher

import com.jwg.jwgapi.readFile.fileReadLine
import com.jwg.jwgapi.writeFile
import java.awt.Color
import java.io.File
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.UIManager


fun settings(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val settingsFile = File("settings.cfg")
    var startPopup = fileReadLine("settings.cfg", 0).substring(11)
    var cracked = fileReadLine("settings.cfg", 1).substring(8)
    var enableCustomJVM = fileReadLine("settings.cfg", 2).substring(16)
    var crackedIGN = fileReadLine("settings.cfg", 3).substring(11)
    var minRamAlloc = fileReadLine("settings.cfg", 4).substring(12)
    var maxRamAlloc = fileReadLine("settings.cfg", 5).substring(12)
    var jrePath = fileReadLine("settings.cfg", 6).substring(8)
    var customArgs = fileReadLine("settings.cfg", 7).substring(11)

    val resetSettings = JButton("Reload Settings")
    val saveSettings = JButton("Save to file")

    val startPopupT = JLabel("Start Popup: ")
    val crackedT = JLabel("Cracked: ")
    val enableCustomJVMT = JLabel("Custom JVM Args: ")
    val crackedIGNT = JLabel("Cracked Name: ")
    val minRamAllocT = JLabel("Min Ram Allocated: ")
    val maxRamAllocT = JLabel("Max Ram Allocated: ")
    val jrePathT = JLabel("JRE Path: ")
    val customArgsT = JLabel("Custom Args: ")

    val startPopupI = JButton(startPopup)
    val crackedI = JButton(cracked)
    val enableCustomJVMI = JButton(enableCustomJVM)
    val crackedIGNI = JTextField(crackedIGN)
    val minRamAllocI = JTextField(minRamAlloc)
    val maxRamAllocI = JTextField(maxRamAlloc)
    val jrePathI = JTextField(jrePath)
    val customArgsI = JTextField(customArgs)

    JFrame().also { settingsMenu ->
        resetSettings.setBounds(10, 10, 125, 35)
        saveSettings.setBounds(10, 55, 125, 35)

        startPopupT.setBounds(10, 100, 125, 35)
        crackedT.setBounds(10, 135, 125, 35)
        enableCustomJVMT.setBounds(10, 170, 125, 35)
        crackedIGNT.setBounds(10, 205, 125, 35)
        minRamAllocT.setBounds(10, 240, 150, 35)
        maxRamAllocT.setBounds(10, 275, 150, 35)
        jrePathT.setBounds(10, 310, 125, 35)
        customArgsT.setBounds(10, 345, 125, 35)

        startPopupI.setBounds(169, 100, 127, 33)
        crackedI.setBounds(169, 135, 127, 33)
        enableCustomJVMI.setBounds(169, 170, 127, 33)
        crackedIGNI.setBounds(170, 205, 125, 30)
        minRamAllocI.setBounds(170, 240, 125, 30)
        maxRamAllocI.setBounds(170, 275, 125, 30)
        jrePathI.setBounds(170, 310, 125, 30)
        customArgsI.setBounds(170, 345, 125, 30)

        settingsMenu.title = "Settings"
        settingsMenu.setSize(310, 420)
        settingsMenu.layout = null
        settingsMenu.setLocationRelativeTo(null)
        settingsMenu.isVisible = visible
        settingsMenu.isResizable = false

        settingsMenu.add(resetSettings)
        settingsMenu.add(saveSettings)

        settingsMenu.add(startPopupT)
        settingsMenu.add(crackedT)
        settingsMenu.add(enableCustomJVMT)
        settingsMenu.add(crackedIGNT)
        settingsMenu.add(minRamAllocT)
        settingsMenu.add(maxRamAllocT)
        settingsMenu.add(jrePathT)
        settingsMenu.add(customArgsT)

        settingsMenu.add(startPopupI)
        settingsMenu.add(crackedI)
        settingsMenu.add(enableCustomJVMI)
        settingsMenu.add(crackedIGNI)
        settingsMenu.add(minRamAllocI)
        settingsMenu.add(maxRamAllocI)
        settingsMenu.add(jrePathI)
        settingsMenu.add(customArgsI)

        resetSettings.foreground = Color.WHITE
        saveSettings.foreground = Color.WHITE

        startPopupT.foreground = Color.WHITE
        crackedT.foreground = Color.WHITE
        enableCustomJVMT.foreground = Color.WHITE
        crackedIGNT.foreground = Color.WHITE
        minRamAllocT.foreground = Color.WHITE
        maxRamAllocT.foreground = Color.WHITE
        jrePathT.foreground = Color.WHITE
        customArgsT.foreground = Color.WHITE

        startPopupI.foreground = Color.WHITE
        crackedI.foreground = Color.WHITE
        enableCustomJVMI.foreground = Color.WHITE
        crackedIGNI.foreground = Color.WHITE
        minRamAllocI.foreground = Color.WHITE
        maxRamAllocI.foreground = Color.WHITE
        jrePathI.foreground = Color.WHITE
        customArgsI.foreground = Color.WHITE

        resetSettings.addActionListener {
            startPopup = fileReadLine("settings.cfg", 0).substring(11)
            cracked = fileReadLine("settings.cfg", 1).substring(8)
            enableCustomJVM = fileReadLine("settings.cfg", 2).substring(16)
            crackedIGN = fileReadLine("settings.cfg", 3).substring(11)
            minRamAlloc = fileReadLine("settings.cfg", 4).substring(12)
            maxRamAlloc = fileReadLine("settings.cfg", 5).substring(12)
            jrePath = fileReadLine("settings.cfg", 6).substring(8)
            customArgs = fileReadLine("settings.cfg", 7).substring(11)

            settingsMenu.repaint()
        }
        saveSettings.addActionListener {
            crackedIGN = crackedIGNI.text
            minRamAlloc = minRamAllocI.text
            maxRamAlloc = maxRamAllocI.text
            jrePath = jrePathI.text
            customArgs = customArgsI.text

            writeFile.overwriteFile(settingsFile.toString(), "startPopup=${startPopup}\ncracked=${cracked}\nenableCustomJVM=${enableCustomJVM}\ncrackedIGN=${crackedIGN}\nminRamAlloc=${minRamAlloc}\nmaxRamAlloc=${maxRamAlloc}\njrePath=${jrePath}\ncustomArgs=${customArgs}")
        }

        startPopupI.addActionListener {
            var bl = false
            bl = startPopup != "true"
            startPopup = bl.toString()
            startPopupI.text = startPopup
            settingsMenu.repaint()
        }
        crackedI.addActionListener {
            var bl = false
            bl = cracked != "true"
            cracked = bl.toString()
            crackedI.text = cracked
            settingsMenu.repaint()
        }
        enableCustomJVMI.addActionListener {
            var bl = false
            bl = enableCustomJVM != "true"
            enableCustomJVM = bl.toString()
            enableCustomJVMI.text = cracked
            enableCustomJVMI.text = enableCustomJVM
            settingsMenu.repaint()
        }

    }

}

