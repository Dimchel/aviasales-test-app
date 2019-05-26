package com.dimchel.aviasalestestapp.api

import com.dimchel.aviasalestestapp.ApiConstants
import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AviasalesService {

	@GET(ApiConstants.REQUEST_AUTOCOMPLETE)
	fun autocomplete(@Query("term") query: String, @Query("lang") language: String)
		: Call<AutocompleteResponseScheme>

}