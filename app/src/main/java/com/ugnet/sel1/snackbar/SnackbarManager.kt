package com.ugnet.sel1.snackbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SnackbarManager(private val coroutineScope: CoroutineScope) {
    private var _message by mutableStateOf("")
    val message: String get() = _message

    private var _isVisible by mutableStateOf(false)
    val isVisible: Boolean get() = _isVisible

    fun showSnackbar(message: String, duration: Long = 3000) {
        coroutineScope.launch {
            _message = message
            _isVisible = true
            delay(duration)
            _isVisible = false
        }
    }
}