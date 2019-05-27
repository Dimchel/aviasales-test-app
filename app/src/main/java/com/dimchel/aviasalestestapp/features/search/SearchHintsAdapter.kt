package com.dimchel.aviasalestestapp.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
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
		val diffUtillCallback = HintsDiffUtilCallback(hintsList, newHintsList)

		this.hintsList = newHintsList

		DiffUtil.calculateDiff(diffUtillCallback).dispatchUpdatesTo(listUpdateCallback)
	}

	private val listUpdateCallback = object : ListUpdateCallback {
		override fun onChanged(position: Int, count: Int, payload: Any?) {
			notifyItemRangeChanged(position, count, payload)
		}

		override fun onMoved(fromPosition: Int, toPosition: Int) {
			notifyItemMoved(fromPosition, toPosition)
		}

		override fun onInserted(position: Int, count: Int) {
			notifyItemRangeInserted(position, count)
		}

		override fun onRemoved(position: Int, count: Int) {
			notifyItemRangeRemoved(position, count)
		}
	}

	class SearchHintsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val hintTextView: TextView = itemView as TextView

		fun bind(hintName: String) {
			hintTextView.text = hintName
		}
	}

	class HintsDiffUtilCallback(
		private val oldList: List<String>,
		private val newList: List<String>
	) : DiffUtil.Callback() {

		override fun getOldListSize(): Int = oldList.size
		override fun getNewListSize(): Int = newList.size

		override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
			oldItemPosition == newItemPosition

		override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
			oldItemPosition == newItemPosition
	}
}