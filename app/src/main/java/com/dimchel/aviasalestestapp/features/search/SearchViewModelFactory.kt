package com.dimchel.aviasalestestapp.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimchel.aviasalestestapp.data.FlightRepository

class SearchViewModelFactory(
	private val flightRepository: FlightRepository
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = SearchViewModel(flightRepository) as T

}