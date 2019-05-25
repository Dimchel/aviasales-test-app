package com.dimchel.aviasalestestapp.utils

import com.dimchel.aviasalestestapp.models.NavigationPointModel
import com.google.android.gms.maps.model.LatLng
import java.util.ArrayList

object NavigationUtils {

	private fun getGreatCirclePath(startPoint: LatLng, endPoint: LatLng, numberOfPoints:Int)
		: ArrayList<NavigationPointModel> {

		val result = ArrayList<NavigationPointModel>()

		val lat1 = startPoint.latitude * Math.PI / 180
		val lon1 = startPoint.longitude * Math.PI / 180
		val lat2 = endPoint.latitude * Math.PI / 180
		val lon2 = endPoint.longitude * Math.PI / 180

		val d = (2 * Math.asin(
			Math.sqrt(
				Math.pow(Math.sin((lat1 - lat2) / 2), 2.0)
					+ (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon1 - lon2) / 2), 2.0))
			)
		))

		for (i in 0..numberOfPoints) {
			val f = 1.0 / numberOfPoints * i
			val a = Math.sin((1 - f) * d) / Math.sin(d)
			val b = Math.sin(f * d) / Math.sin(d)
			val x = a * Math.cos(lat1) * Math.cos(lon1) + b * Math.cos(lat2) * Math.cos(lon2)
			val y = a * Math.cos(lat1) * Math.sin(lon1) + b * Math.cos(lat2) * Math.sin(lon2)
			val z = a * Math.sin(lat1) + b * Math.sin(lat2)

			var latN = Math.atan2(z, Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)))
			var lonN = Math.atan2(y, x)

			latN /= (Math.PI / 180)
			lonN /= (Math.PI / 180)

			val bearing = calculateBearing(latN, lonN, lat2, lon2)

			result.add(NavigationPointModel(LatLng(latN, lonN), bearing))
		}
		return result
	}

	private fun calculateBearing(startLat: Double, startLng: Double, endLat: Double, endLng: Double): Double {
		val bearing = Math.atan2(
			Math.sin(startLng - endLng) * Math.cos(endLat),
			Math.cos(startLat) * Math.sin(endLat) - Math.sin(startLat) * Math.cos(
				endLat
			) * Math.cos(startLng - endLng)
		) / -(Math.PI / 180)

		return if (bearing < 0) 360 + bearing else bearing
	}
}