package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import java.awt.Desktop
import java.awt.desktop.AboutHandler
import java.awt.desktop.PreferencesHandler
import java.net.URI
import java.util.*

private val osNameLowerCase = System.getProperty("os.name", "").lowercase(Locale.getDefault())
val IS_MACOS = osNameLowerCase.contains("mac os x")

fun Desktop.installAboutHandler(handler: AboutHandler) {
    if (isSupported(Desktop.Action.APP_ABOUT)) {
        setAboutHandler(handler)
    }
}

fun Desktop.installPreferencesHandler(handler: PreferencesHandler) {
    if (isSupported(Desktop.Action.APP_ABOUT)) {
        setPreferencesHandler(handler)
    }
}

fun openUri(uri: URI) {
    with(Desktop.getDesktop()) {
        if (isSupported(Desktop.Action.BROWSE)) {
            browse(uri)
        }
    }
}

fun FrameWindowScope.openFileDialog() {
    java.awt.FileDialog(window).isVisible = true
}

fun create(key: Key): KeyShortcut {
    return KeyShortcut(
        key = key,
        ctrl = !IS_MACOS,
        meta = IS_MACOS
    )
}
