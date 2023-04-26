package com.ugnet.sel1.authentication.signup.components



data class AccountUiState(
    val name: String = "",
    val surname : String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)