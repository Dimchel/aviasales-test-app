package com.dimchel.aviasalestestapp.data

import androidx.lifecycle.MutableLiveData
import com.dimchel.aviasalestestapp.api.ApiResult
import com.dimchel.aviasalestestapp.data.models.CityModel

interface FlightRepository {

	val departureCity: MutableLiveData<CityModel?>
	val destinationCity: MutableLiveData<CityModel?>

	suspend fun getHints(query: String, language: String): ApiResult<List<CityModel>>

}