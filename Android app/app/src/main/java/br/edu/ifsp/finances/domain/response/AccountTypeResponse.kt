package br.edu.ifsp.finances.domain.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AccountTypeResponse (
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()
    )

    override fun toString() : String {
        return name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountTypeResponse> {
        override fun createFromParcel(parcel: Parcel): AccountTypeResponse {
            return AccountTypeResponse(parcel)
        }

        override fun newArray(size: Int): Array<AccountTypeResponse?> {
            return arrayOfNulls(size)
        }
    }
}