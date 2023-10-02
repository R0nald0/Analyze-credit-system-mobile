package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Customer

interface IFindCustomerUseCase {

    suspend fun findCustumerByEmail(email: String): Result<Customer>
    suspend fun findCustumerByAccountNumber(accountNumber: Long): Result<Customer>
    suspend fun findCustumerById(): Result<Customer>
}