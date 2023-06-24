package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import java.math.BigDecimal

data class Customer(
    val firstName :String,
    val lastName :String,
    val cpf :String,
    val email :String,
    val password :String,
    val address : Address,
    val income : BigDecimal,
    val listCredits:MutableList<CreditDTO>,
 )

fun Customer.toDTO() =CustumerDTO(
   fistName = this.firstName,
   lastName = this.lastName,
   cpf =  this.cpf,
   email = this.email,
   passoword = this.password,
   street =  this.address.street,
    zipCode = this.address.zipCode,
    income = this.income,
)