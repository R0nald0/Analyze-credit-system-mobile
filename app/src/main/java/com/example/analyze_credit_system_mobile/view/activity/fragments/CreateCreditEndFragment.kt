package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditEndBinding
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateLongToString
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import com.example.analyze_credit_system_mobile.view.shared.dialog.AlertDialogCustom
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class CreateCreditEndFragment : Fragment() {
    private val binding by lazy {
         FragmentCreateCreditEndBinding.inflate(layoutInflater)
    }
    private val creditViewModel by activityViewModels<CreateCreditViewModel>()
    private val  creditArgs : CreateCreditEndFragmentArgs by navArgs()
    private val alertDialogCustom by lazy {
        AlertDialogCustom(this.requireActivity(),R.layout.alert_custom_layout)
    }

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
        initBindings()
    }

    private fun confirmData(){
          alertDialogCustom.exibirDiaolog(
              "Seu pedido está em Análise",
              false,
              "Ok",
              null,
              positiveAction = { findNavController().navigate(R.id.mainFragment) },
              nagativeAction = {}
          )
    }

    fun initBindings(){
        val numberOfInstallments = creditArgs.creditCreateView.numberOfInstallments
        val valueCreditFinal = creditArgs.creditCreateView.creditValue

        val resumeCredit =creditViewModel.calculateInstallment( numberOfInstallments,valueCreditFinal.toDouble())
        val resul = String.format("x R$ %.2f",resumeCredit)
        val date = Date().convertDateLongToString(creditArgs.creditCreateView.dayFistInstallment)

        binding.txvValorCreditConfirm.text = "R$ ${valueCreditFinal}"
        binding.txvDateCreditConfirm.text = date
        binding.txvInstallmentConference.text = "${numberOfInstallments} $resul"

        binding.btnProgresbarConfirm.setOnClickListener {
            creditViewModel.createCredit(creditArgs.creditCreateView)
            confirmData()
        }
    }




}