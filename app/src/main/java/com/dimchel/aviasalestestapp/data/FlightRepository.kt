package com.dimchel.aviasalestestapp.data

import androidx.lifecycle.MutableLiveData
import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme

interface FlightRepository {

	val departureCity: MutableLiveData<CityResponseScheme?>
	val destinationCity: MutableLiveData<CityResponseScheme?>

	suspend fun getHints(query: String, language: String): ApiResult<AutocompleteResponseScheme>

}