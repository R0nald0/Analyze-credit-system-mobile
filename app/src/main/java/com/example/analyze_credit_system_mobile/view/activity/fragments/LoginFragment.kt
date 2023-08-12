package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
     loginViewModel.authenticationState.observe(viewLifecycleOwner){ authenticationState ->
            when(authenticationState){
                is AuthenticationState.Loading ->{
                    binding.btnProgresssLogin.setLoading()
                }
                is AuthenticationState.Loaded->{
                    binding.btnProgresssLogin.setNormal()
                }
                is AuthenticationState.InvalidAuthentication ->{
                    authenticationState.fields.forEach {campo->
                        bindInputWithLoginViewModels()[campo]?.error = getString(campo.second)
                    }
                }
                is AuthenticationState.Logged ->{
                    findNavController().navigateUp()
                }
                is AuthenticationState.errorState ->{
                    Toast.makeText(context, authenticationState.mensageError, Toast.LENGTH_LONG).show()
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