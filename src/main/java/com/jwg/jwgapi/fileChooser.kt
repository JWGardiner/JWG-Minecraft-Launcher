package com.jwg.jwgapi

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
    fun fileChooser(startingDir: String, windowTitle: String, allowBack: Boolean, allowedFileTypes: Array<String?>): String {
        //Create the actual window
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        val model = DefaultListModel<String>()
        val folderList = JList(model)
        val scrollPane = JScrollPane()

        val select = JButton("Select")
        val back = JButton("Go Back")
        val unsupportedWarn = JLabel("Unsupported File Type!")
        val clearWarn = JButton("X")
        folderList.setListData(scanFiles(File(startingDir)))
        scrollPane.setViewportView(folderList)

        val chosenFile = ""
        var dir = startingDir
        JFrame().also { window ->
            window.title = windowTitle
            window.setSize(750, 500)
            window.isResizable = false
            window.layout = null
            window.setLocationRelativeTo(null)
            window.add(scrollPane)

            select.setBounds(9, 10, 135, 35)
            back.setBounds(155, 10,135, 35)
            unsupportedWarn.setBounds(355, 53,175, 35)
            clearWarn.setBounds(310, 55,31, 31)

            unsupportedWarn.isVisible = false
            clearWarn.isVisible = false

            window.add(select)
            window.add(back)
            window.add(unsupportedWarn)
            window.add(clearWarn)

            if (allowBack) {
                back.isVisible = true
                back.updateUI()
            } else {
                back.isVisible = false
                back.updateUI()
            }
            back.addActionListener {
                dir += "/.."
                folderList.setListData(scanFiles(File(dir)))
                folderList.updateUI()
            }
            select.addActionListener {
                folderList.updateUI()
                val selected: String = folderList.selectedValue.toString()
                var supportedFile = false
                var m = 0
                for (i in allowedFileTypes) {
                    if (allowedFileTypes[m] == "folder" && File(dir+selected).isDirectory) {
                        supportedFile = true
                    }
                    else if (selected.endsWith(allowedFileTypes[m].toString())) {
                        supportedFile = true
                    }
                    m+=1
                }
                if (!supportedFile) {
                    unsupportedWarn.isVisible = true
                    clearWarn.isVisible = true
                    unsupportedWarn.updateUI()
                    clearWarn.updateUI()
                }
                if (dir != startingDir) {
                    back.isVisible = true
                    back.updateUI()
                }
            }
            clearWarn.addActionListener {
                unsupportedWarn.isVisible = false
                clearWarn.isVisible = false
                unsupportedWarn.updateUI()
                clearWarn.updateUI()
            }

            //window.add(folderList)
            scrollPane.setBounds(10, 55, 280, 405)
            folderList.setBounds(10, 55, 280, 405)
            window.isVisible = true
        }
        return chosenFile
    }






