package com.example.movietv.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val link: String,
    val title: String,
    val detail: String = "Top Ten movie in this genre",
    val picUrl: String = "https://www.denofgeek.com/wp-content/uploads/2016/05/cobra_poster.jpg?resize=620%2C349"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(link)
        parcel?.writeString(title)
        parcel?.writeString(detail)
        parcel?.writeString(picUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}