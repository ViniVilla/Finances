package br.edu.ifsp.finances.domain.request

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionRequest (
    @SerializedName("name")
    val name : String,
    @SerializedName("amount")
    val amount : BigDecimal,
    @SerializedName("account")
    val account : Long,
    @SerializedName("category")
    val category : Long
)