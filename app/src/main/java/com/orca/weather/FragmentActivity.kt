package com.orca.weather

import ViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.AppBarLayout
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.api.RetrofitService
import com.orca.weather.repository.Repository
import java.util.*
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class ToolbarFragment(private val con : Context) : Fragment() {
    private var mAppBarState = 0
    private var viewContactsBar: AppBarLayout? = null
    private var searchBar: AppBarLayout? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    @SuppressLint("SetTextI18n", "MissingPermission")
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val reset = view.findViewById<ImageButton>(R.id.location_search)
        val editText = view.findViewById<EditText>(R.id.tv_editText)

        viewModel = ViewModelProvider(this,
            ViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)


        val textView = view.findViewById<TextView>(R.id.textView)

        val city = view.findViewById<TextView>(R.id.location)
        val feelsLike = view.findViewById<TextView>(R.id.feels_like)

        val humidity = view.findViewById<TextView>(R.id.humidity)
        val pressure = view.findViewById<TextView>(R.id.pressure)
        val wind = view.findViewById<TextView>(R.id.wind)

        val lat = view.findViewById<TextView>(R.id.latitude)
        val long = view.findViewById<TextView>(R.id.longitude)

        val mWeatherIcon = view.findViewById<ImageView>(R.id.weather_icon)


        location()
        viewModel.weatherList.observe(viewLifecycleOwner) { result ->
            try {
                Log.e("MAIN ACTIVITY", result.coord.lon.toString())
                val condition = result.weather[0].description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }

                city!!.text = result.name

                mWeatherIcon.setImageResource(fetchIcon(result.weather[0].main))
                textView!!.text = "${result.main.temp.roundToInt()}°"
                feelsLike!!.text = "Feels like ${result.main.feels_like.roundToInt()}°  ~  $condition"

                humidity!!.text = "${result.main.humidity.roundToInt()} %"
                pressure!!.text = "${result.main.pressure.roundToInt()} mBar"
                wind!!.text = "${result.wind.speed} Km/h"

                lat!!.text = "${(result.coord.lat * 100.0).roundToInt() / 100.0}"
                long!!.text = "${(result.coord.lon * 100.0).roundToInt() / 100.0}"

            } catch (e: Exception) {
                Log.e("MAIN ACTIVITY", "Invalid Input of City Name")
            }
        }

        viewContactsBar = view.findViewById(R.id.viewContactsToolbar)
        searchBar = view.findViewById(R.id.searchToolbar)
        Log.d(TAG, "onCreateView: started")
        setAppBarState(STANDARD_APPBAR)
        val ivSearchContact = view.findViewById<ImageButton>(R.id.ivSearchIcon)
        ivSearchContact.setOnClickListener {
            Log.d(TAG, "onClick: clicked searched icon")
            toggleToolBarState()
        }
        val ivBackArrow = view.findViewById<ImageButton>(R.id.ivBackArrow)
        ivBackArrow.setOnClickListener {
            Log.d(TAG, "onClick: clicked back arrow.")
            toggleToolBarState()
        }

        reset.setOnClickListener {
            editText.text.clear()
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    viewModel.getCurrentWeatherData(editText.text.toString())
                    true
                }
                else -> false
            }
        }
        return view
    }

    private fun fetchIcon(mCondition: String): Int {
        return when (mCondition) {
            "Thunderstorm" -> R.drawable.thunderstorm
            "Drizzle" -> R.drawable.drizzle
            "Rain" -> R.drawable.rain
            "Clouds" -> R.drawable.clouds
            "Clear" -> R.drawable.clear
            "Snow" -> R.drawable.snow
            else -> R.drawable.other
        }
    }

    // Initiate toggle (it means when you click the search icon it pops up the editText and clicking the back button goes to the search icon again)
    private fun toggleToolBarState() {
        Log.d(TAG, "toggleToolBarState: toggling AppBarState.")
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR)
        } else {
            setAppBarState(STANDARD_APPBAR)
        }
    }

    // Sets the appbar state for either search mode or standard mode.
    private fun setAppBarState(state: Int) {
        Log.d(
            TAG,
            "setAppBarState: changing app bar state to: $state"
        )
        mAppBarState = state
        if (mAppBarState == STANDARD_APPBAR) {
            searchBar!!.visibility = View.GONE
            viewContactsBar!!.visibility = View.VISIBLE
            val view: View? = view
            val im =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                if (view != null) {
                    im.hideSoftInputFromWindow(view.windowToken, 0)
                } // make keyboard hide
            } catch (e: NullPointerException) {
                Log.d(
                    TAG,
                    "setAppBarState: NullPointerException: $e"
                )
            }
        } else if (mAppBarState == SEARCH_APPBAR) {
            viewContactsBar!!.visibility = View.GONE
            searchBar!!.visibility = View.VISIBLE
            val im =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0) // make keyboard popup
        }
    }

    override fun onResume() {
        super.onResume()
        setAppBarState(STANDARD_APPBAR)
    }

    companion object {
        private const val TAG = "ToolbarFragment"
        private const val STANDARD_APPBAR = 0
        private const val SEARCH_APPBAR = 1
    }


    @SuppressLint("MissingPermission")
    private fun location() {
        val lm: LocationManager = con.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
        val builder = AlertDialog.Builder(con)
        builder.setTitle("Current Location")
        builder.setMessage("Enable GPS to show weather update of your current location")

        builder.setPositiveButton("Yes"){ _, _ ->
            Toast.makeText(con,"Enable Location Service",Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
            exitProcess(0)
        }
        builder.setNegativeButton("No"){ _, _ ->
            Toast.makeText(con,"Location Service not enabled",Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        }
        else{
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(con)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
            Log.e("LONGITUDE", location?.longitude.toString())
                val geocoder = Geocoder(con, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(location?.latitude!!,
                    location.longitude, 1)
                val cityName: String = addresses[0].locality
                Log.e("CITY:" , cityName)
                viewModel.getCurrentWeatherData(cityName)
        }
    }
}