package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import com.example.analyze_credit_system_mobile.data.dto.CustomerDTO
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import java.math.BigDecimal

data class Customer(
    val id:Long?,
    val firstName :String,
    val lastName :String,
    val cpf :String,
    val email :String,
    val password :String,
    val address : Address,
    val income : BigDecimal,
    val listCredits:MutableList<CreditDTO>,
 )

fun Customer.toView() = CustomerView(
    firstName = this.firstName,
    lastName = this.lastName,
    cpf = this.cpf,
    email = this.email,
    street = this.address.street,
    zipCode = this.address.zipCode,
    income = this.income
)
fun Customer.toDTO() =CustomerDTO(
   fistName = this.firstName,
   lastName = this.lastName,
   cpf =  this.cpf,
   email = this.email,
   password = this.password,
   street =  this.address.street,
    zipCode = this.address.zipCode,
    income = this.income,
)