package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditDateBinding
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateLongToString
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateStringToLong
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.example.analyze_credit_system_mobile.view.model.CreditCreateView
import com.example.analyze_credit_system_mobile.view.shared.dialog.AlertDialogCustom
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import java.util.Calendar
import java.util.Date


class CreateCreditDateFragment : Fragment() {

    private val binding by lazy {
       FragmentCreateCreditDateBinding.inflate(layoutInflater)
    }
    private val creditViewModel by activityViewModels<CreateCreditViewModel>()

    private val args : CreateCreditDateFragmentArgs by navArgs()
    private lateinit var creditCreateView :CreditCreateView
    private val alertDialog by lazy {
        AlertDialogCustom(this.requireActivity(),null)
    }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinds()
        initListernersViewModel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun initBinds(){

        //Configuracao data
        val dateFirstInstallment = creditViewModel.getLimitsDate(Calendar.DATE,20)
        var ActualDate =Date().convertDateLongToString(dateFirstInstallment)!!

        binding.calendarView.apply {
                minDate = dateFirstInstallment
                maxDate =creditViewModel.getLimitsDate(Calendar.MONTH,3)
               setOnDateChangeListener { view, year, month, dayOfMonth ->
                   ActualDate ="$dayOfMonth/${month + 1}/$year"
                        binding.txvDataSelected.text = getString(
                            R.string.credit_create_date_number_selected,dayOfMonth,month + 1,year)
                 }
        }

        binding.txvDataSelected.text = ActualDate
        binding.txvCoutValueInstallment.text ="x R${args.creditValue.toBigDecimal().formatCurrency()} por mês"

        binding.progresseButton.setOnClickListener {
            val creditValue = args.creditValue
            val numberOfInstallment = binding.edtNumberInstallment.text.toString()
            val date = Date().convertDateStringToLong(ActualDate)

            if (date != null && creditValue.isNotEmpty() && numberOfInstallment.toInt() > 0){
                creditCreateView = CreditCreateView(
                    creditValue =  args.creditValue.toBigDecimal(),
                    numberOfInstallments = numberOfInstallment.toInt(),
                    dayFistInstallment = date,
                    args.customer
                )
                creditViewModel.validCredit(creditCreateView)
            }else{
                context?.toastAlert("preencha os dados corretamente")
            }
        }

        binding.edtNumberInstallment.addTextChangedListener {
            val number = binding.edtNumberInstallment.text.toString()
             if (number.isNotBlank()) {
                 val validateInstallments = creditViewModel.validateInstallments(number)

                 if (validateInstallments != null){
                     binding.edtNumberInstallment.error = validateInstallments
                     binding.progresseButton.isClickable = false
                  }
                 else{
                     binding.progresseButton.isClickable = true
                     val valueInstallment= creditViewModel.getInstallments(number,args.creditValue.toBigDecimal())
                     binding.txvCoutValueInstallment.text= "x $valueInstallment por mês"
                 }
             }
             else {
                 binding.edtNumberInstallment.error = "numero de parcelas não pode ser vazio"
                 binding.txvCoutValueInstallment.text = "x R${args.creditValue} por mês"
             }
        }
    }

    private fun initListernersViewModel(){

        creditViewModel.stateCreditLiveData.observe(viewLifecycleOwner){stateCredit->
            when(stateCredit){
                is StateCredit.Sucecss ->{
                    binding.progresseButton.setNormal()
                    val action =
                        CreateCreditDateFragmentDirections.actionCreateCreditDateFragmentToCreateCreditEndFragment(creditCreateView)
                    findNavController().navigate(action)
                }
                is StateCredit.InvalidCreditfields->{
                    context?.toastAlert("Erro nos campos")
                }
                is StateCredit.Loading->{
                    alertDialog.exibirDiaolog(null)
                    binding.progresseButton.setLoading()
                }
                is StateCredit.Loaded ->{

                    binding.progresseButton.setNormal()
                    alertDialog.fecharDialog()
                }
                is StateCredit.Error ->{
                    binding.progresseButton.setNormal()
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
                    context?.toastAlert("${falha.message}")
                    findNavController().popBackStack()
                }

            }
        }
    }
}