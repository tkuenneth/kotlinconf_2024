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
    LaunchedEffect(Unit) {
        with(Desktop.getDesktop()) {
            installAboutHandler { appState.setShowAboutDialog(true) }
            installPreferencesHandler { appState.setShowSettingsDialog(true) }
            installQuitHandler { _, response -> appState.exit { canQuit -> if (!canQuit) response.cancelQuit() } }
        }
    }
    MaterialTheme {
        appState.windows.forEach { state ->
            AppWindow(appWindowState = state)
        }
        if (showAboutDialog) {
            AboutDialog { appState.setShowAboutDialog(false) }
        }
        if (showSettingsDialog) {
            SettingsDialog { appState.setShowSettingsDialog(false) }
        }
    }
}
