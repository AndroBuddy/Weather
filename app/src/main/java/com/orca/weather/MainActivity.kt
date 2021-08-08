package com.orca.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private val TAG = "PermissionRequest"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setupPermissions()

    }

    private fun init() {
        val fragment = ToolbarFragment(this)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back.
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Location permission denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }
}
