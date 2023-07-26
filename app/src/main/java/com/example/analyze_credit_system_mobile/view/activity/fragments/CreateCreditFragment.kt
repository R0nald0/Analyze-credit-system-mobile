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
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.view.model.CustomerView
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
    private  lateinit var  customerView:CustomerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListenersViewModel()
        initBindings()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.customerLogged()
    }

   private fun initBindings(){
       binding.btnNext.setOnClickListener {
           val creditValue = binding.edtCreditValue.text.toString()
           if (creditValue.isEmpty()){
               binding.edtCreditValue.error = "Valor inválido"
           }else{
               val args = CreateCreditFragmentDirections.actionCreateCreditFragmentToNavigationGraph(customerView,creditValue)
               findNavController().navigate(args)
           }

       }

    }
    private fun initListenersViewModel(){
        loginViewModel.authenticationState.observe(viewLifecycleOwner){state ->
           when(state){
               is AuthenticationState.Loaded ->{
                   binding.progressBarHorizontal.visibility = View.GONE
                   binding.linearLayoutCredit.visibility =View.VISIBLE
               }
               is AuthenticationState.Loading->{
                   binding.progressBarHorizontal.visibility = View.VISIBLE
                   binding.linearLayoutCredit.visibility =View.GONE
               }
              is AuthenticationState.Unlogged ->{
                  findNavController().navigate(R.id.loginFragment)
              }
               is AuthenticationState.Logged ->{
                   customerView = state.customerView
                   binding.linearLayoutCredit.visibility =View.VISIBLE
                   binding.txvName.text = getString(R.string.credit_create_txv_quanto_precisa,customerView.firstName)
               }
               is AuthenticationState.errorState->{
                   Toast.makeText(context, state.mensageError, Toast.LENGTH_SHORT).show()
               }
               else -> {}
           }
        }
    }


}