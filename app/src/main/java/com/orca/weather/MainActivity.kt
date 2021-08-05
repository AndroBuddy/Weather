package com.orca.weather

import ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.api.RetrofitService
import com.orca.weather.databinding.ActivityMainBinding
import com.orca.weather.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)

        var textView = findViewById<TextView>(R.id.textView)
        var button = findViewById<Button>(R.id.button)
        var tv_editText = findViewById<TextView>(R.id.tv_editText)

        viewModel.weatherList.observe(this, Observer { result ->
            try {
//            Log.e("MAINACTIVITY" , viewModel.weatherList.value?.coord.toString())
                Log.e("MAINACTIVITY", result.coord.lon.toString())
                textView!!.text = result.coord.lon!!.toString()
            }catch(e: Exception){
                Log.e("MAINACTIVITY", "Invalid Input of City Name")
            }
        })

        button.setOnClickListener {
            viewModel.getCurrentWeatherData(tv_editText.text.toString())
        }
    }
}