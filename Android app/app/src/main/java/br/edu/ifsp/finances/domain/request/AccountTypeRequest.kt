package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName

data class AccountTypeRequest(
    @SerializedName("name")
    val name : String
)