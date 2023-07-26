package com.example.analyze_credit_system_mobile.data.dto


import com.example.analyze_credit_system_mobile.domain.model.Account
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal

data class CustomerCreateDto(
    val cpf: String,
    val email: String,
    val fistName: String,
    val income: BigDecimal,
    val lastName: String,
    val password: String,
    val street: String,
    val zipCode: String,
    val account : Account
){
    constructor(customer: Customer):this(
        email = customer.email,
       account =customer.account,
        fistName =customer.firstName,
        income = customer.income,
        lastName = customer.lastName,
        cpf = customer.cpf,
        password = customer.password,
        street = customer.address.street,
        zipCode= customer.address.zipCode ,
    )
}