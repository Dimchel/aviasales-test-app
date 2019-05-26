package com.dimchel.aviasalestestapp.api

import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AviasalesApiProviderImpl(private val apiService: AviasalesService) : AviasalesApiProvider {

	override suspend fun autocomplete(query: String, language: String): ApiResult<AutocompleteResponseScheme> =
		apiService.autocomplete(query, language).await()

	private suspend fun <T> Call<T>.await(): ApiResult<T> =
		suspendCoroutine { continuation ->
			enqueue(object : Callback<T> {
				override fun onResponse(call: Call<T>, response: Response<T>) {
					if (response.isSuccessful) {
						continuation.resume(ApiResult.ApiSuccess(response.body()!!))
					} else {
						continuation.resume(ApiResult.ApiError)
					}
				}

				override fun onFailure(call: Call<T>, t: Throwable) {
					continuation.resume(ApiResult.ApiError)
				}
			})
		}
}