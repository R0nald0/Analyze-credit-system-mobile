package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import java.math.BigDecimal

data class CustumerDTO(
     val fistName :String,
     val lastName :String,
     val cpf :String,
     val email :String,
     val password :String,
     val address : Address,
     val income :BigDecimal,
     val listCredits:MutableList<CreditDTO>,
     val id : Long
)

fun CustumerDTO.toCustomer() = Customer(
    fistName = this.fistName,
    lastName = this.lastName,
    cpf =  this.cpf,
    email = this.email,
    password = this.password,
    address =  this.address,
    income = this.income,
    id = this.id,
    listCredits = this.listCredits
)
