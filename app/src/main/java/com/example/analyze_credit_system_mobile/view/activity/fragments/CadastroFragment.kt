package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentCadastroBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AutenticationUseCaseImpl
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.clearFieldsError
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.shared.dialog.AlertDialogCustom
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.hideKeyboard
import com.example.analyze_credit_system_mobile.view.viewmodel.CadastroViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

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
                 is AuthenticationState.InvalidForm ->{
                      if (authInvaliForm.listInvalidField.isNotEmpty()){
                          authInvaliForm.listInvalidField.forEach { pair->
                          bindInputsWithKeys()[pair]?.error = getString(pair.second)
                          }
                      }
                 }
                 is AuthenticationState.Loading ->{
                     binding.apply {
                         btnSave.setLoading()
                         progressZipCode.visibility = View.VISIBLE
                         edtInputZipCode.isActivated =false
                         edtInputZipCode.isClickable =false
                         alertDialog.exibirDiaolog("Carregando...")
                         txtInputStreet.visibility =View.GONE
                     }
                 }
                  is AuthenticationState.Loaded ->{
                      binding.apply {
                          progressZipCode.visibility = View.GONE
                          btnSave.setNormal()
                          edtInputZipCode.isActivated =true
                          edtInputZipCode.isClickable =true
                      }
                      alertDialog.fecharDialog()

                  }
                  is AuthenticationState.errorState -> {
                      Toast.makeText(context, "erro: ${authInvaliForm.mensageError}", Toast.LENGTH_LONG).show()
                      binding.txtInputZipCode.error = getString(R.string.cep_inexistente_erro)
                  }
                  else -> {}
              }
        }

        cadastroViewModel.addressLiveData.observe(viewLifecycleOwner){addressResult->
               if (addressResult.isSuccess){
                   binding.txtInputZipCode.clearFieldsError()
                   val address = addressResult.getOrThrow()
                   binding.txtInputStreet.visibility =View.VISIBLE
                   val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.enter_anime)
                   binding.txtInputStreet.startAnimation(loadAnimation)
                   binding.txtInputStreet.setText(address.street)
               }else{
                   val err  = addressResult.exceptionOrNull()
               }
        }

        cadastroViewModel.resulCustomer.observe(viewLifecycleOwner){ resultCustomer->
             if(resultCustomer.isSuccess){
                 val customerCreated = resultCustomer.getOrThrow()
                 Toast.makeText(this.context, "criado cliente ${customerCreated.firstName} ${customerCreated.lastName} ", Toast.LENGTH_SHORT).show()
                 cadastroViewModel.delsogar()
                 findNavController().navigateUp()
             }else{
                 resultCustomer.getOrElse {erro ->
                     Toast.makeText(activity?.applicationContext, erro.message, Toast.LENGTH_SHORT).show()
                 }
             }
        }
    }

    private fun initBindings(){
        binding.includeLbar.imageBtnBack.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.txtInputStreet.visibility =View.GONE

        binding.btnSave.setOnClickListener {
                       val customerView = getBindsToCustomer()
                       cadastroViewModel.validField(customerView)
                       it.hideKeyboard()
                 }
        binding.edtInputZipCode.addTextChangedListener {
            val zipCode = binding.edtInputZipCode.unMaskedText
            if (zipCode?.length == 8) {
                binding.txtInputStreet.setText("")
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
         AutenticationUseCaseImpl.PASSWORD_CUSTOMER to  binding.txtInputPassword
     )
    private fun  getBindsToCustomer(): CustomerView {

        val name = binding.edtInputFirstName.text.toString()
        val lastName = binding.edtInputLastName.text.toString()
        val email = binding.edtInputEmail.text.toString()
        val cpf = binding.edtInputCpf.unMaskedText.toString()
        val zipCode = binding.edtInputZipCode.unMaskedText.toString()
        val street = binding.txtInputStreet.text.toString()
        var income = binding.edtInputIncome.unMaskedText.toString()
        val password = binding.edtInputPassword.text.toString()

        if (income.isBlank()) income = "0.0"
        return CustomerView(
            firstName = name,
            lastName= lastName,
            cpf =cpf,
            income =  income.toBigDecimal(),
            street = street,
            numberAccount = 0,
            password = password,
            accountBalanceBlocked = BigDecimal.ZERO,
            zipCode = zipCode,
            id = -1,
            email = email,
            accountFreeBalance = BigDecimal.ZERO,
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

      binding.edtInputPassword.addTextChangedListener {
          binding.txtInputPassword.clearFieldsError()

      }

  }
}