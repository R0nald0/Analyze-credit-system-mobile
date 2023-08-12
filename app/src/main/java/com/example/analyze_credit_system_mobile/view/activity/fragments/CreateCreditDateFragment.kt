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
        binding.txvCoutValueInstallment.text = String.format("R$ %.4s por mês",args.creditValue)
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
                Toast.makeText(this.context, "preencha os dados corretamente", Toast.LENGTH_SHORT).show()
            }
        }

        binding.edtNumberInstallment.addTextChangedListener {
            val number = binding.edtNumberInstallment.text.toString()
             if (number.isNotBlank()) {
                 val  valueInstallment= creditViewModel.getInstallments(number,args.creditValue.toDouble())
                 binding.txvCoutValueInstallment.text= valueInstallment
             }
             else binding.edtNumberInstallment.error ="numero de parcelas não pode ser vazio"
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
                    Toast.makeText(context?.applicationContext, "Erro nos campos", Toast.LENGTH_SHORT).show()
                }
                is StateCredit.Loading->{
                    alertDialog.exibirDiaolog(null)
                    Toast.makeText(context?.applicationContext, "Carregando....", Toast.LENGTH_SHORT).show()
                    binding.progresseButton.setLoading()
                }
                is StateCredit.Loaded ->{
                    Toast.makeText(context?.applicationContext, "Carregado", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this.context, falha.message, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }

            }
        }
    }
}