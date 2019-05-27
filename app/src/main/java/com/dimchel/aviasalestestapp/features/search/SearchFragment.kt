package com.dimchel.aviasalestestapp.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimchel.aviasalestestapp.AviasalesApp
import com.dimchel.aviasalestestapp.R
import com.dimchel.aviasalestestapp.utils.navController
import com.dimchel.aviasalestestapp.utils.viewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

	private val viewModel: SearchViewModel by viewModel(
		SearchViewModel::class.java,
		SearchViewModelFactory(AviasalesApp.getFlightRepository())
	)
	private lateinit var searchHintsAdapter: SearchHintsAdapter

	private var isDeparturePointSearch: Boolean = false

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
		inflater.inflate(R.layout.fragment_search, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		prepareScreen()

		initRecyclerView()

		search_search_edittext.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(s: Editable?) = Unit
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
			override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
				viewModel.onQuery(text.toString())
			}
		})

		viewModel.getHintsList().observe(viewLifecycleOwner, Observer { hintsList ->
			searchHintsAdapter.updateData(hintsList.map { it.fullname })
		})
	}

	private fun prepareScreen() {
		isDeparturePointSearch = arguments!!.getBoolean(ARGUMENT_IS_DEPARTURE_POINT_SEARCH)

		val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
		if (isDeparturePointSearch) {
			actionBar?.title = "Откуда вылет"
		} else {
			actionBar?.title = "Куда лететь"
		}
		actionBar?.setDisplayHomeAsUpEnabled(true)
	}

	private fun initRecyclerView() {
		search_recyclerview.layoutManager = LinearLayoutManager(context)
		search_recyclerview.setHasFixedSize(true)

		searchHintsAdapter = SearchHintsAdapter {
			if (isDeparturePointSearch) {
				viewModel.onDepartureHintSelected(it)
			} else {
				viewModel.onDestinationHintSelected(it)
			}

			navController().popBackStack()
		}
		search_recyclerview.adapter = searchHintsAdapter
	}

	companion object {
		const val ARGUMENT_IS_DEPARTURE_POINT_SEARCH = "3f2ade0d-0626-4c96-884a-1cb3235a342b"
	}
}