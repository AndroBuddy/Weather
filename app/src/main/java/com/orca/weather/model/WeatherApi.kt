package com.orca.weather.model

data class WeatherApi(
    val base: String,
    val clouds: Clouds,
    val cod: Double,
    val coord: Coord,
    val dt: Double,
    val id: Double,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Double,
    val visibility: Double,
    val weather: List<Weather>,
    val wind: Wind
)