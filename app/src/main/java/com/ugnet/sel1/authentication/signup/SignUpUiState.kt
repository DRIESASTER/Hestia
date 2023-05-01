package com.ugnet.sel1.authentication.signup



data class SignUpUiState(
    val name: String = "",
    val surname : String = "",
    val email: String = "",
    val password: String = "",
)