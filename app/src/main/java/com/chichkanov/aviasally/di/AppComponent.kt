package com.chichkanov.aviasally.di

import android.app.Application
import com.chichkanov.aviasally.routedetails.di.RouteDetailsComponent
import com.chichkanov.aviasally.routeselection.di.RouteSelectionComponent
import com.chichkanov.aviasally.searchcity.di.SearchCityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    val routeDetailsComponent: RouteDetailsComponent.Builder

    val routeSelectionComponent: RouteSelectionComponent.Builder

    val searchCityComponent: SearchCityComponent.Builder

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun context(appContext: Application): Builder
    }
}