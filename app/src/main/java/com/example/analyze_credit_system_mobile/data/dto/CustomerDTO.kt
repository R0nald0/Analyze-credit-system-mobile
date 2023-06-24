package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal

data class CustumerDTO(
    val cpf: String,
    val email: String,
    val fistName: String,
    val income: BigDecimal,
    val lastName: String,
    val passoword: String,
    val street: String,
    val zipCode: String
)

fun CustumerDTO.toCustomer() = Customer(
    firstName = this.fistName,
    lastName = this.lastName,
    cpf =  this.cpf,
    email = this.email,
    password = this.passoword,
    address = Address(zipCode = this.zipCode, street = this.street),
    income = this.income,
    listCredits = mutableListOf()

)
