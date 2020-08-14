package com.chichkanov.aviasally.searchcity.di

import com.chichkanov.aviasally.searchcity.data.CitiesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class SearchCityModule {

    @Provides
    fun eventsApi(retrofit: Retrofit): CitiesService.CitiesApiApi = retrofit.create()
}