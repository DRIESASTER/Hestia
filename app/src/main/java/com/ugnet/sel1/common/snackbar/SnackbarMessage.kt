package com.ugnet.sel1.common.snackbar


import android.content.res.Resources
import androidx.annotation.StringRes
import  com.ugnet.sel1.R.string as AppText

sealed class SnackbarMessage {

    abstract val aboveKeyboard: Boolean

    class StringSnackbar(val message: String, override val aboveKeyboard: Boolean = false) : SnackbarMessage()
    class ResourceSnackbar(@StringRes val message: Int, override val aboveKeyboard: Boolean = false) : SnackbarMessage()



    companion object {
        fun SnackbarMessage.toMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }

        fun Throwable.toSnackbarMessage(): SnackbarMessage {
            val message = this.message.orEmpty()
            return if (message.isNotBlank()) StringSnackbar(message)
            else ResourceSnackbar(AppText.generic_error)
        }
    }
}