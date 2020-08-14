package com.chichkanov.aviasally.searchcity.data

import com.chichkanov.aviasally.core.data.CoordinatesResponseTransformer
import com.chichkanov.aviasally.core.domain.BaseTransformer
import com.chichkanov.aviasally.core.domain.City
import javax.inject.Inject

class CitiesResponseTransformer @Inject constructor(
    private val coordinatesResponseTransformer: CoordinatesResponseTransformer
) : BaseTransformer<CitiesResponse, List<City>> {

    override fun transform(data: CitiesResponse): List<City> = data.cities.map { cityResponse ->
        City(
            id = cityResponse.id,
            fullName = cityResponse.fullName,
            iata = cityResponse.iata?.firstOrNull(),
            city = cityResponse.city,
            coordinates = coordinatesResponseTransformer.transform(cityResponse.coordinates)
        )
    }
}