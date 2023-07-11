package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentLoginBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.shared.extensions.clearFieldsError
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private  val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initBidings()

    }

    private fun initViewModels() {
        loginViewModel.stateProgress.observe(viewLifecycleOwner){ stateProgress->
             if (stateProgress) binding.btnProgresssLogin.setLoading()
        }
        loginViewModel.authenticationState.observe(viewLifecycleOwner){ authenticationState ->
            when(authenticationState){
                is AuthenticationState.InvalidAuthentication ->{
                    authenticationState.fields.forEach {campo->
                        bindInputWithLoginViewModels()[campo]?.error = getString(campo.second)
                    }
                }
                is AuthenticationState.Logged ->{
                    val email = binding.edtInputEmailLogin.text.toString()
                    val bundle = bundleOf("user" to email)
                    findNavController().navigate(R.id.action_loginFragment_to_createCreditFragment,bundle)
                }
                else -> {}
            }
        }
    }
     private fun bindInputWithLoginViewModels() = mapOf(
         LoginViewModel.INPUT_EMAIL to binding.txtInputEmailLogin,
         LoginViewModel.INPUT_PASSWORD to binding.txtInputPassword
     )

   private fun initBidings(){
       binding.txtInputEmailLogin.clearFieldsError()
       binding.txtInputPassword.clearFieldsError()


       binding.btnProgresssLogin.setOnClickListener {

              val email = binding.edtInputEmailLogin.text.toString()
               val password = binding.edtInputPassword.text.toString()
               loginViewModel.authentication(email, password)

       }
       binding.btnCadastresse.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
       }
   }

}