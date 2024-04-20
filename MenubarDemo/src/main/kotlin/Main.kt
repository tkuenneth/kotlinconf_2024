package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.awt.desktop.AboutHandler
import java.awt.desktop.PreferencesHandler

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FrameWindowScope.App(exitApplication: () -> Unit) {
    var showAboutDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installAboutHandler { showAboutDialog = true }
            installPreferencesHandler { showAboutDialog = true }
        }
    }
    MaterialTheme {
        MenuBar {
            if (!IS_MACOS) {
                Menu(text = stringResource(Res.string.file)) {
                    Item(
                        text = stringResource(Res.string.quit),
                        shortcut = KeyShortcut(Key.F4, alt = true),
                        onClick = exitApplication
                    )
                }
            }
            Menu(text = stringResource(Res.string.help)) {
                if (!IS_MACOS) {
                    Item(
                        text = stringResource(Res.string.about),
                        onClick = {
                            showAboutDialog = true
                        }
                    )
                }
            }
        }
        if (showAboutDialog) {
            AboutDialog {
                showAboutDialog = false
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = ::exitApplication
    ) {
        App(::exitApplication)
    }
}

private fun Desktop.installAboutHandler(handler: AboutHandler) {
    if (isSupported(Desktop.Action.APP_ABOUT)) {
        setAboutHandler(handler)
    }
}

private fun Desktop.installPreferencesHandler(handler: PreferencesHandler) {
    if (isSupported(Desktop.Action.APP_ABOUT)) {
        setPreferencesHandler(handler)
    }
}
