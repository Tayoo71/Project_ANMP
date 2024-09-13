package com.example.project_anmp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.project_anmp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dapatkan NavController dari NavHostFragment
        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment)
                as NavHostFragment).navController

        // Tentukan fragment yang merupakan top-level destinations agar disertakan untuk hamburger icon
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.itemWhatWePlay, R.id.itemWhoWeAre, R.id.itemOurSchedule),
            binding.drawerLayout
        )

        // Setup ActionBar dengan NavController dan AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Setup Navigation Drawer
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Setup Bottom Navigation
        binding.bottomNav.setupWithNavController(navController)
    }

    // Pastikan navigasi up bekerja dengan AppBarConfiguration
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}
