package com.dimchel.aviasalestestapp.data

import androidx.lifecycle.MutableLiveData
import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.api.AviasalesApiProvider
import com.dimchel.aviasalestestapp.api.mapToOtherApiResult
import com.dimchel.aviasalestestapp.api.mappers.mapToCityModelList
import com.dimchel.aviasalestestapp.data.models.CityModel
import com.dimchel.aviasalestestapp.utils.default

class FlightRepositoryImpl(private val aviasalesApiProvider: AviasalesApiProvider) : FlightRepository {

	override val departureCity: MutableLiveData<CityModel?>
		= MutableLiveData<CityModel?>().default(null)
	override val destinationCity: MutableLiveData<CityModel?>
		= MutableLiveData<CityModel?>().default(null)

	override suspend fun getHints(query: String, language: String): ApiResult<List<CityModel>> =
		aviasalesApiProvider.autocomplete(query, language).mapToOtherApiResult {
            it.cities.mapToCityModelList()
        }
}