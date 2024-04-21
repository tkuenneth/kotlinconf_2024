package de.thomaskuenneth.kotlinconf24.fileassociationdemo

import java.awt.Desktop
import java.awt.desktop.OpenFilesHandler

fun Desktop.installOpenFileHandler(handler: OpenFilesHandler) {
    if (isSupported(Desktop.Action.APP_OPEN_FILE)) {
        setOpenFileHandler(handler)
    } else {
        println("setOpenFileHandler() not available on this platform")
    }
}
