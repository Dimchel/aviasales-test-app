package com.dimchel.aviasalestestapp.api.mappers

import com.dimchel.aviasalestestapp.api.schemes.CityResponseScheme
import com.dimchel.aviasalestestapp.api.schemes.LocationResponseScheme
import com.dimchel.aviasalestestapp.data.models.CityModel
import com.dimchel.aviasalestestapp.data.models.LocationModel

fun CityResponseScheme.mapToCityModel() =
    CityModel(
        fullname,
        city,
        location.mapToLocationModel()
    )

fun List<CityResponseScheme>.mapToCityModelList() =
    this.map { it.mapToCityModel() }

fun LocationResponseScheme.mapToLocationModel() =
    LocationModel(lat, lon)