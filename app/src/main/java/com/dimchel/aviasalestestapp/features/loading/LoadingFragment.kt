package com.dimchel.aviasalestestapp.features.loading

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dimchel.aviasalestestapp.AviasalesApp
import com.dimchel.aviasalestestapp.R
import com.dimchel.aviasalestestapp.utils.NavigationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class LoadingFragment : Fragment() {

	private val departureCity = AviasalesApp.getFlightRepository().departureCity.value!!
	private val destinationCity = AviasalesApp.getFlightRepository().destinationCity.value!!

	private lateinit var googleMap: GoogleMap

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.fragment_loading, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		(activity as AppCompatActivity).supportActionBar?.title = "Loading..."
		(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

		(childFragmentManager.findFragmentById(R.id.search_map_fragment) as SupportMapFragment).getMapAsync {
			googleMap = it
			googleMap.uiSettings.isScrollGesturesEnabled = false
			googleMap.uiSettings.isZoomGesturesEnabled = false
			googleMap.uiSettings.isTiltGesturesEnabled = false
			googleMap.uiSettings.isRotateGesturesEnabled = false
			googleMap.setOnMarkerClickListener { true }

			loadMapData()
		}
	}

	private fun loadMapData() {
		val departureLocation = LatLng(
			departureCity.location.lat,
			departureCity.location.lon
		)
		val destinationLocation = LatLng(
			destinationCity.location.lat,
			destinationCity.location.lon
		)

		val departure = MarkerOptions()
			.title(departureCity.city)
			.position(departureLocation)

		val destination = MarkerOptions()
			.title(departureCity.city)
			.position(destinationLocation)

		val builder = LatLngBounds.Builder()
		builder.include(departure.position)
		builder.include(destination.position)

		googleMap.addMarker(departure)
		googleMap.addMarker(destination)
		googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 256))

		val navigationModel = NavigationUtils.getGreatCirclePath(departureLocation, destinationLocation, NUMBER_OF_PATH_POINTS)

		navigationModel.navigationPoints.forEach {
			googleMap.addCircle(
				CircleOptions()
					.center(it.departurePoint)
					.clickable(false)
					.fillColor(R.color.white)
					.strokeWidth(0f)
					.radius(10000.0)
			)
		}
	}

	companion object {
		const val NUMBER_OF_PATH_POINTS = 30
	}

}