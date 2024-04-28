package de.thomaskuenneth.kotlinconf24.menubardemo

import kotlinx.coroutines.flow.*

enum class ASK_SAVE {
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

    private val _askSave: MutableStateFlow<ASK_SAVE> = MutableStateFlow(ASK_SAVE.INVISIBLE)
    val askSave: StateFlow<ASK_SAVE> = _askSave.asStateFlow()

    fun toggleChanged() {
        _changed.update { !_changed.value }
    }

    suspend fun close(): Boolean {
        val close = if (_changed.value) {
            _askSave.update { ASK_SAVE.VISIBLE }
            _askSave.first { it != ASK_SAVE.VISIBLE }
            _askSave.value != ASK_SAVE.CANCEL
        } else true
        if (close) appState.removeWindowState(this)
        return close
    }

    fun cancelClose() {
        _askSave.update { ASK_SAVE.CANCEL }
    }

    fun closeAndSave() {
        appState.save(this)
        _askSave.update { ASK_SAVE.SAVE }
    }

    fun closeWithoutSave() {
        _askSave.update { ASK_SAVE.DONT_SAVE }
    }
}
