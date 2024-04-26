package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.ApplicationScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.net.URI

class AppState(private val applicationScope: ApplicationScope) {

    private val _windows = MutableStateFlow(mutableStateListOf(""))
    val windows = _windows.asStateFlow()

    private val _showAboutDialog = MutableStateFlow(false)
    val showAboutDialog = _showAboutDialog.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog = _showSettingsDialog.asStateFlow()

    fun setShowAboutDialog(show: Boolean) {
        _showAboutDialog.update { show }
    }

    fun setShowSettingsDialog(show: Boolean) {
        _showSettingsDialog.update { show }
    }

    fun newWindow(title: String = "") {
        _windows.value.add(title)
    }

    fun exit(): Boolean {
        _windows.value.clear()
        applicationScope.exitApplication()
        return false
    }

    fun visitUs() {
        openUri(URI("https://www.thomaskuenneth.de/"))
    }

    fun openFileDialog(window: ComposeWindow) {
        val fileDialog = java.awt.FileDialog(window)
        fileDialog.isVisible = true
        fileDialog.file?.let { newWindow(it) }
    }
}
