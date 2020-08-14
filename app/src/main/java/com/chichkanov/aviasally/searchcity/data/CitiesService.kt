package com.chichkanov.aviasally.searchcity.data

import com.chichkanov.aviasally.core.domain.City
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class CitiesService @Inject constructor(
    private val citiesApiApi: CitiesApiApi,
    private val citiesResponseTransformer: CitiesResponseTransformer
) {

    fun searchCities(input: String): Single<List<City>> = citiesApiApi.searchCities(input)
        .map { citiesResponseTransformer.transform(it) }

    interface CitiesApiApi {
        @GET("autocomplete")
        fun searchCities(
            @Query("term") input: String?,
            @Query("lang") lang: String = "ru"
        ): Single<CitiesResponse>
    }
}