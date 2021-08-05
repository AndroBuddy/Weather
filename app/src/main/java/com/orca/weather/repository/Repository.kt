package com.orca.weather.repository

import com.orca.weather.api.RetrofitService

class Repository(private val retrofitService : RetrofitService){

    fun getCurrentWeatherData(city: String) = retrofitService.getCurrentWeatherData(city)
}