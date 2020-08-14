package com.chichkanov.aviasally.routedetails.di

import com.chichkanov.aviasally.routedetails.presentation.RouteDetailsViewModel
import dagger.Subcomponent

@Subcomponent
interface RouteDetailsComponent {

    val routeDetailsViewModel: RouteDetailsViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): RouteDetailsComponent
    }
}