package com.chichkanov.aviasally.di

import com.chichkanov.aviasally.App

object DI {
    lateinit var appComponent: AppComponent

    val routeDetailsComponent = componentHolder(
        constructor = { appComponent.routeDetailsComponent.build() }
    )

    val routeSelectionComponent = componentHolder(
        constructor = { appComponent.routeSelectionComponent.build() }
    )

    val searchCityComponent = componentHolder(
        constructor = { appComponent.searchCityComponent.build() }
    )

    fun init(app: App) {
        appComponent = DaggerAppComponent
            .builder()
            .context(app)
            .build()
    }
}