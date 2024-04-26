package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.Res
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.app_name
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.logo
import de.thomaskuenneth.kotlinconf24.menubardemo.menubardemo.generated.resources.untitled
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop


fun main() = application {
    App(AppState(applicationScope = this))
}

@Composable
fun ApplicationScope.App(appState: AppState) {
    val showAboutDialog by appState.showAboutDialog.collectAsState()
    val showSettingsDialog by appState.showSettingsDialog.collectAsState()
    val windows by appState.windows.collectAsState()
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installAboutHandler { appState.setShowAboutDialog(true) }
            installPreferencesHandler { appState.setShowSettingsDialog(true) }
            installQuitHandler { _, response -> if (!appState.exit()) response.cancelQuit() }
        }
    }
    MaterialTheme {
        windows.forEach { title ->
            AppWindow(
                title = title,
                appState = appState
            )
        }
        if (showAboutDialog) {
            AboutDialog { appState.setShowAboutDialog(false) }
        }
        if (showSettingsDialog) {
            SettingsDialog { appState.setShowSettingsDialog(false) }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppWindow(
    title: String,
    appState: AppState
) {
    Window(
        title = "${stringResource(Res.string.app_name)} \u2012 ${title.ifEmpty { stringResource(Res.string.untitled) }}",
        icon = painterResource(Res.drawable.logo),
        onCloseRequest = { appState.exit() }
    ) {
        AppMenuBar(appState)
    }
}
