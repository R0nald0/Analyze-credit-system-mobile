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
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateLongToString
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateStringToLong
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import java.util.Calendar
import java.util.Date


class CreateCreditDateFragment : Fragment() {

    private val binding by lazy {
       FragmentCreateCreditDateBinding.inflate(layoutInflater)
    }
    private val creditViewModel by activityViewModels<CreateCreditViewModel>()

    private val args : CreateCreditDateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    fun initBinds(){
        val dateFirstInstallment = creditViewModel.getLimitsDate(Calendar.DATE,20)
        val inicialDate =Date().convertDateLongToString(dateFirstInstallment)!!
        var ActualDate = inicialDate

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
            val dateFistInstallmentString= binding.txvDataSelected.text.toString()
            val numberOfInstallment =binding.edtNumberInstallment.text.toString()

            val date = Date().convertDateStringToLong(ActualDate)

            if (date != null && creditValue.isNotEmpty() && date !=null && numberOfInstallment.isNotEmpty()){
                val creditView = CreditView(
                    creditValue =  args.creditValue.toBigDecimal(),
                    numberOfInstallments = numberOfInstallment.toInt(),
                    dayFistInstallment = date,
                    args.customer
                )
                val action = CreateCreditDateFragmentDirections
                    .actionCreateCreditDateFragmentToCreateCreditEndFragment(creditView)
                findNavController().navigate(action)
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

}