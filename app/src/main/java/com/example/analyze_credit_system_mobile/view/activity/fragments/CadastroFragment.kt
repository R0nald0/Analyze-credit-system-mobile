package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.dto.CustomerDTO
import com.example.analyze_credit_system_mobile.databinding.FragmentCadastroBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AutenticationUseCaseImpl
import com.example.analyze_credit_system_mobile.shared.dialog.AlertDialogCustom
import com.example.analyze_credit_system_mobile.shared.extensions.clearFieldsError
import com.example.analyze_credit_system_mobile.view.viewmodel.CadastroViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CadastroFragment : Fragment() {
    private  val cadastroViewModel by viewModels<CadastroViewModel>()
    private  val binding by lazy {
        FragmentCadastroBinding.inflate(layoutInflater)
    }
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
        super.onViewCreated(binding.root, savedInstanceState)
        iniViewModelObserver()
        initBindings()
        clearTextInputTypeAlertErroField()

    }

    private fun iniViewModelObserver() {

        cadastroViewModel.authenticationState.observe(viewLifecycleOwner){authInvaliForm->
              when(authInvaliForm){
                  is AuthenticationState.FetchingDataState->{
                      if (!authInvaliForm.isActivated ){
                          alertDialog.exibirDiaolog()
                      }
                      else{
                          alertDialog.fecharDialog()
                      }
                      binding.progressZipCode.visibility =authInvaliForm.isProgressVisibility
                      binding.edtInputZipCode.isActivated = authInvaliForm.isActivated
                      binding.edtInputZipCode.isClickable = authInvaliForm.isActivated
                      binding.txtInputZipCode.isActivated =authInvaliForm.isActivated
                      binding.txtInputZipCode.isClickable =authInvaliForm.isActivated

                  }
                 is AuthenticationState.InvalidForm ->{
                      if (authInvaliForm.listInvalidField.isNotEmpty()){
                          authInvaliForm.listInvalidField.forEach { pair->
                          bindInputsWithKeys()[pair]?.error = getString(pair.second)
                          }
                      }
                 }
                 is AuthenticationState.Loading ->{
                     binding.btnSave.setLoading()
                     alertDialog.exibirDiaolog()
                 }
                  is AuthenticationState.Loaded ->{
                      binding.btnSave.setNormal()
                      alertDialog.fecharDialog()
                  }
                  else -> {}
              }
        }

        cadastroViewModel.addressLiveData.observe(viewLifecycleOwner){addressResult->
               if (addressResult.isSuccess){
                   binding.txtInputZipCode.clearFieldsError()
                   val address = addressResult.getOrThrow()
                   binding.edtInputStreet.setText(address.street)
               }else{
                   val err  = addressResult.exceptionOrNull()
                   binding.txtInputZipCode.error = getString(R.string.cep_inexistente_erro)
                   Toast.makeText(context, "Erro -${err?.message}", Toast.LENGTH_SHORT).show()
               }
        }

        cadastroViewModel.resulCustomer.observe(viewLifecycleOwner){ resultCustomer->
             if(resultCustomer.isSuccess){
                 val customerCreated = resultCustomer.getOrThrow()
                 Toast.makeText(this.context, "criado cliente ${customerCreated.firstName} ${customerCreated.lastName} }", Toast.LENGTH_SHORT).show()
                 findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
             }else{
                 resultCustomer.getOrElse {erro ->
                     Toast.makeText(activity?.applicationContext, erro.message, Toast.LENGTH_SHORT).show()
                 }
             }
        }
    }

    private fun initBindings(){
        binding.btnSave.setOnClickListener {
                     //TODO rever animacao de loading do botao salvar
                      val customerDTO = getBindsToCustomer()
                      cadastroViewModel.validField(customerDTO)
                 }
        binding.edtInputZipCode.addTextChangedListener {
            val zipCode = binding.edtInputZipCode.unMaskedText
            if (zipCode?.length == 8) {
                binding.edtInputStreet.setText("")
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
         AutenticationUseCaseImpl.ZIPCODE_CUSTOMER_INEXISTENTE to binding.txtInputZipCode,
         AutenticationUseCaseImpl.STREET_CUSTOMER to  binding.txtInputStreet,
         AutenticationUseCaseImpl.PASSWORD_CUSTOMER to  binding.txtInputPassword
     )
    private fun  getBindsToCustomer():CustomerDTO {

        val name = binding.edtInputFirstName.text.toString()
        val lastName = binding.edtInputLastName.text.toString()
        val email = binding.edtInputEmail.text.toString()
        val cpf = binding.edtInputCpf.unMaskedText.toString()
        val zipCode = binding.edtInputZipCode.unMaskedText.toString()
        val street = binding.edtInputStreet.text.toString()
        var income = binding.edtInputIncome.unMaskedText.toString()
        val password = binding.edtInputPassword.text.toString()

        if (income.isBlank()) income = "0.0"
        return CustomerDTO(
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