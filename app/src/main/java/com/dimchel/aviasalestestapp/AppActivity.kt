package com.dimchel.aviasalestestapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dimchel.aviasalestestapp.utils.navController

class AppActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean =
		when (item.itemId) {
			android.R.id.home -> {
				navController().popBackStack()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
}
