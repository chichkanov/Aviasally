package com.chichkanov.aviasally.searchcity.di

import com.chichkanov.aviasally.searchcity.presentation.SearchCityViewModel
import dagger.Subcomponent

@Subcomponent(modules = [SearchCityModule::class])
interface SearchCityComponent {

    val searchCityViewModel: SearchCityViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): SearchCityComponent
    }
}