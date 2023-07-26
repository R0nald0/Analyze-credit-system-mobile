package com.example.analyze_credit_system_mobile.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.databinding.ActivityLoginCadastroBinding
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginCadastroActivity : AppCompatActivity() {
    private val  auth = FirebaseAuth.getInstance()

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
                         auth.signOut()
                         true
                     }
                     else->true
                 }
            }
        })
    }
}