package com.dimchel.aviasalestestapp.features.flight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimchel.aviasalestestapp.data.FlightRepository

class FlightViewModelFactory(
	private val flightRepository: FlightRepository
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = FlightViewModel(flightRepository) as T

}