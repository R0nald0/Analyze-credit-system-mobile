package com.example.analyze_credit_system_mobile.data.dto.response

import com.example.analyze_credit_system_mobile.domain.model.Account
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal

data class CustomerViewDTO(
    val fistName:String,
    val lastName:String,
    val cpf :String,
    val income :BigDecimal,
    val email: String,
    val zipCode:String,
    val street :String,
    val id: Long?,
    var accountFreeBalance: BigDecimal,
    var accountBalanceBlocked :BigDecimal ,
    val numberAccount:Long
)
fun CustomerViewDTO.toCustomer()=Customer (
    firstName = this.fistName,
    lastName = this.lastName,
    cpf = this.cpf,
    income = this.income,
    email = this.email,
    address = Address(zipCode = this.zipCode,street = this.street),
    password = "",
    account = Account(
       accountFreeBalance =  this.accountFreeBalance,
       accountBalanceBlocked = this.accountBalanceBlocked,
        numberAccount=this.numberAccount
    ),
    listCredits = mutableListOf(),
    id = this.id
)
