package com.example.analyze_credit_system_mobile.view.model

import android.os.Parcelable
import com.example.analyze_credit_system_mobile.domain.model.Account
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer

import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class CustomerView(
    val firstName:String,
    val lastName:String,
    val cpf :String,
    val income : BigDecimal,
    val email: String,
    val zipCode:String,
    val street :String,
    val id :Long,
    var accountFreeBalance: BigDecimal,
    var accountBalanceBlocked :BigDecimal ,
    val numberAccount:Long?,
    val password :String?

):Parcelable {
    constructor(customer: Customer):this(
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        email = customer.email,
        password= null,
        street = customer.address.street,
        zipCode = customer.address.zipCode,
        income = customer.income,
        id = customer.id!!,
        accountFreeBalance = customer.account.accountFreeBalance,
        numberAccount = customer.account.numberAccount!!,
        accountBalanceBlocked = customer.account.accountBalanceBlocked
    )
}

fun CustomerView.toEntity() = Customer(
    firstName = this.firstName,
    lastName = this.lastName,
    cpf = this.cpf,
    income = this.income,
    email = this.email,
    address = Address(zipCode= this.zipCode, street = this.street),
    password = this.password!!,
    listCredits = mutableListOf(),
    id = this.id,
    account = Account(
        accountFreeBalance=this.accountFreeBalance,
        accountBalanceBlocked = this.accountBalanceBlocked,
        numberAccount = this.numberAccount
    )
)