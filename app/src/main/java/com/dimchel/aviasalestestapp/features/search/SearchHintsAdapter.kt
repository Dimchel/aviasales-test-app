package com.dimchel.aviasalestestapp.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimchel.aviasalestestapp.R

class SearchHintsAdapter(
	private val selectedHintListener: (selectedHint: String) -> Unit
) : RecyclerView.Adapter<SearchHintsAdapter.SearchHintsViewHolder>() {

	private var hintsList: List<String> = arrayListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHintsViewHolder =
		SearchHintsViewHolder(
			LayoutInflater.from(parent.context).inflate(R.layout.item_search_hint, parent, false)
		)

	override fun getItemCount(): Int = hintsList.size

	override fun onBindViewHolder(holder: SearchHintsViewHolder, position: Int) {
		holder.bind(hintsList[position])

		holder.hintTextView.setOnClickListener {
			selectedHintListener(hintsList[position])
		}
	}

	fun updateData(newHintsList: List<String>) {
		this.hintsList = newHintsList

		notifyDataSetChanged()
	}
	class SearchHintsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val hintTextView: TextView = itemView as TextView

		fun bind(hintName: String) {
			hintTextView.text = hintName
		}
	}
}