package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.databinding.FragmentCadastroBinding
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AutenticationUseCaseImpl
import com.example.analyze_credit_system_mobile.shared.extensions.clearFieldsError
import com.example.analyze_credit_system_mobile.view.viewmodel.CadastroViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CadastroFragment : Fragment() {
    private  val cadastroViewModel by viewModels<CadastroViewModel>()
    private  val binding by lazy {
        FragmentCadastroBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        iniViewModelObserver()
        initBindings()
        clearTextInputTypeAlertErroField()
    }

    private fun iniViewModelObserver() {
        cadastroViewModel.itemInvalidFieldList.observe(viewLifecycleOwner){authInvaliForm->
              when(authInvaliForm){
                 is AutenticationValidFormState.InvalidForm ->{
                      if (authInvaliForm.listInvalidField.isNotEmpty()){
                          authInvaliForm.listInvalidField.forEach { fields->
                              bindInputsWithKeys()[fields]?.error = getString(fields.second)
                          }
                      }else{
                          cadastroViewModel.createCustomer(getBindsToCustomer())
                      }
                 }
                  else -> {}
              }
        }

        cadastroViewModel.addressLiveData.observe(viewLifecycleOwner){addressResult->
               if (addressResult.isSuccess){
                   binding.txtInputZipCode.clearFieldsError()
                   val address = addressResult.getOrThrow()
                   binding.edtInputStreet.setText(address.street)
               }
        }

        cadastroViewModel.resulCustomer.observe(viewLifecycleOwner){ resultCustomer->
             if(resultCustomer.isSuccess){
                 val customerCreated = resultCustomer.getOrThrow()
                 Toast.makeText(this.context, "criado cliente ${customerCreated.firstName} ${customerCreated.lastName} }", Toast.LENGTH_SHORT).show()
                 //findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
             }else{
                 resultCustomer.getOrElse {erro ->
                     Toast.makeText(activity?.applicationContext, erro.message, Toast.LENGTH_SHORT).show()
                 }
             }
        }
    }

    private fun initBindings(){
        binding.btnSave.setOnClickListener {
                      val customer = getBindsToCustomer()
                      binding.btnSave.setLoading()

                      cadastroViewModel.validField(
                             customer.fistName,
                             customer.lastName,
                             customer.cpf,
                             customer.income,
                             customer.email,
                             customer.zipCode,
                             customer.street,
                             customer.password
                      )
                      binding.btnSave.setNormal()
                 }
        binding.edtInputZipCode.addTextChangedListener {
            val zipCode = binding.edtInputZipCode.unMaskedText
            if (zipCode?.length == 8) {
                cadastroViewModel.findAddress(zipCode)
            }
        }
    }
    private fun bindInputsWithKeys() = mapOf(
         AutenticationUseCaseImpl.FIRSTNAME_CUSTOMER to binding.txtInputFirstName,
         AutenticationUseCaseImpl.LASTNAME_CUSTOMER to binding.txtInputLastName,
         AutenticationUseCaseImpl.CPF_CUSTOMER to  binding.txtInputCpf,
         AutenticationUseCaseImpl.INCOME_CUSTOMER to  binding.txtInputIncome,
         AutenticationUseCaseImpl.EMAIL_CUSTOMER to  binding.txtInputEmail,
         AutenticationUseCaseImpl.ZIPCODE_CUSTOMER to  binding.txtInputZipCode,
         AutenticationUseCaseImpl.STREET_CUSTOMER to  binding.txtInputStreet,
         AutenticationUseCaseImpl.PASSWORD_CUSTOMER to  binding.txtInputPassword
     )
    private fun  getBindsToCustomer():CustomerViewDTO {

        val name = binding.edtInputFirstName.text.toString()
        val lastName = binding.edtInputLastName.text.toString()
        val email = binding.edtInputEmail.text.toString()
        val cpf = binding.edtInputCpf.unMaskedText.toString()
        val zipCode = binding.edtInputZipCode.unMaskedText.toString()
        val street = binding.edtInputStreet.text.toString()
        var income = binding.edtInputIncome.text.toString()
        val password = binding.edtInputPassword.text.toString()

        if (income.isBlank()) income = "0.0"
        return CustomerViewDTO(
            fistName = name,
            lastName = lastName,
            email = email,
            income = income.toBigDecimal(),
            cpf = cpf,
            password = password,
            zipCode = zipCode,
            street = street
        )
    }
    private fun clearTextInputTypeAlertErroField(){
      binding.edtInputFirstName.addTextChangedListener {
          binding.txtInputFirstName.clearFieldsError()
      }
      binding.edtInputLastName.addTextChangedListener {
          binding.txtInputLastName.clearFieldsError()
      }
      binding.edtInputCpf.addTextChangedListener {
          binding.txtInputCpf.clearFieldsError()
      }
      binding.edtInputIncome.addTextChangedListener {
          binding.txtInputIncome.clearFieldsError()
      }
      binding.edtInputEmail.addTextChangedListener {
          binding.txtInputEmail.clearFieldsError()
      }
      binding.edtInputZipCode.addTextChangedListener {
          binding.txtInputZipCode.clearFieldsError()
      }
      binding.edtInputStreet.addTextChangedListener {
          binding.txtInputStreet.clearFieldsError()
      }
      binding.edtInputPassword.addTextChangedListener {
          binding.txtInputPassword.clearFieldsError()
      }

  }
}