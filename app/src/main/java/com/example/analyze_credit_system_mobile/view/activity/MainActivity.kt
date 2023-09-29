package com.example.analyze_credit_system_mobile.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.ActivityMainBinding
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var  navController : NavController
    private lateinit var  appbar : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        menuToolbar()
        initNavHostfragment()
        configBottomView()

    }

    private fun initNavHostfragment(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewNavHost) as NavHostFragment

        navController = navHostFragment.navController
        appbar =  AppBarConfiguration(setOf(
            R.id.home_fragment,
            R.id.createCreditFragment,
            R.id.mainFragment,
            R.id.loginFragment)
        )
      
       binding.toolbar.setupWithNavController(navController,appbar)


        navController.addOnDestinationChangedListener{ _,destination,_ ->
            if (destination.id == R.id.loginFragment ||
                destination.id == R.id.createCreditFragment
                //destination.id == R.id.homeFragment
                )
            {binding.toolbar.visibility = View.GONE}
           else binding.toolbar.visibility = View.VISIBLE
        }

    }
    private fun configBottomView(){
      //  binding.bottomNavigation.setBackgroundColor(getColor(R.color.md_theme_light_primary))

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }
    private fun menuToolbar(){
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val menu = menuInflater.inflate(R.menu.menu_toolbar,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
              return  when(menuItem.itemId){
                     R.id.item_sair->{
                         applicationContext.toastAlert("Saindo")
                         navController.navigate(R.id.mainFragment)
                         loginViewModel.delsogar()
                         true
                     }
                     else->true
                 }
            }
        })
    }
}