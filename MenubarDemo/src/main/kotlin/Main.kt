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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.net.URI

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(exitApplication: () -> Unit) {
    var showAboutDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installAboutHandler { showAboutDialog = true }
            installPreferencesHandler { showSettingsDialog = true }
        }
    }
    MaterialTheme {
        AppWindow(
            scope = scope,
            openAboutDialog = { showAboutDialog = true },
            exitApplication = exitApplication
        )
        if (showAboutDialog) {
            AboutDialog { showAboutDialog = false }
        }
        if (showSettingsDialog) {
            SettingsDialog { showSettingsDialog = false }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppWindow(
    scope: CoroutineScope,
    openAboutDialog: () -> Unit,
    exitApplication: () -> Unit
) {
    Window(
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = exitApplication
    ) {
        AppMenuBar(
            openAboutDialog = openAboutDialog,
            openUrl = { scope.launch { openUri(URI("https://www.thomaskuenneth.de/")) } },
            exit = exitApplication
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FrameWindowScope.AppMenuBar(
    openAboutDialog: () -> Unit,
    openUrl: () -> Unit,
    exit: () -> Unit
) {
    MenuBar {
        Menu(text = stringResource(Res.string.file)) {
            Item(
                text = stringResource(Res.string.open),
                shortcut = create(Key.O),
                onClick = { openFileDialog() }
            )
            if (!IS_MACOS) {
                Item(
                    text = stringResource(Res.string.quit),
                    shortcut = KeyShortcut(Key.F4, alt = true),
                    onClick = exit
                )
            }
        }
        Menu(text = stringResource(Res.string.help)) {
            if (!IS_MACOS) {
                Item(
                    text = stringResource(Res.string.about),
                    onClick = openAboutDialog
                )
            }
            Item(
                text = stringResource(Res.string.homepage),
                onClick = openUrl
            )
        }
    }
}

fun main() = application {
    App(::exitApplication)
}
