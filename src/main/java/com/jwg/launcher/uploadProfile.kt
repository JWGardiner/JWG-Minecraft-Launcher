package com.jwg.launcher

import com.jwg.jwgapi.readFile.fileReadLine
import com.jwg.launcher.unzipFile.extractFolder
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

import javax.swing.JFileChooser
import javax.swing.JFrame


fun uploadProfile() {
    val tmpZip = File("launcher/tmp.zip")
    val tmpZipF = File("launcher/tmp")

    if (tmpZip.delete()) {
        println("Deleted temp zip: " + tmpZip.name)
    } else {
        println("Temp zip doesn't exist.")
    }
    if (tmpZipF.delete()) {
        println("Deleted temp folder: " + tmpZip.name)
    } else {
        println("Temp folder doesn't exist.")
    }
    val profiles = "launcher/profiles"
    val fileChooser = JFileChooser()
    fileChooser.currentDirectory = File(System.getProperty("user.home"));
    val frame = JFrame()
    val result = fileChooser.showOpenDialog(frame)
    if (result == JFileChooser.APPROVE_OPTION) {
        Files.copy(Path.of(fileChooser.selectedFile.absolutePath), Path.of("launcher/tmp.zip"))
    }
    extractFolder("launcher/tmp.zip", "launcher/tmp/")
    val installName = fileReadLine("launcher/tmp/profile.cfg", 0).replace("Name = ", "")
    Files.move(Path.of("launcher/tmp"), Path.of("$profiles/$installName"))



}

