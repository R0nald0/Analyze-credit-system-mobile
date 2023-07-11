package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditEndBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateLongToString
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class CreateCreditEndFragment : Fragment() {
    private val binding by lazy {
         FragmentCreateCreditEndBinding.inflate(layoutInflater)
    }

    private val  creditArgs : CreateCreditEndFragmentArgs by navArgs()
    private val creditViewModel : CreateCreditViewModel by activityViewModels()
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

          creditViewModel.stateCreditLiveData.observe(viewLifecycleOwner){stateCredit->
                when(stateCredit){
                    is StateCredit.Sucecss ->{

                        confirmData()
                        binding.btnProgresbarConfirm.setNormal()
                    }
                    is StateCredit.InvalidCreditfields->{
                        Toast.makeText(context?.applicationContext, "Erro nos campos", Toast.LENGTH_SHORT).show()
                    }
                    is StateCredit.Loading->{
                        Toast.makeText(context?.applicationContext, "Carregando....", Toast.LENGTH_SHORT).show()
                        binding.btnProgresbarConfirm.setLoading()
                    }
                    is StateCredit.Loaded ->{
                        Toast.makeText(context?.applicationContext, "Carregado", Toast.LENGTH_SHORT).show()
                        binding.btnProgresbarConfirm.setNormal()
                    }
                    is  StateCredit.error ->{
                        binding.btnProgresbarConfirm.setNormal()
                        Toast.makeText(context?.applicationContext, stateCredit.menssage, Toast.LENGTH_SHORT).show()
                    }
                    else->{}
                }

          }
         creditViewModel.resultCreatCredit.observe(viewLifecycleOwner){
             if (it.isSuccess){
                   Toast.makeText(this.context, it.getOrThrow(), Toast.LENGTH_LONG).show()
             }else{
                    it.getOrElse { falha ->
                        Toast.makeText(this.context, falha.message, Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                 }

             }
         }
    }

    private fun confirmData(){
        val alertDialog = AlertDialog.Builder(binding.btnProgresbarConfirm.context)
        alertDialog.setMessage("Seu pedido está em Análise")
            .setPositiveButton("ok"){ lister,tex ->
                findNavController().popBackStack(R.id.mainFragment,false)
            }
            .show()
            .create()
    }


    fun initBindings(){
        val resumeCredit = calculateInstallment(creditArgs.credit.numberOfInstallments,creditArgs.credit.creditValue.toDouble())
        val resul = String.format("x R$ %.4s",resumeCredit)
        val date = Date().convertDateLongToString(creditArgs.credit.dayFistInstallment)

        binding.txvValorCreditConfirm.text = "R$ ${creditArgs.credit.creditValue}"
        binding.txvDateCreditConfirm.text = date
        binding.txvInstallmentConference.text = "${creditArgs.credit.numberOfInstallments} $resul"

        binding.btnProgresbarConfirm.setOnClickListener {
            creditViewModel.validCredit(creditArgs.credit)
        }
    }

    private fun calculateInstallment(numberInstallment: Int,valueCredit:Double) :Double{
        return  valueCredit/ numberInstallment
    }


}