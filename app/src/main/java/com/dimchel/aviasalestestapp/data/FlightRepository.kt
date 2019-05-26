package com.dimchel.aviasalestestapp.data

import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme

interface FlightRepository {

	var departureCity: CityResponseScheme?
	var destinationCity: CityResponseScheme?

	suspend fun getHints(query: String, language: String): ApiResult<AutocompleteResponseScheme>

}