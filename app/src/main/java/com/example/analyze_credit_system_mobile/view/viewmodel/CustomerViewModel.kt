package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerUseCase: ICustomerUseCase
) : ViewModel() {

    private val customer =MutableLiveData<Result<Customer>>()

    fun createCustomer(customerViewDto: CustomerViewDTO){
           val customer = customerViewDto.toCustomer()

    }
}