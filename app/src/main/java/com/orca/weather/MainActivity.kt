package com.orca.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init() {
        val fragment = ToolbarFragment(this)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back.
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
