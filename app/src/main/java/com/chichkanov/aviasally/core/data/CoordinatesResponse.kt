package com.chichkanov.aviasally.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinatesResponse(
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lon")
    val longitude: Double
)