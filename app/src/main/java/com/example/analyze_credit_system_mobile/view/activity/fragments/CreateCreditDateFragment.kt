package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCreateCreditDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar


class CreateCreditDateFragment : Fragment() {

    private val binding by lazy {
       FragmentCreateCreditDateBinding.inflate(layoutInflater)
    }

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

    private fun getLimitsDate( field :Int , amountTime: Int) :Long {
        val dataMinima = Calendar.getInstance()
        dataMinima.time
        dataMinima.add(field,amountTime)
        return  dataMinima.time.time
    }


    private fun calculateInstallment(numberInstallment: Int,valueCredit:Double) :Double{
          return  valueCredit/ numberInstallment
    }

    fun initBinds(){
        val dateLimitInicial = getLimitsDate(Calendar.DATE,20)

        binding.txvCoutValueInstallment.text = String.format("R$ %.4s por mês",args.creditValue)
        binding.calendarView.apply {
                minDate = dateLimitInicial
                maxDate = getLimitsDate(Calendar.MONTH,3)
               setOnDateChangeListener { view, year, month, dayOfMonth ->
                        binding.txvDataSelected.text = getString(
                            R.string.credit_create_date_number_selected,dayOfMonth,month + 1,year)
               }
        }

        val format =SimpleDateFormat("dd/MM/YYYY").format(dateLimitInicial)

        binding.txvDataSelected.text = format

        binding.progresseButton.setOnClickListener {
            val creditValue = args.creditValue
            val dateFistInstallment= binding.txvDataSelected.text.toString()
            val numberOfInstallment =binding.edtNumberInstallment.text.toString()

            if (creditValue.isNotEmpty() && dateFistInstallment.isNotEmpty() && numberOfInstallment.isNotEmpty()){
                val action = CreateCreditDateFragmentDirections
                    .actionCreateCreditDateFragmentToCreateCreditEndFragment(creditValue,dateFistInstallment,numberOfInstallment)
                findNavController().navigate(action )
            }else{
                Toast.makeText(this.context, "preencha os dados corretamente", Toast.LENGTH_SHORT).show()
            }

        }

        binding.edtNumberInstallment.addTextChangedListener {
            val number = binding.edtNumberInstallment.text.toString()
            if(number.isNotEmpty()){
                val installment = number.toInt()
                if (installment > 0){
                    val totalValueInstallment = calculateInstallment(installment,args.creditValue.toDouble())
                    val resul = String.format("x R$ %.4s por mês",totalValueInstallment)
                    binding.txvCoutValueInstallment.text = resul
                }
            }else{
                Toast.makeText(this.context, "digite uma parcela maior que 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

}