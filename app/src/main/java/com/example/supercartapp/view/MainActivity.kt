package com.example.supercartapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.supercartapp.R
import com.example.supercartapp.databinding.ActivityMainBinding
import com.example.supercartapp.model.local.preferences.LoginPreferences
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.ProductRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.show
import com.example.supercartapp.view.auth.AuthActivity
import com.example.supercartapp.viewmodel.SearchProductViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val searchViewModel: SearchProductViewModel by viewModels {
        val repository = ProductRepositoryImpl(ApiClient.apiService)

        SearchProductViewModel.SearchViewModelFactory(repository)
    }
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
        setUpBottomNav()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController

        setUpToolbar()
        setUpSideNav()
        setUpObserver()
    }

    private fun setUpObserver() {
        searchViewModel.searchState.observe(this){ state ->
            with(binding){
                when(state){
                    is UiState.Loading -> {
                        pbMainActivity.show()
                    }
                    is UiState.Error ->{
                        pbMainActivity.hide()
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success -> {
                        binding.etSearchField.hide()
                        binding.imgBtnSearchInsideSearchEt.hide()
                        binding.imgBtnCloseInsideSearchEt.hide()
                        val bundle = Bundle()
                        bundle.putInt("productId", state.data[0].productId.toInt())
                        pbMainActivity.hide()
                        navController.navigate(R.id.product_details_fragment,bundle)
                    }
                }
            }
        }
    }

    private fun setUpToolbar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            with(binding.toolbar){
                when(destination.id){

                    R.id.home_fragment -> {
                        title = "SUPER CART"
                        navigationIcon = null
                        menu.findItem(R.id.search_menu)?.isVisible = true
                    }

                    R.id.product_list_fragment -> {
                        title = "Products"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.product_details_fragment -> {
                        title = "Product Details"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.cartFragment -> {
                        title = "Cart"
                        navigationIcon = null
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.finalCartFragment -> {
                        title = "Checkout"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.deliveryAddressFragment -> {
                        title = "Checkout"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.paymentFragment -> {
                        title = "Checkout"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.orderSummaryFragment -> {
                        title = "Checkout"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.orderConfirmationFragment -> {
                        title = "Order Confirmation"
                        setNavigationIcon(R.drawable.back_icon)
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }

                    R.id.orderListFragment -> {
                        title = "Orders"
                        navigationIcon = null
                        menu.findItem(R.id.search_menu)?.isVisible = false
                    }
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            if(navController.currentDestination?.id == R.id.orderConfirmationFragment){
                navController.navigate(
                    R.id.action_orderConfirmationFragment_to_home_fragment
                )
            }else{
                navController.navigateUp()
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search_menu -> {
                    binding.etSearchField.show()
                    binding.imgBtnSearchInsideSearchEt.show()
                    binding.imgBtnCloseInsideSearchEt.show()
                    true
                }
                else -> false
            }
        }

        binding.imgBtnCloseInsideSearchEt.setOnClickListener {
            binding.etSearchField.text?.clear()
            binding.etSearchField.hint = "Search for any Product"
            binding.etSearchField.hide()
            binding.imgBtnSearchInsideSearchEt.hide()
            binding.imgBtnCloseInsideSearchEt.hide()
        }

        binding.imgBtnSearchInsideSearchEt.setOnClickListener {
            val searchText = binding.etSearchField.text.toString().trim()
            if(searchText.isNotEmpty()){
                searchViewModel.searchProduct(searchText)
            }else{
                binding.etSearchField.hint = "Please enter a product to search"
            }
        }
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

    private fun setUpSideNav() {
        val headerView = binding.nvSideNav.getHeaderView(0)

        val tvUserName = headerView.findViewById<TextView>(R.id.tv_user_name)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tv_user_email)
        val tvUserPhone = headerView.findViewById<TextView>(R.id.tv_user_phone)

        tvUserName.text = "Welcome ${LoginPreferences.getUserName()}"
        tvUserEmail.text = LoginPreferences.getUserEmail()
        tvUserPhone.text = LoginPreferences.getUserPhone()

        binding.nvSideNav.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.order_menu -> {
                    navController.navigate(R.id.nav_order)
                    binding.main.closeDrawer(GravityCompat.END)
                    true
                }

                R.id.logout_menu -> {
                    LoginPreferences.logout()

                    startActivity(
                        Intent(this, AuthActivity::class.java)
                    )

                    finish()
                    true
                }

                else -> false
            }
        }
    }
}