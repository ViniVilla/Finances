package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("usernameOrEmail")
    val usernameOrEmail : String,
    @SerializedName("password")
    val password : String
)