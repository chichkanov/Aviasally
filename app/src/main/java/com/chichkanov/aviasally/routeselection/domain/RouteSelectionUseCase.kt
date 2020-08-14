package com.chichkanov.aviasally.routeselection.domain

import android.annotation.SuppressLint
import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.core.ext.applySchedulers
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteSelectionUseCase @Inject constructor(
    private val citiesHistoryUseCase: CitiesHistoryUseCase
) {

    private val selectedFromCitySubject = PublishSubject.create<City>()
    private val selectedToCitySubject = PublishSubject.create<City>()

    val selectedFromCity: Observable<City> = selectedFromCitySubject
    val selectedToCity: Observable<City> = selectedToCitySubject

    @SuppressLint("CheckResult")
    fun onNewFromCitySelected(city: City) {
        selectedFromCitySubject.onNext(city)
        citiesHistoryUseCase.saveLastFromCity(city)
            .applySchedulers()
            .subscribe(
                { Timber.d("Last from city saved") },
                { Timber.e(it, "Error save from city") }
            )
    }

    @SuppressLint("CheckResult")
    fun onNewToCitySelected(city: City) {
        selectedToCitySubject.onNext(city)
        citiesHistoryUseCase.saveLastToCity(city)
            .applySchedulers()
            .subscribe(
                { Timber.d("Last to city saved") },
                { Timber.e(it, "Error save to city") }
            )
    }
}