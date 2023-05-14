package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Customer

interface ICustomerUseCase {
    fun createCustumer(customer: Customer):Result<Boolean>
    fun getAllCustomer():Result<List<Customer>>
    fun findCustumerById(idCustomer:Long):Result<Customer>
    fun deleteCustumer(customer: Customer):Result<Boolean>
    fun updateCustumer(idCustomer:Long):Result<Boolean>
}
