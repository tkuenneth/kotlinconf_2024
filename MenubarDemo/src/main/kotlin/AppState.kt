package de.thomaskuenneth.kotlinconf24.menubardemo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URI

class AppState(private val applicationScope: ApplicationScope) {

    private val _windows = mutableStateListOf<AppWindowState>()
    val windows: List<AppWindowState> get() = _windows

    private val _showAboutDialog = MutableStateFlow(false)
    val showAboutDialog = _showAboutDialog.asStateFlow()

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog = _showSettingsDialog.asStateFlow()

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        newWindow()
    }

    fun setShowAboutDialog(show: Boolean) {
        _showAboutDialog.update { show }
    }

    fun setShowSettingsDialog(show: Boolean) {
        _showSettingsDialog.update { show }
    }

    fun newWindow(title: String = "") {
        _windows.add(
            AppWindowState(
                title = title,
                appState = this
            )
        )
    }

    fun removeWindowState(state: AppWindowState) {
        _windows.remove(state)
        if (_windows.isEmpty()) {
            applicationScope.exitApplication()
        }
    }

    fun exit(callback: (Boolean) -> Unit) {
        scope.launch {
            scope.launch {
                for (windowState in _windows.toList()) {
                    if (!windowState.close()) break
                }
            }.join()
            val canQuit = _windows.isEmpty()
            callback(canQuit)
            if (canQuit) applicationScope.exitApplication()
        }
    }

    fun visitUs() {
        openUri(URI("https://www.thomaskuenneth.de/"))
    }

    fun openFileDialog(window: ComposeWindow) {
        val fileDialog = java.awt.FileDialog(window)
        fileDialog.isVisible = true
        fileDialog.file?.let { newWindow(it) }
    }

    fun save(appWindowState: AppWindowState) {}
}
