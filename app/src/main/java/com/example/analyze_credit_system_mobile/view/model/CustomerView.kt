package com.example.analyze_credit_system_mobile.view.model

import android.os.Parcelable
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
):Parcelable

fun CustomerView.toEntity() = Customer(
    firstName = this.firstName,
    lastName = this.lastName,
    cpf = this.cpf,
    income = this.income,
    email = this.email,
    address = Address(zipCode= this.zipCode, street = this.street),
    password = "***********",
    listCredits = mutableListOf(),
    id = -1
)