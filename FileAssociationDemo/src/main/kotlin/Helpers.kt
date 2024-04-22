package de.thomaskuenneth.kotlinconf24.fileassociationdemo

import java.awt.Desktop
import java.awt.Toolkit
import java.awt.desktop.OpenFilesHandler

fun Desktop.installOpenFileHandler(handler: OpenFilesHandler) {
    if (isSupported(Desktop.Action.APP_OPEN_FILE)) {
        setOpenFileHandler(handler)
    } else {
        println("setOpenFileHandler() not available on this platform")
    }
}

// See https://github.com/JetBrains/compose-multiplatform/issues/3308
fun setAppName(name: String) {
    val toolkit = Toolkit.getDefaultToolkit()
    val awtAppClassNameField = toolkit.javaClass.getDeclaredField("awtAppClassName")
    try {
        awtAppClassNameField.isAccessible = true
    } catch (ignored: Throwable) {
        awtAppClassNameField.trySetAccessible()
    }
    awtAppClassNameField.set(toolkit, name)
}
