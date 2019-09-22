package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name")
    val name : String,
    @SerializedName("username")
    val username : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("password")
    val password : String
)