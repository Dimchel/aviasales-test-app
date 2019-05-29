package com.dimchel.aviasalestestapp.features.loading

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
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

	private val pathPoints: MutableList<Point> = arrayListOf()
	private val currentPlanePosition = 0

	private lateinit var navigationModel: NavigationModel

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

		navigationModel = NavigationUtils.getGreatCirclePath(departureLocation, destinationLocation)

		navigationModel.pointsList.forEach {
			googleMap.addCircle(
				CircleOptions()
					.center(it.departurePoint)
					.clickable(false)
					.fillColor(R.color.colorAccent)
					.strokeWidth(0f)
					.radius(navigationModel.pointSizeMeters.toDouble())
			)

			val point: Point = googleMap.projection.toScreenLocation(it.departurePoint)
			pathPoints.add(point)
		}

		startAnimation()
	}

	private fun startAnimation() {
		val pointsList = navigationModel.pointsList

		val rotateAnimatorSet = AnimatorSet()
		rotateAnimatorSet.playSequentially(
			ObjectAnimator
				.ofFloat(
					search_plane_imageview,
					View.ROTATION,
					pointsList[0].bearing.toFloat(),
					pointsList[pointsList.size / 2].bearing.toFloat()
				).apply {
					duration = ANIMATION_DURATION / 2
					interpolator = LinearInterpolator()
				},
			ObjectAnimator
				.ofFloat(
					search_plane_imageview,
					View.ROTATION,
					pointsList[pointsList.size / 2].bearing.toFloat(),
					pointsList[pointsList.size - 2].bearing.toFloat()
				).apply {
					duration = ANIMATION_DURATION / 2
					interpolator = LinearInterpolator()
				}
		)

		val movePath = Path()
		movePath.moveTo(
			pathPoints[0].x.toFloat() - search_plane_imageview.width / 2,
			pathPoints[0].y.toFloat() - search_plane_imageview.height / 2
		)
		for (i in 1 until pathPoints.size) {
			movePath.lineTo(
				pathPoints[i].x.toFloat() - search_plane_imageview.width / 2,
				pathPoints[i].y.toFloat() - search_plane_imageview.height / 2
			)
		}

		val resultAnimatorSet = AnimatorSet()
		resultAnimatorSet.playTogether(
			ObjectAnimator
				.ofFloat(
					search_plane_imageview,
					View.X,
					View.Y,
					movePath
				).apply {
					duration = ANIMATION_DURATION
					interpolator = LinearInterpolator()
				},
			rotateAnimatorSet
		)

		resultAnimatorSet.addListener(object : Animator.AnimatorListener {
			override fun onAnimationRepeat(animation: Animator?) = Unit
			override fun onAnimationEnd(animation: Animator?) = Unit
			override fun onAnimationCancel(animation: Animator?) = Unit
			override fun onAnimationStart(animation: Animator?) {
				search_plane_imageview.visibility = View.VISIBLE
			}
		})
		resultAnimatorSet.start()
	}

	companion object {
		const val ANIMATION_DURATION = 10000L
	}
}