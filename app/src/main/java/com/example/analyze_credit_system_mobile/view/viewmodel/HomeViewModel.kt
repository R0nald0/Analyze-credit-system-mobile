package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import com.example.analyze_credit_system_mobile.domain.usecase.AccountMovimentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountMovimentUseCase: AccountMovimentUseCase
) : ViewModel() {
    private val _listMoviments = MutableLiveData<List<AccountMovimentView>>()
    val  listMoviments :LiveData<List<AccountMovimentView>>
        get() = _listMoviments
    init {
    }
    fun getAllMovimentsCustomer(idCustomer:Long){
       viewModelScope.launch {
            val resultMoviments =accountMovimentUseCase.getAccountMovimentsCustomer(idCustomer)
            _listMoviments.postValue(resultMoviments)
       }
    }
}