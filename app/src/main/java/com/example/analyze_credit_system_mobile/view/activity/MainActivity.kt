package com.example.analyze_credit_system_mobile.view.activity

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.ActivityMainBinding
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.view.enums.Status
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.view.model.CreditExhibitionView
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.createNavigationPending
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.createNotification
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.formatCurrency
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.toastAlert
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val loginViewModel by viewModels<LoginViewModel>()
    private val creditViewModel by viewModels<CreateCreditViewModel>()
    private lateinit var  navController : NavController
    private lateinit var  appbar : AppBarConfiguration

    private  var listNotificado = mutableListOf<CreditExhibitionView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       menuToolbar()
        initNavHostfragment()
        configBottomView()

        CoroutineScope(Dispatchers.Main).launch {
            repeat(3232) {
                delay(3000)
                checkChangeAccount()
            }
        }
    }


    private fun checkChangeAccount(){
         loginViewModel.authenticationState.observe(this){state->
             when(state){
                 is AuthenticationState.Logged ->{
                     creditViewModel.getAllOrderCreditFromCustomer(state.customerView.id)
                     creditViewModel.listCredit.observe(this){listCredit->
                         if (listCredit.isNotEmpty()){
                             listCredit.filter {creditiVew ->
                               if ((creditiVew.status != Status.IN_PROGRESS) &&
                                   !listNotificado.contains(creditiVew)
                                   ){
                                    sendNotification(creditiVew)
                                   listNotificado.add(creditiVew)
                                   Log.i("INFO_", "checkChangeAccount: item ${listNotificado.size}")
                               }
                                 return@filter true
                             }
                             false
                         }

                     }

                 }
                 else -> {}
             }
         }
    }
    private fun initNavHostfragment(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewNavHost) as NavHostFragment

        navController = navHostFragment.navController


       //binding.toolbar.setupWithNavController(navController)


       navController.addOnDestinationChangedListener{ _,destination,_ ->
//            if (
//                destination.id == R.id.createCreditFragment ||
//                destination.id == R.id.home_fragment ||
//                destination.id == R.id.mainFragment
//                )
//            {binding.toolbar.visibility = View.GONE}
//           else binding.toolbar.visibility = View.VISIBLE

            if (
                destination.id == R.id.cadastroFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.createCreditDateFragment
            )    binding.bottomNavigation.visibility =View.GONE
            else binding.bottomNavigation.visibility =View.VISIBLE

        }

    }
    private fun configBottomView(){
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
                         loginViewModel.logout()
                         true
                     }
                     else->true
                 }
            }
        })
    }
    fun sendNotification(creditExhibitionView: CreditExhibitionView){
        val pending = applicationContext.createNavigationPending(R.id.home_fragment,R.navigation.navigation_user_accers,null)
       val notification = applicationContext.createNotification(
            title = "Status do seu Pedido",
            contentText = "Empréstimo no valor de ${creditExhibitionView.creditValue.formatCurrency()} foi ${creditExhibitionView.status.state}",
            chaneId = Consts.CHANNEL_ID,
            icon = R.drawable.attach_money_24,
            pendingIntent = pending
        ).build()
        with(NotificationManagerCompat.from(this)){
                    if (ActivityCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding                     //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    notify(Consts.NOTIFICATION_ID,notification)
                }

            }

}