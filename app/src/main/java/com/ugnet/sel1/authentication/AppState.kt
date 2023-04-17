package com.ugnet.sel1.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ugnet.sel1.domain.models.User

@Composable
fun rememberAppState(): AppState {
    return remember { mutableStateOf(AppState()) }.value
}

data class AppState(
    val user: User? = null
)