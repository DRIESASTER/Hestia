package com.ugnet.sel1.domain.models

sealed class UserData {
    data class Huurder(
        val id: String,
        val name: String,
        val surname: String,
        val username: String,
        val email: String
    ) : UserData()

    data class Manager(
        val id: String,
        val name: String,
        val surname: String,
        val username: String,
        val email: String
    ) : UserData()
}