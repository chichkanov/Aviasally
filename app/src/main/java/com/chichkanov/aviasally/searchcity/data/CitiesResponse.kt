package com.chichkanov.aviasally.searchcity.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CitiesResponse(
    @Json(name = "cities")
    val cities: List<CityResponse>
)