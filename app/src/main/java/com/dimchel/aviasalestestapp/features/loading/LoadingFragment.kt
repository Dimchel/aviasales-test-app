package com.dimchel.aviasalestestapp.features.loading

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Path
import android.graphics.Point
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
import kotlinx.android.synthetic.main.fragment_loading.*


class LoadingFragment : Fragment() {

	private val departureCity = AviasalesApp.getFlightRepository().departureCity.value!!
	private val destinationCity = AviasalesApp.getFlightRepository().destinationCity.value!!

	private var navigationPointsList: List<NavigationPointModel> = arrayListOf()
	private val pathPoints: MutableList<Point> = arrayListOf()
	private val currentPlanePosition = 0

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

		navigationPointsList = NavigationUtils.getGreatCirclePath(departureLocation, destinationLocation)

		navigationPointsList.forEach {
			googleMap.addCircle(
				CircleOptions()
					.center(it.departurePoint)
					.clickable(false)
					.fillColor(R.color.colorAccent)
					.strokeWidth(0f)
					.radius(POINT_RADIUS_IN_METERS)
			)

			val point: Point = googleMap.projection.toScreenLocation(it.departurePoint)
			pathPoints.add(point)
		}

		startAnimation()
	}

	private fun startAnimation() {
		val movePath = Path()
		movePath.moveTo(pathPoints[0].x.toFloat(), pathPoints[0].y.toFloat())
		for (i in 1 until pathPoints.size) {
			movePath.lineTo(pathPoints[i].x.toFloat(), pathPoints[i].y.toFloat())
		}

		val rotationKeyFrames = arrayOfNulls<PropertyValuesHolder>(navigationPointsList.size)
		navigationPointsList.forEachIndexed { i, it ->
			Log.v("123123", "1: " + it.bearing)
			rotationKeyFrames[i] = PropertyValuesHolder.ofFloat("rotation", it.bearing.toFloat())
		}

		val animatorSet = AnimatorSet()
		animatorSet.playTogether(
			ObjectAnimator
				.ofFloat(search_plane_imageview, "x", "y", movePath).apply {
					duration = ANIMATION_DURATION
				},
			ObjectAnimator
				.ofFloat(search_plane_imageview, "rotation", navigationPointsList.first().bearing.toFloat(), navigationPointsList[5].bearing.toFloat()).apply {
					duration = ANIMATION_DURATION
				}
		)
		animatorSet.start()
	}

	companion object {
		const val ANIMATION_DURATION = 10000L
		const val POINT_RADIUS_IN_METERS = 6000.0
	}
}