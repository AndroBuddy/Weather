package com.orca.weather.api


import com.orca.weather.BuildConfig
import com.orca.weather.model.WeatherApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") city : String?,
        @Query("appid") AppId: String? = "2e65127e909e178d0af311a81f39948c" //Will have to create own API KEY and put them inside token
    ): Call<WeatherApi>?


    companion object{
        var retrofitService: RetrofitService? = null
        private var URL = "http://api.openweathermap.org/"

        fun getInstance() : RetrofitService {
            if (retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}