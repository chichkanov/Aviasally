package com.chichkanov.aviasally.searchcity.presentation

import androidx.lifecycle.MutableLiveData
import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.core.ext.applySchedulers
import com.chichkanov.aviasally.routeselection.domain.RouteSelectionUseCase
import com.chichkanov.aviasally.searchcity.domain.SearchCitiesUseCase
import com.chichkanov.diary.base.BaseViewModel
import javax.inject.Inject

class SearchCityViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val routeSelectionUseCase: RouteSelectionUseCase
) : BaseViewModel() {

    private companion object {
        private const val INITIAL_STATE_SEARCH = "Москва"
    }

    val dataState = MutableLiveData<DataState>()

    init {
        search(INITIAL_STATE_SEARCH)
    }

    fun onInputChanged(input: String) = search(input)

    fun onFromCitySelected(city: City) = routeSelectionUseCase.onNewFromCitySelected(city)

    fun onToCitySelected(city: City) = routeSelectionUseCase.onNewToCitySelected(city)

    private fun search(input: String) {
        searchCitiesUseCase.searchCities(input)
            .applySchedulers()
            .doOnSubscribe { dataState.value = DataState.Loading }
            .subscribe(
                { dataState.value = DataState.Loaded(it) },
                { dataState.value = DataState.Error }
            )
            .addDisposable()
    }

    sealed class DataState {
        object Loading : DataState()
        object Error : DataState()
        class Loaded(val cities: List<City>) : DataState()
    }
}