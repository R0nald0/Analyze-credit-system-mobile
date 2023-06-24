package com.example.analyze_credit_system_mobile.view.activity.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.NavigationGraphCraditArgs
import com.example.analyze_credit_system_mobile.NavigationGraphCraditDirections

import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditBinding
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCreditFragment : Fragment() {
    private val loginViewModel by activityViewModels<LoginViewModel>()
    private  val creditCreatViewModel by  viewModels<CreateCreditViewModel>()
    private val binding by lazy {
        FragmentCreateCreditBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListenersViewModel()
         binding.btnNext.setOnClickListener {
            initBindings()
        }
    }


   private fun initBindings(){
       val creditValue = binding.edtCreditValue.text.toString()
        if (creditValue.isEmpty()){
              binding.edtCreditValue.error = "Valor inválido"
        }else{
             val args =  CreateCreditFragmentDirections.actionCreateCreditFragmentToNavigation(creditValue)
            findNavController().navigate(args)
        }
    }
    private fun initListenersViewModel(){
        loginViewModel.authenticationState.observe(viewLifecycleOwner){loginViewModelAuthenticate->
           when(loginViewModelAuthenticate){
              is LoginViewModel.AuthenticationState.Unlogged ->{
                  findNavController().navigate(R.id.action_createCreditFragment_to_loginFragment)
              }
               is LoginViewModel.AuthenticationState.Logged ->{
                  val user = arguments?.getString("user")
                   binding.txvName.text = getString(R.string.credit_create_txv_quanto_precisa,user)
               }
               else -> {}
           }
        }
    }


}