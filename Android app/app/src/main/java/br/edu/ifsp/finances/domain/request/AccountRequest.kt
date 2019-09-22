package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName

data class AccountRequest (
    @SerializedName("name")
    val name : String,
    @SerializedName("typeId")
    val typeId : Long
)