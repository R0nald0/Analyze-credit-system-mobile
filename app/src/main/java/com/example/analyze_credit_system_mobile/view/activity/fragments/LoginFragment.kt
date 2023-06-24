package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentLoginBinding
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        loginViewModel.authenticationState.observe(viewLifecycleOwner){
            when(it){
                is LoginViewModel.AuthenticationState.InvalidAuthentication ->{
                    it.fields.forEach {campo->
                        bindInputWithLoginViewModels()[campo]?.error = getString(campo.second)
                    }
                }
                is LoginViewModel.AuthenticationState.Logged ->{
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
     binding.edtInputEmailLogin.addTextChangedListener {
         binding.txtInputEmailLogin.error = null
     }
        binding.edtInputPassword.addTextChangedListener {
            binding.txtInputPassword.error = null
        }

       binding.btnProgresssLogin.setOnClickListener {
          // binding.btnProgresssLogin.setLoading()

               val email = binding.edtInputEmailLogin.text.toString()
               val password = binding.edtInputPassword.text.toString()
               loginViewModel.authentication(email, password)
              // binding.btnProgresssLogin.setNormal()
       }
       binding.btnCadastresse.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
       }
   }

}