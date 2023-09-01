package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AccountMovimentUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.ValidateCredit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountMovimentUseCase: AccountMovimentUseCase,
    private val customerUseCase: ICustomerUseCase,
    private val validateCredit: ValidateCredit
) : ViewModel() {
    private val _listMoviments = MutableLiveData<List<AccountMovimentView>>()
    val  listMoviments :LiveData<List<AccountMovimentView>>
        get() = _listMoviments

    private val _recipientData = MutableLiveData<Result<Customer>>()
    val recipientData : LiveData<Result<Customer>>
        get() = _recipientData


    init {
    }
    fun getAllMovimentsCustomer(idCustomer:Long){
       viewModelScope.launch {
            val resultMoviments =accountMovimentUseCase.getAccountMovimentsCustomer(idCustomer)
            _listMoviments.postValue(resultMoviments)
       }
    }

    fun findCustomerByEmail(accountNumber: Long){
             viewModelScope.launch {
                 val byAccountNumber =
                     customerUseCase.findCustumerByAccountNumber(accountNumber)
                  _recipientData.postValue(byAccountNumber)
             }
    }

    fun validateAccountNumber(accountNumber: String):Set<String>{

        val validAccountNumberErros = validateCredit.validAccountNumber(accountNumber)
        if (validAccountNumberErros.isEmpty()){
            findCustomerByEmail(accountNumber.toLong())
            emptySet<String>()
        }
        return validAccountNumberErros

    }

}