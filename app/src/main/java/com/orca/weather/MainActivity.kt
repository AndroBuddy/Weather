package com.orca.weather

import ViewModelFactory
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.api.RetrofitService
import com.orca.weather.databinding.ActivityMainBinding
import com.orca.weather.repository.Repository
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)

        val textView = findViewById<TextView>(R.id.textView)
        val weatherView = findViewById<TextView>(R.id.text2)
        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<TextView>(R.id.tv_editText)

        val feelsLike = findViewById<TextView>(R.id.feels_like)

        val humidity = findViewById<TextView>(R.id.humidity)
        val pressure = findViewById<TextView>(R.id.pressure)
        val wind = findViewById<TextView>(R.id.wind)

        viewModel.weatherList.observe(this, { result ->
            try {
                Log.e("MAINACTIVITY", result.coord.lon.toString())
                val temp = result.main.temp
                val feelsLikeVal = result.main.feels_like
                val humid = result.main.humidity.roundToInt()
                val pressureVal = result.main.pressure.roundToInt()
                val windVal = result.wind.speed

                textView!!.text = "$temp°C"
                feelsLike!!.text = "$feelsLikeVal°C"
                weatherView!!.text = result.weather[0].description
                humidity!!.text = "$humid %"
                pressure!!.text = "$pressureVal mBar"
                wind!!.text = "$windVal Km/h"
            }
            catch(e: Exception) {
                Log.e("MAINACTIVITY", "Invalid Input of City Name")
            }
        })

        button.setOnClickListener {
            viewModel.getCurrentWeatherData(editText.text.toString())
        }
    }
}
