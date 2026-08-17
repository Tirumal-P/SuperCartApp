package com.example.supercartapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.supercartapp.R
import com.example.supercartapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        setUpSideNav()
        setUpBottomNav()
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment_container_view, ProductDetailsFragment())
//            .commit()

    }

    private fun setUpBottomNav() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bnBottomNav.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.home_menu -> {
                    navController.navigate(R.id.home_fragment)
                    true
                }

                R.id.cart_menu -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }

                R.id.menu_menu -> {
                    if(binding.main.isDrawerOpen(GravityCompat.END)){
                        binding.main.closeDrawer(GravityCompat.END)
                    }else{
                        binding.main.openDrawer(GravityCompat.END)
                    }
                    true
                }

                else -> false
            }
        }
    }

//    private fun setUpSideNav() {
//        with(binding) {
//            bnBottomNav.setOnItemSelectedListener { menu ->
//                when (menu.itemId) {
//                    R.id.menu_menu -> {
//                        if (main.isDrawerOpen(GravityCompat.END)) {
//                            main.closeDrawer(GravityCompat.END)
//                        }else{
//                            main.openDrawer(GravityCompat.END)
//                        }
//                    }
//                }
//                true
//            }
//        }
//    }
}