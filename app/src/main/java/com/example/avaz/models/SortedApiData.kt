package com.example.avaz.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SortedApiData (
    var name : String,
    var img_dish : String,
    var selected : Boolean
): Parcelable