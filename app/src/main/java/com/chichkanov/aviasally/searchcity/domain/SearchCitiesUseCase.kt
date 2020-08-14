package com.chichkanov.aviasally.searchcity.domain

import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.searchcity.data.CitiesService
import io.reactivex.Single
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val citiesService: CitiesService
) {

    fun searchCities(input: String): Single<List<City>> = citiesService.searchCities(input)
}