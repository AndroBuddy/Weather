package com.orca.weather

import android.content.Context

class SharedPreference(context: Context) {

    val PREFERENCE_NAME = "PREFERENCE"
    val PREFERENCE_CITY = "city"
    val PREFERENCE_TEMP = "temp"
    val PREFERENCE_FEELSLIKE = "feels_like"
    val PREFERENCE_HUMID = "humid"
    val PREFERENCE_PRESSURE = "pressure"
    val PREFERENCE_WIND = "wind"
    val PREFERENCE_LAT = "lat"
    val PREFERENCE_LON = "lon"


    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCityName(): String?{
        return preference.getString(PREFERENCE_CITY, null)
    }

    fun setCityName(city : String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_CITY, city)
        editor.apply()
    }

}