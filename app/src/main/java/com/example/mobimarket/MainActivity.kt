package com.example.mobimarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobimarket.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val  navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_menu)
        bottomNav.background = null

        bottomNav.setupWithNavController(navController)

        binding.floating.setOnClickListener {
            navController.navigate(R.id.addFragment)
        }


        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.userFragment|| destination.id == R.id.chatFragment
                || destination.id == R.id.walletFragment|| destination.id == R.id.homeFragment) {
                bottomNav.visibility = View.VISIBLE
                bottomAppBar.visibility = View.VISIBLE
                floatingActionButton.visibility = View.VISIBLE
            } else {
                bottomNav.visibility = View.GONE
                bottomAppBar.visibility = View.GONE
                floatingActionButton.visibility = View.GONE
            }
        }
    }
}