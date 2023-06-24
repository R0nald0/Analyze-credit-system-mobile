package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import java.math.BigDecimal

interface ICustomerUseCase {
    suspend fun createCustumer(customer: Customer): Result<Customer>
    suspend fun getAllCustomer(): Result<List<Customer>>
    suspend fun findCustumerById(idCustomer: Long): Result<Customer>
    suspend fun deleteCustumer(customer: Customer): Result<Boolean>
    suspend fun updateCustumer(idCustomer: Long): Result<Boolean>
}
