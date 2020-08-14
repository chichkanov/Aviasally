package com.chichkanov.aviasally.routeselection.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.chichkanov.aviasally.R
import com.chichkanov.aviasally.core.ext.createViewModel
import com.chichkanov.aviasally.core.ext.nonNullValue
import com.chichkanov.aviasally.core.presentation.BaseFragment
import com.chichkanov.aviasally.di.DI
import com.chichkanov.aviasally.searchcity.presentation.SearchCityLaunchMode
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_route_selection.*

class RouteSelectionFragment : BaseFragment<RouteSelectionViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_route_selection

    override fun provideViewModel(): RouteSelectionViewModel = createViewModel {
        DI.routeSelectionComponent.get().routeSelectionViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            routeSelectionContainer.fit { Edge.Top + Edge.Bottom + Edge.Left + Edge.Right }
        }

        fromTv.setOnClickListener {
            findNavController().navigate(
                RouteSelectionFragmentDirections.searchCity(
                    SearchCityLaunchMode.FROM_SELECT
                )
            )
        }
        toTv.setOnClickListener {
            findNavController().navigate(
                RouteSelectionFragmentDirections.searchCity(
                    SearchCityLaunchMode.TO_SELECT
                )
            )
        }
        searchBtn.setOnClickListener {
            findNavController().navigate(
                RouteSelectionFragmentDirections.routeDetails(
                    viewModel.dataState.nonNullValue.fromCity!!,
                    viewModel.dataState.nonNullValue.toCity!!
                )
            )
        }
        switchCitiesBtn.setOnClickListener { viewModel.onSwitchCitiesClick() }

        observe(viewModel.dataState, ::onDataStateChanged)
    }

    private fun onDataStateChanged(dataState: RouteSelectionViewModel.DataState) {
        fromTv.text = dataState.fromCity?.fullName
        toTv.text = dataState.toCity?.fullName
        searchBtn.isEnabled = dataState.fromCity != null && dataState.toCity != null
    }
}