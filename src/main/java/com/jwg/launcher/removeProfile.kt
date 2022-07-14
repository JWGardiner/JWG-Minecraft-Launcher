package com.jwg.launcher

import com.jwg.Main.*
import com.jwg.jwgapi.logger
import com.jwg.jwgapi.parseVersion
import com.jwg.jwgapi.parseVersion.versionInt
import scanFiles
import java.io.File
import javax.swing.*

fun removeProfile(visible: Boolean) {
    val profiles = "launcher/profiles"

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val confirm = JButton("Confirm")

    val profileListScrollPane = JScrollPane()
    val profileListModel = DefaultListModel<String>()
    val profileList = JList(profileListModel)
    profileList.setListData(scanFiles(File(profiles)))
    profileListScrollPane.setViewportView(profileList)

    profileList.setBounds(10, 55, 140, 305)

    JFrame().also { rmProfMenu ->
        rmProfMenu.title = "Remove Profile"
        rmProfMenu.setSize(310, 400)
        rmProfMenu.layout = null
        rmProfMenu.setLocationRelativeTo(null)
        rmProfMenu.isVisible = visible
        rmProfMenu.isResizable = false


        rmProfMenu.add(confirm)
        rmProfMenu.add(profileListScrollPane)

        confirm.setBounds(160, 10, 139, 35)
        profileList.setBounds(10, 10, 140, 350)
        profileListScrollPane.setBounds(10, 10, 140, 350)

        confirm.addActionListener {
            val profileToRemove = profileList.selectedValue.toString()
            val f = File("$profiles/$profileToRemove")
            if (f.deleteRecursively()) {
                logger.log(logFile, versionInt(version), project, 0, "Removed profile ${f.name}")
            } else {
                logger.log(logFile, versionInt(version), project, 1, "Failed to remove profile ${f.name}")
            }
            profileList.setListData(scanFiles(File(profiles)))
            profileList.updateUI()
        }
    }
}