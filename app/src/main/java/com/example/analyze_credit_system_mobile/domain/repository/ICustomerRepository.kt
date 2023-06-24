package com.example.analyze_credit_system_mobile.domain.repository

import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer

interface ICustomerRepository {
    suspend fun createCustumer(customer: Customer):Customer?
  // suspend  fun getAllCustomer():List<Customer>
    suspend fun findCustumerById(idCustomer:Long):Customer?
    suspend fun deleteCustumer(customer: Customer):Boolean
    suspend fun updateCustumer(idCustomer:Long,custumerDTO: CustumerDTO):Boolean

}
