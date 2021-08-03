package com.orca.weather

data class Weather(
    val date: String = "29, April 2021",
    val description: String = "Cooling down with a chance of rain Sunday, Monday & Tuesday.",
    val condition: String = "Thunder",
    val location: String = "MANILA, Philippines",
    val update: List<WeatherUpdate> = weatherList
) {
    data class WeatherUpdate(
        val time: String,
        val temp: String,
        val icon: String
    )
}


val weatherList = arrayListOf(
    Weather.WeatherUpdate("00:00:00", "29.0", "wind"),
    Weather.WeatherUpdate("02:30:00", "26.6", "rain"),
    Weather.WeatherUpdate("04:00:00", "27.8", "thunder"),
    Weather.WeatherUpdate("06:30:00", "29.3", "angledRain"),
    Weather.WeatherUpdate("08:00:00", "25.5", "wind"),
    Weather.WeatherUpdate("10:30:00", "26.8", "angledRain"),
)