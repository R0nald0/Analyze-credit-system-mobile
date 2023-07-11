package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal

data class CustomerViewDTO(
    val cpf: String,
    val email: String,
    val fistName: String,
    val id: Long,
    val income: BigDecimal,
    val lastName: String,
    val street: String,
    val zipCode: String
){
    constructor(customer: Customer):this(
        fistName = customer.firstName,
        lastName =  customer.lastName,
        cpf =  customer.cpf,
        email = customer.email,
        income = customer.income,
        zipCode = customer.address.zipCode,
        street = customer.address.zipCode,
        id = customer.id!!
    )
}
fun CustomerViewDTO.toCustomer()=Customer (
    firstName = this.fistName,
    lastName = this.lastName,
    cpf = this.cpf,
    income = this.income,
    email = this.email,
    address = Address(zipCode = this.zipCode,street = this.street),
    password = "",
    id = this.id,
    listCredits = mutableListOf()
)
