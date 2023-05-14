package com.example.analyze_credit_system_mobile.domain.repository

import com.example.analyze_credit_system_mobile.domain.model.Customer

interface ICustomerRepository {
    fun createCustumer(customer: Customer):String
    fun getAllCustomer():List<Customer>
    fun findCustumerById(idCustomer:Long):Customer?
    fun deleteCustumer(customer: Customer):Boolean
    fun updateCustumer(idCustomer:Long):Boolean
}
