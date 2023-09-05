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
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.shared.utils.MyWatcher
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCreditFragment : Fragment() {
    private val loginViewModel by activityViewModels<LoginViewModel>()
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
       val watcher = MyWatcher(binding.edtCreditValue)
       binding.edtCreditValue.addTextChangedListener(watcher)

       binding.btnNext.setOnClickListener {
           val unMsk = watcher.unmsk()
           if (unMsk.isEmpty()) {
               binding.edtCreditValue.error = "Valor inválido"
           } else {
               val args =
                   CreateCreditFragmentDirections.actionCreateCreditFragmentToNavigationGraph(
                       customerView,
                       unMsk
                   )
               findNavController().navigate(args)
           }
       }

    }
    private fun initListenersViewModel() {
        loginViewModel.authenticationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthenticationState.Loaded -> {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.linearLayoutCredit.visibility = View.VISIBLE
                }

                is AuthenticationState.Loading -> {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.linearLayoutCredit.visibility = View.GONE
                }

                is AuthenticationState.Unlogged -> {
                    findNavController().navigate(R.id.loginFragment)
                }

                is AuthenticationState.Logged -> {
                    customerView = state.customerView
                    binding.linearLayoutCredit.visibility = View.VISIBLE
                    binding.txvName.text =
                        getString(R.string.credit_create_txv_quanto_precisa, customerView.firstName)
                }
                is AuthenticationState.errorState -> {
                    Toast.makeText(context, state.mensageError, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }
}