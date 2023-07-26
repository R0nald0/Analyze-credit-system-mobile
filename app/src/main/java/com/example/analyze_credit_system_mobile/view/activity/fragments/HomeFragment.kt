package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentHomeBinding
import com.example.analyze_credit_system_mobile.domain.adapter.AdapterAtividades
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val  loginViewModel by activityViewModels<LoginViewModel>()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private lateinit var customerView : CustomerView

    private lateinit var  adapetAtividades : AdapterAtividades
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBindings()
    }

    private fun initBindings() {
        binding.imgNotification.setOnClickListener {
            loginViewModel.delsogar()
        }
        binding.rcvAtividades.apply {
            adapter =adapetAtividades
            layoutManager = LinearLayoutManager(context.applicationContext,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(DividerItemDecoration(context.applicationContext,LinearLayoutManager.HORIZONTAL))

        }
    }

    fun initAdapter(){
        adapetAtividades = AdapterAtividades()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.customerLogged()
    }
    private fun initObservers() {
        loginViewModel.authenticationState.observe(this.viewLifecycleOwner){authenticateState ->
               when(authenticateState){
                   is  AuthenticationState.Logged ->{

                       customerView = authenticateState.customerView
                       val name = "${customerView.firstName} ${customerView.lastName}"
                       binding.txvNameUser.setText(name)
                       binding.txvSaldoValor.setText(getString(R.string.home_mensage_free_value,customerView.accountFreeBalance.toString()))
                       binding.txvSaldoBloqueado.setText(getString(R.string.home_mensage_block_value,customerView.accountBalanceBlocked.toString()))
                       homeViewModel.getAllMovimentsCustomer(customerView.id)
                       binding.txvContaUserNumber.setText(customerView.numberAccount.toString())
                   }
                   is AuthenticationState.Unlogged -> {
                       findNavController().navigate(R.id.loginFragment)
                   }
                   else -> {}
               }
        }
        homeViewModel.listMoviments.observe(this.viewLifecycleOwner){listMoviment->
             if (listMoviment.isNullOrEmpty()){
                 Toast.makeText(context,"No moviments", Toast.LENGTH_SHORT).show()
             }else{
                 adapetAtividades.getListAtividade(listMoviment)
             }
        }
    }

}