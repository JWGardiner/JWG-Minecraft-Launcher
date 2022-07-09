package com.jwg.launcher

import java.awt.Desktop
import java.awt.Frame
import java.net.URI
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.UIManager

val CONTRIBUTORS = arrayOf("JWGardiner", "JustAPotota")

//TODO: Fix bug where link doesn't open until the launcher is closed
fun openUri(uri: String) {
    try {
        Desktop.getDesktop().browse(URI.create(uri))
    } catch (e: UnsupportedOperationException) {
        val f: Frame = JFrame()
        JOptionPane.showMessageDialog(f,
            "Could not open $uri:\n$e", "Error opening URI",
            JOptionPane.ERROR_MESSAGE
        )
    }
}

fun contributorlist(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val gitLink = JButton("See full list")
    gitLink.setBounds(150, 435, 150, 35)
    
    JFrame().also { contributors ->
        contributors.title = "Contributors"
        contributors.setSize(300, 500)
        contributors.layout = null
        contributors.setLocationRelativeTo(null)
        contributors.isVisible = visible
        contributors.isResizable = false
        contributors.add(gitLink)

        gitLink.addActionListener {
            openUri("https://github.com/JWGardiner/JWG-Minecraft-Launcher/graphs/contributors")
            contributors.run { repaint() }
        }

        for ((i, contributor) in CONTRIBUTORS.withIndex()) {
            val contribButton = JButton(contributor)
            contribButton.setBounds(75, 25 + i*45, 150, 35)

            contributors.add(contribButton)

            contribButton.addActionListener {
                openUri("https://github.com/$contributor")
                contributors.run { repaint() }
            }
        }
    }
}