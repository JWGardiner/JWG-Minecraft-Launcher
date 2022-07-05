package com.jwg.launcher

import mainMenu
import javax.swing.UIManager


fun start() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    mainMenu()
}