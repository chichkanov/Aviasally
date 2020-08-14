package com.chichkanov.aviasally.searchcity.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chichkanov.aviasally.R
import com.chichkanov.aviasally.core.domain.City
import com.chichkanov.aviasally.core.ext.closeKeyboard
import com.chichkanov.aviasally.core.ext.createViewModel
import com.chichkanov.aviasally.core.ext.showKeyboard
import com.chichkanov.aviasally.core.presentation.AbstractTextWatcher
import com.chichkanov.aviasally.core.presentation.BaseFragment
import com.chichkanov.aviasally.di.DI
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_search_city.*

class SearchCityFragment : BaseFragment<SearchCityViewModel>() {

    companion object {
        private const val INPUT_DEBOUNCE = 400L
    }

    private var debounceInputRunnable = Runnable { }
    private val callbacksHandler = Handler(Looper.getMainLooper())

    private val debounceTextChangeListener = object : AbstractTextWatcher() {
        override fun afterTextChanged(text: Editable?) {
            callbacksHandler.removeCallbacks(debounceInputRunnable)
            debounceInputRunnable = Runnable {
                text?.let { viewModel.onInputChanged(it.toString()) }
            }
            callbacksHandler.postDelayed(debounceInputRunnable, INPUT_DEBOUNCE)
        }
    }

    private val suggestsAdapter = GroupAdapter<GroupieViewHolder>()
    private val args: SearchCityFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_search_city

    override fun provideViewModel(): SearchCityViewModel = createViewModel {
        DI.searchCityComponent.get().searchCityViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            searchCityContainer.fit { Edge.Top + Edge.Bottom + Edge.Left + Edge.Right }
        }

        initRv()

        backBtn.setOnClickListener {
            closeKeyboard()
            findNavController().popBackStack()
        }
        cityInput.addTextChangedListener(debounceTextChangeListener)
        cityInput.showKeyboard()

        observe(viewModel.dataState, ::onDataStateChanged)
    }

    private fun onDataStateChanged(dataState: SearchCityViewModel.DataState) {
        progress.isVisible = dataState is SearchCityViewModel.DataState.Loading
        errorTv.isVisible = dataState is SearchCityViewModel.DataState.Error
        emptyTv.isVisible = dataState is SearchCityViewModel.DataState.Loaded
                && dataState.cities.isEmpty()
        suggestsRv.isVisible = dataState is SearchCityViewModel.DataState.Loaded
                && dataState.cities.isNotEmpty()

        if (dataState is SearchCityViewModel.DataState.Loaded) {
            suggestsAdapter.update(
                dataState.cities.map { CitySuggestItem(it) { onCityClick(it) } }
            )
        }
    }

    private fun onCityClick(city: City) {
        closeKeyboard()
        if (args.launchMode == SearchCityLaunchMode.FROM_SELECT) {
            viewModel.onFromCitySelected(city)
        } else {
            viewModel.onToCitySelected(city)
        }
        findNavController().popBackStack()
    }

    private fun initRv() = with(suggestsRv) {
        adapter = suggestsAdapter
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(SearchCityItemDecoration(context))
    }
}