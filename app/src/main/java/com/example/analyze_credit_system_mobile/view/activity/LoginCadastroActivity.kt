package com.example.analyze_credit_system_mobile.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.databinding.ActivityLoginCadastroBinding
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class LoginCadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginCadastroBinding.inflate(layoutInflater)
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

    /*override fun onSupportNavigateUp(): Boolean {
         return navController.navigateUp(appbar) || super.onSupportNavigateUp()
    }
*/
    private fun initNavHostfragment(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewNavHost) as NavHostFragment

        navController = navHostFragment.navController
        appbar =  AppBarConfiguration(setOf(R.id.homeFragment,R.id.createCreditFragment,R.id.mainFragment,R.id.loginFragment))
      // setupActionBarWithNavController(navController,appbar)
       binding.toolbar.setupWithNavController(navController,appbar)
    }
    private fun configBottomView(){
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
                         Toast.makeText(this@LoginCadastroActivity, "Saindo", Toast.LENGTH_SHORT).show()
                         //findNavController().navigate(R.id.homeFragment)
                         true
                     }
                     else->true
                 }
            }
        })
    }
}