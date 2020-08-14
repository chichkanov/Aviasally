package com.chichkanov.aviasally.core.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Long,
    val fullName: String,
    val city: String,
    val iata: String?,
    val coordinates: Coordinates
) : Parcelable