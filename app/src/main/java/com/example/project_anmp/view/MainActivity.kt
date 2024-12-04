package com.example.project_anmp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.project_anmp.R
import com.example.project_anmp.databinding.ActivityMainBinding
import com.example.project_anmp.databinding.NavHeaderBinding
import com.example.project_anmp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Setup navigation
        setupNavigation()

        // Observe username changes
        observeViewModel()

        // Sign Out Listener
        setupSignOutListener()
    }

    private fun observeViewModel(){
        val headerBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0))
        viewModel.username.observe(this, Observer { username ->
            headerBinding.userName.text = "Welcome, $username"
        })
    }

    fun setupNavigation(){
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(destination.id)
        }
    }

    private fun handleDestinationChange(destinationId: Int) {
        when (destinationId) {
            R.id.itemSignIn, R.id.itemSignUp -> {
                supportActionBar?.hide()
                binding.bottomNav.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            else -> {
                supportActionBar?.show()
                binding.bottomNav.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)

                // Validate user session
                validateUserSession()
            }
        }
    }

    private fun validateUserSession() {
        val sharedPreferences = getSharedPreferences("com.example.project_anmp", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        if (username == null) {
            clearLoginData()
            restartAppToSignIn()
        }
        else{
            viewModel.setUsername(username)
        }
    }

    // Pastikan navigasi up bekerja dengan AppBarConfiguration
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Handle SignOut
    private fun setupSignOutListener() {
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemSignOut -> {
                    clearLoginData()
                    restartAppToSignIn()
                    true
                }
                else -> {
                    // Let the navigation system handle other menu items
                    NavigationUI.onNavDestinationSelected(item, navController)
                    binding.drawerLayout.closeDrawer(binding.navView)
                    true
                }
            }
        }
    }

    // Restart app to navigate to sign-in
    private fun restartAppToSignIn() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // Clear login data
    private fun clearLoginData() {
        val sharedPreferences = getSharedPreferences("com.example.project_anmp", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
