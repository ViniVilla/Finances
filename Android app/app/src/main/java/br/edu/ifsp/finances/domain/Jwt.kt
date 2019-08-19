package br.edu.ifsp.finances.domain

import com.google.gson.annotations.SerializedName

data class Jwt (
    @SerializedName("accessToken")
    val accessToken : String,
    @SerializedName("tokenType")
    val tokenType : String
)