package de.thomaskuenneth.kotlinconf24.menubardemo

import kotlinx.coroutines.flow.*

enum class AskToSave {
    INVISIBLE,
    VISIBLE,
    CANCEL,
    SAVE,
    DONT_SAVE
}

class AppWindowState(
    title: String,
    val appState: AppState
) {

    private val _title: MutableStateFlow<String> = MutableStateFlow(title)
    val title: StateFlow<String> = _title.asStateFlow()

    private val _changed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val changed: StateFlow<Boolean> = _changed.asStateFlow()

    private val _askToSave: MutableStateFlow<AskToSave> = MutableStateFlow(AskToSave.INVISIBLE)
    val askToSave: StateFlow<AskToSave> = _askToSave.asStateFlow()

    fun toggleChanged() {
        _changed.update { !_changed.value }
    }

    suspend fun close(): Boolean {
        val close = if (_changed.value) {
            _askToSave.update { AskToSave.VISIBLE }
            _askToSave.first { it != AskToSave.VISIBLE }
            _askToSave.value != AskToSave.CANCEL
        } else true
        if (close) appState.removeWindowState(this)
        return close
    }

    fun cancelClose() {
        _askToSave.update { AskToSave.CANCEL }
    }

    fun closeAndSave() {
        appState.save(this)
        _askToSave.update { AskToSave.SAVE }
    }

    fun closeWithoutSave() {
        _askToSave.update { AskToSave.DONT_SAVE }
    }
}
