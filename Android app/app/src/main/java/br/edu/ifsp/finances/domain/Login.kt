package br.edu.ifsp.finances.domain

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("usernameOrEmail")
    val usernameOrEmail : String,
    @SerializedName("password")
    val password : String
)