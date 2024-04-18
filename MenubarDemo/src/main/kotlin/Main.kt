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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FrameWindowScope.App(exitApplication: () -> Unit) {
    var showAboutDialog by remember { mutableStateOf(false) }
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
