package com.jwg

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.UIManager


fun start() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    val isLoggedIn = false
    var loginText = ""
    val username = "JWG_"

    val settings = JButton("⚙️")
    if (isLoggedIn){
        loginText = username
    } else {
        loginText = "Login"
    }
    val login = JButton(loginText)
    JFrame().also { window ->
        window.title = "JWG Minecraft Launcher"
        window.setSize(1200, 700)
        window.layout = null
        window.setLocationRelativeTo(null)
        window.isVisible = true
        window.isResizable = false;

        settings.setBounds(0,0,35,35);
        login.setBounds(1050,0,150,35);
        window.add(settings)
        window.add(login)
    }

}