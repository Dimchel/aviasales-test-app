package com.dimchel.aviasalestestapp.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme
import com.dimchel.aviasalestestapp.data.FlightRepository
import kotlinx.coroutines.launch

class SearchViewModel(
	private val flightRepository: FlightRepository
) : ViewModel() {

	private val hintsList: MutableLiveData<List<CityResponseScheme>> =
		MutableLiveData<List<CityResponseScheme>>().apply { arrayListOf<CityResponseScheme>() }

	fun onQuery(query: String) {
		if (query.length < MIN_QUERY_LENGTH) return

		viewModelScope.launch {
			val result = flightRepository.getHints(query, "en")

			if (result is ApiResult.ApiSuccess) {
				hintsList.value = result.result.cities
			}
		}
	}

	fun onDepartureHintSelected(selectedHint: String) {
		val selectedCity = hintsList.value!!.find { it.fullname == selectedHint }

		if (selectedCity != null) {
			flightRepository.departureCity = selectedCity
		}
	}

	fun onDestinationHintSelected(selectedHint: String) {
		val selectedCity = hintsList.value!!.find { it.fullname == selectedHint }

		if (selectedCity != null) {
			flightRepository.destinationCity = selectedCity
		}
	}

	fun getHintsList(): LiveData<List<CityResponseScheme>> = hintsList

	companion object {
		private const val MIN_QUERY_LENGTH = 3
	}
}