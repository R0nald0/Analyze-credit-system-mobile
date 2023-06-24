package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.util.Log
import android.view.DisplayCutout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentHomeBinding
import com.example.analyze_credit_system_mobile.domain.adapter.AdapterAtividades
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment() {
    val args : HomeFragmentArgs by navArgs()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val  loginViewModel by activityViewModels<LoginViewModel>()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

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
       /* binding.idBtnLogarHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }*/
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

    private fun initObservers() {
        loginViewModel.authenticationState.observe(this.viewLifecycleOwner){authenticateState ->
               when(authenticateState){
                   is  LoginViewModel.AuthenticationState.Logged ->{
                       binding.txvNameUser.setText(args.nameUser)
                     //  binding.idBtnLogarHome.visibility =View.INVISIBLE
                   }
                   is LoginViewModel.AuthenticationState.Unlogged -> {
                       binding.txvNameUser.setText("No user on ")
                    //   binding.idBtnLogarHome.visibility =View.VISIBLE
                   }
                   else -> {}
               }

        }
    }

}