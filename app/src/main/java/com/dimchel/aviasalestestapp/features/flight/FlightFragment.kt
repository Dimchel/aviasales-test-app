package com.dimchel.aviasalestestapp.features.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dimchel.aviasalestestapp.AviasalesApp
import com.dimchel.aviasalestestapp.R
import com.dimchel.aviasalestestapp.data.FlightRepository
import com.dimchel.aviasalestestapp.features.search.SearchFragment.Companion.ARGUMENT_IS_DEPARTURE_POINT_SEARCH
import com.dimchel.aviasalestestapp.utils.navController
import kotlinx.android.synthetic.main.fragment_flight.*

class FlightFragment : Fragment() {

	private val repository: FlightRepository = AviasalesApp.getFlightRepository()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.fragment_flight, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		flight_departure_point_edittext.setOnClickListener {
			val bundle = Bundle().apply {
				putBoolean(ARGUMENT_IS_DEPARTURE_POINT_SEARCH, true)
			}
			navController().navigate(R.id.action_flightFragment_to_searchFragment, bundle)
		}

		flight_destination_edittext.setOnClickListener {
			val bundle = Bundle().apply {
				putBoolean(ARGUMENT_IS_DEPARTURE_POINT_SEARCH, false)
			}
			navController().navigate(R.id.action_flightFragment_to_searchFragment, bundle)
		}

		flight_apply_button.setOnClickListener {
			if (repository.departureCity != null && repository.destinationCity != null) {

			}
		}
	}
}