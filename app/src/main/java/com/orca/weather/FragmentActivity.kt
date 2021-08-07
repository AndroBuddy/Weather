package com.orca.weather

import ViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.orca.weather.ViewModel.MainViewModel
import com.orca.weather.api.RetrofitService
import com.orca.weather.repository.Repository
import java.util.*
import kotlin.math.roundToInt

class ToolbarFragment : Fragment() {
    private var mAppBarState = 0
    private var viewContactsBar: AppBarLayout? = null
    private var searchBar: AppBarLayout? = null

    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    @SuppressLint("SetTextI18n")
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        val reset = view.findViewById<ImageButton>(R.id.location_search)
        val editText = view.findViewById<EditText>(R.id.tv_editText)

        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(MainViewModel::class.java)

        val textView = view.findViewById<TextView>(R.id.textView)
        val weatherView = view.findViewById<TextView>(R.id.text2)

        val city = view.findViewById<TextView>(R.id.location)
        val feelsLike = view.findViewById<TextView>(R.id.feels_like)

        val humidity = view.findViewById<TextView>(R.id.humidity)
        val pressure = view.findViewById<TextView>(R.id.pressure)
        val wind = view.findViewById<TextView>(R.id.wind)

        viewModel.weatherList.observe(viewLifecycleOwner, { result ->
            try {
                Log.e("MAINACTIVITY", result.coord.lon.toString())
                val temp = result.main.temp.roundToInt()
                val feelsLikeVal = result.main.feels_like.roundToInt()
                val humid = result.main.humidity.roundToInt()
                val pressureVal = result.main.pressure.roundToInt()
                val windVal = result.wind.speed

                city!!.text = result.name + ", " + result.sys.country
                feelsLike!!.text = "$feelsLikeVal°"
                textView!!.text = "$temp°"
                weatherView!!.text = result.weather[0].description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }

                humidity!!.text = "$humid %"
                pressure!!.text = "$pressureVal mBar"
                wind!!.text = "$windVal Km/h"
            }
            catch(e: Exception) {
                Log.e("MAINACTIVITY", "Invalid Input of City Name")
            }
        })

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
}