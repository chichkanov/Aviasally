package com.chichkanov.aviasally.searchcity.data

import com.chichkanov.aviasally.core.data.CoordinatesResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "country")
    val country: String,
    @Json(name = "fullname")
    val fullName: String,
    @Json(name = "iata")
    val iata: List<String>?,
    @Json(name = "city")
    val city: String,
    @Json(name = "location")
    val coordinates: CoordinatesResponse
)