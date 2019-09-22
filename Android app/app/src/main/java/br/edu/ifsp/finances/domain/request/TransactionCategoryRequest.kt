package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName

data class TransactionCategoryRequest (
    @SerializedName("name")
    val name : String
)