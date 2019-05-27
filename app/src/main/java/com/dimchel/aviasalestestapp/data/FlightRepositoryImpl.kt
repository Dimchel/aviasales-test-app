package com.dimchel.aviasalestestapp.data

import androidx.lifecycle.MutableLiveData
import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.AviasalesApiProvider
import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme
import com.dimchel.aviasalestestapp.utils.default

class FlightRepositoryImpl(private val aviasalesApiProvider: AviasalesApiProvider) : FlightRepository {

	override val departureCity: MutableLiveData<CityResponseScheme?>
		= MutableLiveData<CityResponseScheme?>().default(null)
	override val destinationCity: MutableLiveData<CityResponseScheme?>
		= MutableLiveData<CityResponseScheme?>().default(null)

	override suspend fun getHints(query: String, language: String): ApiResult<AutocompleteResponseScheme> =
		aviasalesApiProvider.autocomplete(query, language)

}