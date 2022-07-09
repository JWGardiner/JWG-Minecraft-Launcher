package com.jwg.launcher

import java.awt.Desktop
import java.net.URI
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.UIManager

fun contributorlist(visible: Boolean) {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val gitLink = JButton("See full list")
    val jwgardiner = JButton("JWGardiner")
    val justapotota = JButton("JustAPotota")
    gitLink.setBounds(150, 435, 150, 35)
    jwgardiner.setBounds(75,25,150,35)
    justapotota.setBounds(75,70,150,35)
    JFrame().also { contributors ->
        contributors.title = "Contributors"
        contributors.setSize(300, 500)
        contributors.layout = null
        contributors.setLocationRelativeTo(null)
        contributors.isVisible = visible
        contributors.isResizable = false
        contributors.add(gitLink)
        contributors.add(jwgardiner)
        contributors.add(justapotota)
        gitLink.addActionListener {
            //TODO: Fix bug where link doesn't open until the launcher is closed
            Desktop.getDesktop().browse(URI.create("https://github.com/JWGardiner/JWG-Minecraft-Launcher/graphs/contributors"))
            contributors.run { repaint() }
        }
        jwgardiner.addActionListener {
            //TODO: Fix bug where link doesn't open until the launcher is closed
            Desktop.getDesktop().browse(URI.create("https://github.com/JWGardiner"))
            contributors.run { repaint() }
        }
        justapotota.addActionListener {
            //TODO: Fix bug where link doesn't open until the launcher is closed
            Desktop.getDesktop().browse(URI.create("https://github.com/JustAPotota"))
            contributors.run { repaint() }
        }
    }
}