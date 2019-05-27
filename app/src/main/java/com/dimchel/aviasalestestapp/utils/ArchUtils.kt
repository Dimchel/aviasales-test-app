package com.dimchel.aviasalestestapp.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dimchel.aviasalestestapp.R

fun <T : ViewModel> Fragment.viewModel(viewModelClass: Class<T>): Lazy<T> =
	lazy { ViewModelProviders.of(this).get(viewModelClass) }

fun <T : ViewModel> Fragment.viewModel(viewModelClass: Class<T>, factory: ViewModelProvider.Factory): Lazy<T> =
	lazy { ViewModelProviders.of(this, factory).get(viewModelClass) }

fun Fragment.navController(): NavController = Navigation.findNavController(activity!!, R.id.fragment_launch)

fun Activity.navController(): NavController = Navigation.findNavController(this, R.id.fragment_launch)

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }