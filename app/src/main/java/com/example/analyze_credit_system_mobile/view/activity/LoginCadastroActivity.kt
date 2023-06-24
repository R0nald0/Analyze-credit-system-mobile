package com.example.analyze_credit_system_mobile.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
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

        val add = Address("1313","rua d3")
        val cost = Customer(
            "Bob","Silva","56122261602","bob@gmail.com","13412321"
            ,add,244.32.toBigDecimal(), mutableListOf()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
         return navController.navigateUp(appbar) || super.onSupportNavigateUp()
    }

    fun getapi(cutomer : Customer){
        CoroutineScope(Dispatchers.IO).launch {
            var result : retrofit2.Response<CustomerViewDTO>? = null
            try {
                //  val test = CreditSystemApi.createApi(CustumerService::class.java).findCustumerById(1)
                val test = RetrofitApiClient.createApi(CustumerApi::class.java,Consts.BASE_URL_COTACOES_API)
                    .createCustomer(cutomer.toDTO())
                result = test
            }catch (ex : Exception){
                ex.printStackTrace()
                throw  Exception("falaha a carregar dados da api : ${ex.message}")
            }

            if (result != null) {
                if (result.isSuccessful){
                    result.body().let {
                        withContext(Dispatchers.Main){
                            binding.toolbar.title = it?.fistName
                        }
                    }

                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginCadastroActivity, result.code(), Toast.LENGTH_SHORT).show()
                        Log.i("INFO_", "getapi:${result.code()} ")
                    }
                }
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginCadastroActivity, "resultado nulo", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun initNavHostfragment(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewNavHost) as NavHostFragment

        navController = navHostFragment.navController
        appbar =  AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appbar)
    }
    private fun configBottomView(){
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }
}