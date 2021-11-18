package com.example.avaz.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data (
		var name : String,
		var img_dish : Int,
		var selected : Boolean
):Parcelable