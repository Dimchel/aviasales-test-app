package com.dimchel.aviasalestestapp.utils

import android.util.Log
import com.dimchel.aviasalestestapp.features.loading.NavigationModel
import com.dimchel.aviasalestestapp.features.loading.NavigationPointModel
import com.google.android.gms.maps.model.LatLng
import java.lang.Math.*

object NavigationUtils {

	private const val NUMBER_OF_POINTS = 30
	private const val EARTH_RADIUS_IN_KILOMETERS = 6371
	private const val PATH_POINTS_CALCULATION_FACTOR = 4

	fun getGreatCirclePath(startPoint: LatLng, endPoint: LatLng): NavigationModel {

		val result: MutableList<NavigationPointModel> = arrayListOf()

		val lat1 = startPoint.latitude * PI / 180
		val lon1 = startPoint.longitude * PI / 180
		val lat2 = endPoint.latitude * PI / 180
		val lon2 = endPoint.longitude * PI / 180

		val distance = calculateDistance(lat1, lon1, lat2, lon2)

		val d = (2 * asin(
			sqrt(
				pow(sin((lat1 - lat2) / 2), 2.0)
					+ (cos(lat1) * cos(lat2) * pow(sin((lon1 - lon2) / 2), 2.0))
			)
		))

		for (i in 0..NUMBER_OF_POINTS) {
			val f = 1.0 / NUMBER_OF_POINTS * i
			val a = sin((1 - f) * d) / sin(d)
			val b = sin(f * d) / sin(d)
			val x = a * cos(lat1) * cos(lon1) + b * cos(lat2) * cos(lon2)
			val y = a * cos(lat1) * sin(lon1) + b * cos(lat2) * sin(lon2)
			val z = a * sin(lat1) + b * sin(lat2)

			var latN = atan2(z, sqrt(pow(x, 2.0) + pow(y, 2.0)))
			var lonN = atan2(y, x)

			val bearing = calculateBearing(latN, lonN, lat2, lon2)

			latN /= (PI / 180)
			lonN /= (PI / 180)

			result.add(NavigationPointModel(LatLng(latN, lonN), bearing))
		}
		val sizeOfPointsInMeters = (distance / NUMBER_OF_POINTS / PATH_POINTS_CALCULATION_FACTOR * 1000).toInt()
		Log.v("123123", "number: $NUMBER_OF_POINTS  distance: $distance  size: $sizeOfPointsInMeters")

		return NavigationModel(sizeOfPointsInMeters, result)
	}

	private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double) : Double {
		val earthRadiusKm = EARTH_RADIUS_IN_KILOMETERS
		val diffLat = lat2-lat1
		val diffLon = lon2-lon1
		val a = sin(diffLat/2) * sin(diffLat/2) + sin(diffLon / 2) * sin(diffLon / 2) * cos(lat1) * cos(lat2)
		val c = 2 * atan2(sqrt(a), sqrt(1-a))
		return earthRadiusKm * c
	}

	private fun calculateBearing(startLat: Double, startLng: Double, endLat: Double, endLng: Double): Double {
		val bearing = atan2(
			sin(startLng - endLng) * cos(endLat),
			cos(startLat) * sin(endLat) - sin(startLat) * cos(endLat) * cos(startLng - endLng)
		) / -(PI / 180)

		return if (bearing < 0) 360 + bearing else bearing
	}
}