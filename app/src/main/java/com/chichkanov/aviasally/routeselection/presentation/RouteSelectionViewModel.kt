package com.chichkanov.aviasally.routeselection.presentation

import androidx.lifecycle.MutableLiveData
import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.core.ext.applySchedulers
import com.chichkanov.aviasally.core.ext.nonNullValue
import com.chichkanov.aviasally.routeselection.domain.CitiesHistoryUseCase
import com.chichkanov.aviasally.routeselection.domain.RouteSelectionUseCase
import com.chichkanov.diary.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class RouteSelectionViewModel @Inject constructor(
    private val citiesHistoryUseCase: CitiesHistoryUseCase,
    private val routeSelectionUseCase: RouteSelectionUseCase
) : BaseViewModel() {

    val dataState = MutableLiveData<DataState>()

    init {
        dataState.value = DataState()

        citiesHistoryUseCase.getCitiesHistory()
            .applySchedulers()
            .subscribe(
                { dataState.value = DataState(it.fromCity, it.toCity) },
                { Timber.e(it, "Error get cities history") }
            )
            .addDisposable()

        routeSelectionUseCase.selectedFromCity
            .applySchedulers()
            .subscribe(
                { dataState.value = dataState.nonNullValue.copy(fromCity = it) },
                { Timber.e(it, "Error select from city") }
            )
            .addDisposable()

        routeSelectionUseCase.selectedToCity
            .applySchedulers()
            .subscribe(
                { dataState.value = dataState.nonNullValue.copy(toCity = it) },
                { Timber.e(it, "Error select to city") }
            )
            .addDisposable()
    }

    fun onSwitchCitiesClick() {
        with(dataState.nonNullValue.copy()) {
            fromCity?.let { routeSelectionUseCase.onNewToCitySelected(it) }
            toCity?.let { routeSelectionUseCase.onNewFromCitySelected(it) }
        }
    }

    data class DataState(
        val fromCity: City? = null,
        val toCity: City? = null
    )
}