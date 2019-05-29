package com.dimchel.aviasalestestapp.features.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dimchel.aviasalestestapp.AviasalesApp
import com.dimchel.aviasalestestapp.R
import com.dimchel.aviasalestestapp.features.search.SearchFragment.Companion.ARGUMENT_IS_DEPARTURE_POINT_SEARCH
import com.dimchel.aviasalestestapp.utils.navController
import com.dimchel.aviasalestestapp.utils.viewModel
import kotlinx.android.synthetic.main.fragment_flight.*

class FlightFragment : Fragment() {

	private val viewModel: FlightViewModel by viewModel(
		FlightViewModel::class.java,
		FlightViewModelFactory(AviasalesApp.getFlightRepository())
	)

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.fragment_flight, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
		actionBar?.setDisplayHomeAsUpEnabled(false)
		actionBar?.title = "Выберите маршрут"

		initViews()

		subscribeData()
	}

	private fun initViews() {
		flight_departure_city_button.setOnClickListener {
			val bundle = Bundle().apply {
				putBoolean(ARGUMENT_IS_DEPARTURE_POINT_SEARCH, true)
			}
			navController().navigate(R.id.action_flightFragment_to_searchFragment, bundle)
		}

		flight_destination_city_button.setOnClickListener {
			val bundle = Bundle().apply {
				putBoolean(ARGUMENT_IS_DEPARTURE_POINT_SEARCH, false)
			}
			navController().navigate(R.id.action_flightFragment_to_searchFragment, bundle)
		}

		flight_apply_button.setOnClickListener {
			viewModel.onApplyAction()
		}
	}

	private fun subscribeData() {
		viewModel.departureCity.observe(viewLifecycleOwner, Observer {
			if (it != null) {
				flight_departure_city_button.text = it
			} else {
				flight_departure_city_button.text = "Куда лететь"
			}
		})

		viewModel.destinationCity.observe(viewLifecycleOwner, Observer {
			if (it != null) {
				flight_destination_city_button.text = it
			} else {
				flight_destination_city_button.text = "Откуда вылет"
			}
		})

		viewModel.getNavigationState().observe(viewLifecycleOwner, Observer {
			navController().navigate(R.id.action_flightFragment_to_loadingFragment)
		})
	}
}