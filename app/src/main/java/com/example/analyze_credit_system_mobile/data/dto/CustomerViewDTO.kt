package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal

data class CustomerViewDTO(
    val fistName:String = "",
    val lastName:String ="",
    val cpf :String="",
    val income : BigDecimal= BigDecimal.ZERO,
    val email: String="",
    val zipCode:String="",
    val street :String ="",
    val password :String = ""
){
     fun getCustomer () {

     }
}

fun CustomerViewDTO.toCustomer() = Customer(
    firstName = this.fistName,
    lastName = this.lastName,
    cpf =  this.cpf,
    email = this.email,
    password = this.password,
    address = Address(zipCode = this.zipCode, street = this.street),
    income = this.income,
    listCredits = mutableListOf()
)