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
    val PREFERENCE_CONDITION = "condition"
    val PREFERENCE_IMAGE = "image"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCityName(): String?{
        return preference.getString(PREFERENCE_CITY, null)
    }

    fun setCityName(city : String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_CITY, city)
        editor.apply()
    }

    fun setTemp(temp: Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_TEMP, temp.toFloat())
        editor.apply()
    }

    fun setFeelsTemp(feelsTemp: Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_FEELSLIKE, feelsTemp.toFloat())
        editor.apply()
    }
    fun setHumid(humid : Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_HUMID, humid.toFloat())
        editor.apply()
    }
    fun setPressure(pressure : Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_PRESSURE, pressure.toFloat())
        editor.apply()
    }
    fun setWind(wind : Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_WIND, wind.toFloat())
        editor.apply()
    }
    fun setCondition(condition : String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_CONDITION, condition)
        editor.apply()
    }
    fun setLat(lat : Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_LAT, lat.toFloat())
        editor.apply()
    }
    fun setLon(lon : Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_LON, lon.toFloat())
        editor.apply()
    }
    fun setImage(id: String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_IMAGE, id)
        editor.apply()
    }


}