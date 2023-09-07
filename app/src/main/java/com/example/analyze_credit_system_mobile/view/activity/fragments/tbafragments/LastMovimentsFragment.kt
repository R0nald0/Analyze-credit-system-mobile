package com.example.analyze_credit_system_mobile.view.activity.fragments.tbafragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentLastMovimentsBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.example.analyze_credit_system_mobile.view.adapter.AdapterAtividades
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LastMovimentsFragment() : Fragment() {
    private val binding by lazy {
        FragmentLastMovimentsBinding.inflate(layoutInflater)
    }

    private val homeViewModel by viewModels<HomeViewModel>()
    private val  loginViewModel by activityViewModels<LoginViewModel>()
    lateinit var  customerView: CustomerView


    private lateinit var  adapetAtividades : AdapterAtividades
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.customerLogged()
    }


    private fun initBinding() {
        binding.rcvAtividades.apply {
            adapter =adapetAtividades
            layoutManager = LinearLayoutManager(context.applicationContext,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(DividerItemDecoration(context.applicationContext,LinearLayoutManager.HORIZONTAL))
        }
    }

    fun initObservers(){
        homeViewModel.listMoviments.observe(this.viewLifecycleOwner){listMoviment->
            if (listMoviment.isNullOrEmpty()){
                context?.toastAlert("No moviments")
            }else{
                adapetAtividades.getListAtividade(listMoviment)
            }
        }
        loginViewModel.authenticationState.observe(this.viewLifecycleOwner){authenticateState ->
            when(authenticateState){
                is  AuthenticationState.Logged ->{
                    customerView = authenticateState.customerView
                    homeViewModel.getAllMovimentsCustomer(customerView.id)
                }
                is AuthenticationState.Unlogged -> {
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {}
            }
        }
    }

    fun initAdapter(){
        adapetAtividades = AdapterAtividades()
    }




}