package com.dimchel.aviasalestestapp

import android.app.Application
import com.dimchel.aviasalestestapp.api.AviasalesApiProvider
import com.dimchel.aviasalestestapp.api.AviasalesApiProviderImpl
import com.dimchel.aviasalestestapp.api.AviasalesService
import com.dimchel.aviasalestestapp.data.FlightRepository
import com.dimchel.aviasalestestapp.data.FlightRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AviasalesApp : Application() {

	override fun onCreate() {
		super.onCreate()

		initNetwork()
	}

	private fun initNetwork() {
		val loggingInterceptor = HttpLoggingInterceptor()
		loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

		val okHttpClient = OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()

		retrofit = Retrofit.Builder()
			.baseUrl(ApiConstants.API_DOMAIN)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		aviasalesApiProvider = AviasalesApiProviderImpl(retrofit.create(AviasalesService::class.java))

		flightRepository = FlightRepositoryImpl(aviasalesApiProvider)
	}

	companion object {

		private lateinit var flightRepository: FlightRepository
		private lateinit var aviasalesApiProvider: AviasalesApiProvider
		private lateinit var retrofit: Retrofit

		fun getFlightRepository() = flightRepository
	}
}