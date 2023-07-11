package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.data.dto.CustomerDTO

import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CadastroViewModel @Inject constructor(
    private  var customerUseCase: ICustomerUseCase,
    private var autenticationUseCase: IAutenticationUseCase
): ViewModel() {
    private val _itemInvalidFieldList = MutableLiveData<AuthenticationState.InvalidForm>()
    val itemInvalidFieldList : LiveData<AuthenticationState.InvalidForm>
        get() = _itemInvalidFieldList

    private val _resulCustomer = MutableLiveData<Result<Customer>>()
    val resulCustomer : LiveData<Result<Customer>>
     get() = _resulCustomer

    private val _addressLiveData = MutableLiveData<Result<Address>>()
    val addressLiveData: LiveData<Result<Address>>
        get () = _addressLiveData

  private  val _stateProgressLiveDate = MutableLiveData<AuthenticationState.FetchingDataState>()

    val stateProgressLiveData : LiveData<AuthenticationState.FetchingDataState>
        get() = _stateProgressLiveDate

  private val _autenticationState = MutableLiveData<AuthenticationState>()
  val   authenticationState:LiveData<AuthenticationState>
      get()= _autenticationState
    init {
       _autenticationState.value = AuthenticationState.FetchingDataState(8,true)
    }
    fun findAddress(zipCode: String){
         viewModelScope.launch {
             _autenticationState.value = AuthenticationState.FetchingDataState(0,false)
             _addressLiveData.postValue(autenticationUseCase.getAddress(zipCode))
             _autenticationState.value = AuthenticationState.FetchingDataState(8,true)
         }
   }
    fun validField(customerDTO: CustomerDTO){
        viewModelScope.launch {
           autenticationUseCase.validForm(customerDTO.fistName,customerDTO.lastName,customerDTO.cpf, customerDTO.income, customerDTO.email, customerDTO.zipCode,customerDTO.password)
            val listInvalidsFields  =autenticationUseCase.checkInvalidFormList()
                if(listInvalidsFields.listInvalidField.isEmpty()){
                    if (_addressLiveData.value?.isSuccess == true)  createCustomer(customerDTO)
                }else{
                    _autenticationState.value = listInvalidsFields
                }
        }
    }

    fun createCustomer(customerDTO: CustomerDTO) {
        viewModelScope.launch{
            val customer = customerDTO.toCustomer()
            _autenticationState.value =AuthenticationState.Loading
            val result= customerUseCase.createCustumer(customer)
            _resulCustomer.value  = result
            _autenticationState.value =AuthenticationState.Loaded
        }

    }
}