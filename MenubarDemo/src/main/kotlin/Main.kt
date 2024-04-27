package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import java.awt.Desktop


fun main() = application {
    App(AppState(applicationScope = this))
}

@Composable
fun App(appState: AppState) {
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
        windows.forEach { state ->
            AppWindow(
                appWindowState = state,
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
