package de.thomaskuenneth.kotlinconf24.menubardemo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppWindowState(title: String) {

    private val _title: MutableStateFlow<String> = MutableStateFlow(title)
    val title: StateFlow<String> = _title.asStateFlow()

    private val _changed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val changed: StateFlow<Boolean> = _changed.asStateFlow()

    fun toggleChanged() {
        _changed.value = !_changed.value
    }
}
