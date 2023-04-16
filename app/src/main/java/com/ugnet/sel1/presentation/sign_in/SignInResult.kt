package com.ugnet.sel1.presentation.sign_in

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
)

data class Hiree(
    val id: String,
    val username: String,
    val voornaam: String,
    val achternaam: String
)

data class Manager(
    val id: String,
    val username: String,
    val voornaam: String,
    val achternaam: String,
    val ownedPanden: List<String>
)
