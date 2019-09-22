package br.edu.ifsp.finances.domain

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("username")
    val username : String,
    @SerializedName("email")
    val email : String

)