package com.orca.weather

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.orca.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun init() {
        val fragment = ToolbarFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        // replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back.
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
