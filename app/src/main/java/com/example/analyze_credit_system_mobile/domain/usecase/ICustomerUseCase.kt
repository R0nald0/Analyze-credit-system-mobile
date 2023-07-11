package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.view.model.CustomerView

interface ICustomerUseCase {
    suspend fun login(email: String ,password :String) :Result<CustomerView>
    suspend fun createCustumer(customer: Customer): Result<Customer>

    suspend fun custmerAuth():Result<CustomerView?>
    suspend fun findCustumerById(): Result<Customer>
    suspend fun deleteCustumer(customer: Customer): Result<Boolean>
    suspend fun updateCustumer(idCustomer: Long): Result<Boolean>

}
