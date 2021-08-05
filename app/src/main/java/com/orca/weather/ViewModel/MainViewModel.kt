package com.orca.weather.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orca.weather.model.WeatherApi
import com.orca.weather.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel (private val repository: Repository) : ViewModel(){

    val weatherList = MutableLiveData<WeatherApi>()
    val errorMessage = MutableLiveData<String>()

    fun getCurrentWeatherData(city : String = "Durgapur"){
        val response = repository.getCurrentWeatherData(city)

        response?.enqueue(object : Callback<WeatherApi>{
            override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                weatherList.postValue(response.body())
                if (response.isSuccessful)
                    Log.e("VIEWMODEL", response.body()!!.coord.toString())
            }
            override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.e("VIEWMODEL", "MESSAGE ON FAILURE : "+t.message.toString())
            }
        })
    }
}