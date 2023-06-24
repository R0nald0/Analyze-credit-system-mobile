package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditEndBinding
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

@AndroidEntryPoint
class CreateCreditEndFragment : Fragment() {
    private val binding by lazy {
         FragmentCreateCreditEndBinding.inflate(layoutInflater)
    }

    private val  args : CreateCreditEndFragmentArgs by navArgs()
    private val creditViewModel : CreateCreditViewModel by viewModels()
    private val  loginViewModel : LoginViewModel by activityViewModels()

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
        initListernersViewModel()
        initBindings()
    }

    private fun initListernersViewModel(){
        loginViewModel.authenticationState.observe(viewLifecycleOwner){loginViewModelAuthenticate ->

             when(loginViewModelAuthenticate){
                  is LoginViewModel.AuthenticationState.Logged->{

                  }
                 else -> {}
             }
        }

         creditViewModel.resultCreatCredit.observe(viewLifecycleOwner){
             if (it.isSuccess){
                 Toast.makeText(this.context, it.getOrThrow(), Toast.LENGTH_LONG).show()
             }else{
                    it.getOrElse { falha ->
                        Toast.makeText(this.context, falha.message, Toast.LENGTH_LONG).show()
                 }

             }
         }
    }

    private fun confirmData(){
        val alertDialog = AlertDialog.Builder(binding.btnProgresbarConfirm.context)
        alertDialog.setMessage("seu pedido está em análise")
            .setPositiveButton("ok"){ lister,tex ->
                findNavController().popBackStack(R.id.mainFragment,false)
            }
            .show()
            .create()
    }


    fun initBindings(){
        val resumeCredit = calculateInstallment(args.installmentValue.toInt(),args.creditValue.toDouble())
        val resul = String.format("x R$ %.4s por mês",resumeCredit)

        binding.txvValorCreditConfirm.text = args.creditValue
        binding.txvDateCreditConfirm.text = args.dateFistInstallment
        binding.txvInstallmentConference.text = "${args.installmentValue} $resul"

        val creditValue = args.creditValue.toBigDecimal()
        val creditFistInstallment = Date().time
        val creditNumberIsntallment = args.installmentValue.toInt()

       val creditView = CreditView(
            creditValue =  creditValue,
            numberOfInstallments = creditNumberIsntallment,
            dayFistInstallment = creditFistInstallment,
        )
        binding.btnProgresbarConfirm.setOnClickListener {
            binding.btnProgresbarConfirm.setLoading()
             creditViewModel.createCredit(creditView)
            lifecycleScope.launch {
                delay(3000)
                binding.btnProgresbarConfirm.setNormal()
                confirmData()
            }
        }
    }

    private fun calculateInstallment(numberInstallment: Int,valueCredit:Double) :Double{
        return  valueCredit/ numberInstallment
    }


}