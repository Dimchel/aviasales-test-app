package com.dimchel.aviasalestestapp.features.flight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme
import com.dimchel.aviasalestestapp.data.FlightRepository

class FlightViewModel(private val flightRepository: FlightRepository) : ViewModel() {

	val departureCity: LiveData<String?> =
		Transformations.map(flightRepository.departureCity) { city -> city?.fullname }
	val destinationCity: LiveData<String?> =
		Transformations.map(flightRepository.destinationCity) { city -> city?.fullname }

	private val navigationState: MutableLiveData<NavigationState> = MutableLiveData()

	fun onApplyAction() {
		val departureCity = flightRepository.departureCity.value
		val destinationCity = flightRepository.destinationCity.value

		if (departureCity != null && destinationCity != null) {
			navigationState.value = NavigationState(departureCity, destinationCity)
		}
	}

	fun getNavigationState(): LiveData<NavigationState> = navigationState

	class NavigationState(
		val departureCity: CityResponseScheme,
		val destinationCity: CityResponseScheme
	)
}