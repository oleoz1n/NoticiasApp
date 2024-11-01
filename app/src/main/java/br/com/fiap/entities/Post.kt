package br.com.fiap.entities

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val titulo: String = "",
    val desc: String = "",
    val dataGeracao: String = "",
    val tema: String = "",
    val autor: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(desc)
        parcel.writeString(dataGeracao)
        parcel.writeString(tema)
        parcel.writeString(autor)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post = Post(parcel)
        override fun newArray(size: Int): Array<Post?> = arrayOfNulls(size)
    }
}
