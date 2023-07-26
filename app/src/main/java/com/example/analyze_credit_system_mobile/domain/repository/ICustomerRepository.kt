package com.example.analyze_credit_system_mobile.domain.repository

import com.example.analyze_credit_system_mobile.data.dto.CustomerCreateDto
import com.example.analyze_credit_system_mobile.domain.model.Customer

interface ICustomerRepository {
    suspend fun createCustumer(customer: Customer):Customer?
    suspend fun currentUser():Result<Customer>
    suspend fun loginCustomer(email:String , password:String):Result<Customer>

    suspend fun findCustumerById():Result<Customer?>
    suspend fun findCustumerByEmail(email: String):Customer
    suspend fun deleteCustumer(customer: Customer):Boolean
    suspend fun updateCustumer(idCustomer:Long, customerDTO: CustomerCreateDto):Boolean
    suspend fun logout():Boolean

}
