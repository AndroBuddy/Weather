package com.orca.weather

import ViewModelFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.api.RetrofitService
import com.orca.weather.repository.Repository
import com.orca.weather.ui.screens.WeatherScreen
import com.orca.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)
        viewModel.weatherList.observe(this, Observer { result ->
            try {

                // PUT YOUR WORKING CODE HERE. IF USER CHANGES THE CITY NAME, THE RESPONSE IS STORED IN RESULT.
                // THE RESULT CAN BE RETRIEVED EASILY BY JUST REFERRING TO THE PARTICULAR DATA CLASS.
                // AN EXAMPLE IS GIVEN BELOW :

                Log.e("MAINACTIVITY", result.coord.lat.toString())
                // This shows Mumbai's latitude
                Toast.makeText(this, result.coord.toString() , Toast.LENGTH_LONG).show()

            }
            catch(e: Exception){
                // THIS IS DISPLAYED WHENEVER USER INPUTS AN INVALID CITY NAME
                Log.e("MAINACTIVITY", "Invalid Input of City Name")
            }
        })

        viewModel.getCurrentWeatherData("Mumbai") // CALLING THE API

        setContent {
            WeatherTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    WeatherScreen()
}
