package br.edu.ifsp.finances.domain.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("type")
    val type : AccountTypeResponse

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readParcelable(AccountTypeResponse::class.java.classLoader)
    )

    override fun toString() : String {
    return name
}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeParcelable(type, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountResponse> {
        override fun createFromParcel(parcel: Parcel): AccountResponse {
            return AccountResponse(parcel)
        }

        override fun newArray(size: Int): Array<AccountResponse?> {
            return arrayOfNulls(size)
        }
    }
}