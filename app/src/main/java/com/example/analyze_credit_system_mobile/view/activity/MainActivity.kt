package com.example.analyze_credit_system_mobile.view.activity

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.databinding.ActivityMainBinding
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val  auth = FirebaseAuth.getInstance()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var  navController : NavController
    private lateinit var  appbar : AppBarConfiguration

    private lateinit var repository : ICustomerRepository
    private lateinit var service: CustumerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initNavHostfragment()
        configBottomView()
        menuToolbar()
    }

    private fun initNavHostfragment(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewNavHost) as NavHostFragment

        navController = navHostFragment.navController
        appbar =  AppBarConfiguration(setOf(R.id.homeFragment,R.id.createCreditFragment,R.id.mainFragment,R.id.loginFragment))
      
       binding.toolbar.setupWithNavController(navController,appbar)
        navController.addOnDestinationChangedListener{_,destination,_ ->
            if (destination.id == R.id.loginFragment){binding.toolbar.visibility = View.GONE}
            binding.toolbar.visibility = View.VISIBLE
        }

        navController.addOnDestinationChangedListener{ _,destination,_ ->
            if (destination.id == R.id.loginFragment ||
                destination.id == R.id.createCreditFragment
                //destination.id == R.id.homeFragment
                )
            {binding.toolbar.visibility = View.GONE}

        }

    }
    private fun configBottomView(){
      //  binding.bottomNavigation.setBackgroundColor(getColor(R.color.md_theme_light_primary))

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }
    private fun menuToolbar(){
        addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val menu = menuInflater.inflate(R.menu.menu_toolbar,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              return  when(menuItem.itemId){
                     R.id.item_sair->{
                         applicationContext.toastAlert("Saindo")
                         navController.navigate(R.id.mainFragment)
                         auth.signOut()
                         true
                     }
                     else->true
                 }
            }
        })
    }
}