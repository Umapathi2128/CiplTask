package com.uma.cipltask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uma.cipltask.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottomNav = findViewById<BottomNavigationView>(R.id.bnView)

        val navController = Navigation.findNavController(this,R.id.navId)
        NavigationUI.setupWithNavController(bottomNav,navController)
    }
}