package com.chichkanov.aviasally.routeselection.domain

import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.routeselection.data.CitiesHistoryRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CitiesHistoryUseCase @Inject constructor(
    private val citiesHistoryRepository: CitiesHistoryRepository
) {

    fun getCitiesHistory() = Single
        .fromCallable {
            CitiesHistory(
                citiesHistoryRepository.lastFromCity,
                citiesHistoryRepository.lastToCity
            )
        }

    fun saveLastFromCity(city: City): Completable = Completable
        .fromAction { citiesHistoryRepository.lastFromCity = city }

    fun saveLastToCity(city: City): Completable = Completable
        .fromAction { citiesHistoryRepository.lastToCity = city }

    data class CitiesHistory(
        val fromCity: City?,
        val toCity: City?
    )
}