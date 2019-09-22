package br.edu.ifsp.finances.domain.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionResponse(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("amount")
    val amount : BigDecimal,
    @SerializedName("account")
    val account : AccountResponse,
    @SerializedName("category")
    val category: TransactionCategoryResponse
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        BigDecimal(parcel.readString()),
        parcel.readParcelable(AccountResponse::class.java.classLoader),
        parcel.readParcelable(TransactionCategoryResponse::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(amount.toPlainString())
        parcel.writeParcelable(account, flags)
        parcel.writeParcelable(category, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionResponse> {
        override fun createFromParcel(parcel: Parcel): TransactionResponse {
            return TransactionResponse(parcel)
        }

        override fun newArray(size: Int): Array<TransactionResponse?> {
            return arrayOfNulls(size)
        }
    }
}