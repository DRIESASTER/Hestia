package com.ugnet.sel1.authentication


import java.util.regex.Pattern


fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$"
    val pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE)
    return pattern.matcher(email).find()
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{6,}\$"
    val pattern = Pattern.compile(passwordRegex)
    return pattern.matcher(password).matches()
}