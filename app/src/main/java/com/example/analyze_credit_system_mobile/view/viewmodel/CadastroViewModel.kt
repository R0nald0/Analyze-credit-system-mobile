package com.example.analyze_credit_system_mobile.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class CadastroViewModel @Inject constructor(
    private  var customerUseCase: ICustomerUseCase,
    private var autenticationUseCase: IAutenticationUseCase
): ViewModel() {

    private val _itemInvalidFieldList = MutableLiveData<AutenticationValidFormState.InvalidForm>()
    val itemInvalidFieldList : LiveData<AutenticationValidFormState.InvalidForm>
        get() = _itemInvalidFieldList

    private val _resulCustomer = MutableLiveData<Result<Customer>>()
    val resulCustomer : LiveData<Result<Customer>>
     get() = _resulCustomer

    private val _addressLiveData = MutableLiveData<Result<Address>>()
    val addressLiveData: LiveData<Result<Address>>
        get () = _addressLiveData

   fun findAddress(zipCode: String){
         viewModelScope.launch {
              val result = autenticationUseCase.getAddress(zipCode)
                _addressLiveData.postValue(result)
             _itemInvalidFieldList.value = autenticationUseCase.checkInvalidFormList()
         }
   }
    fun validField(nameCustomer:String,lastName:String, cpf:String, income: BigDecimal,
                   email:String, zipCode:String, street:String,password:String){
        viewModelScope.launch {
            _itemInvalidFieldList.value = autenticationUseCase.validForm(nameCustomer,lastName,cpf, income,
                    email, zipCode, street, password)
        }

    }

    fun createCustomer(customerViewDto: CustomerViewDTO) {
        val customer = customerViewDto.toCustomer()
        viewModelScope.launch{
            Log.i("INFO_", "createCustomer: ${customer.firstName}")
            val result= customerUseCase.createCustumer(customer)
            _resulCustomer.value  = result
        }
    }
}