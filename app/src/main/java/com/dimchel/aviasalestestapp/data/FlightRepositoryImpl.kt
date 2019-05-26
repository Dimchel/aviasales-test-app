package com.dimchel.aviasalestestapp.data

import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.AviasalesApiProvider
import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme

class FlightRepositoryImpl(private val aviasalesApiProvider: AviasalesApiProvider) : FlightRepository {

	private var departureCityValue: CityResponseScheme? = null
	private var destinationCityValue: CityResponseScheme? = null

	override var departureCity: CityResponseScheme?
		get() = departureCityValue
		set(value) { departureCityValue = value }

	override var destinationCity: CityResponseScheme?
		get() = destinationCityValue
		set(value) { destinationCityValue = value }

	override suspend fun getHints(query: String, language: String): ApiResult<AutocompleteResponseScheme> =
		aviasalesApiProvider.autocomplete(query, language)

}