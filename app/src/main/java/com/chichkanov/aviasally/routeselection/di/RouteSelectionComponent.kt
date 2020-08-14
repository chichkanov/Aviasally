package com.chichkanov.aviasally.routeselection.di

import com.chichkanov.aviasally.routeselection.presentation.RouteSelectionViewModel
import dagger.Subcomponent

@Subcomponent
interface RouteSelectionComponent {

    val routeSelectionViewModel: RouteSelectionViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): RouteSelectionComponent
    }
}