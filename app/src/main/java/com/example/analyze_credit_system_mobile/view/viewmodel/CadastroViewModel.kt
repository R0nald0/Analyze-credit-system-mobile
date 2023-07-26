package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.model.toEntity

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
    fun validField(customerView: CustomerView){
        viewModelScope.launch {
           autenticationUseCase.validForm(customerView.firstName,customerView.lastName,customerView.cpf, customerView.income, customerView.email, customerView.zipCode,customerView.password!!)
            val listInvalidsFields  = autenticationUseCase.checkInvalidFormList()
                if(listInvalidsFields.listInvalidField.isEmpty()){
                    if (_addressLiveData.value?.isSuccess == true)  createCustomer(customerView)
                }else{
                    _autenticationState.value = listInvalidsFields
                }
        }
    }

    fun createCustomer(customerView: CustomerView) {
        viewModelScope.launch{
            val customer = customerView.toEntity()
            _autenticationState.value =AuthenticationState.Loading
            val result= customerUseCase.createCustumer(customer)
            _resulCustomer.value  = result
            _autenticationState.value =AuthenticationState.Loaded
        }

    }
}