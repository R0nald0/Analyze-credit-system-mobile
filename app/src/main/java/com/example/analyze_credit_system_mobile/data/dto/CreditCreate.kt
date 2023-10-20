package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.convertDateLongToString
import java.math.BigDecimal
import java.util.Date

data class CreditCreate(
    var creditValue: BigDecimal,
    var dayOfInstallment:String,
    val numberOfInstallment : Int,
    var customerId :Long?
    ){

    constructor(credit: Credit):this(
        creditValue = credit.creditValue,
        dayOfInstallment = Date().convertDateLongToString(credit.dayFistInstallment)!!,
        customerId = credit.idCusotmer,
        numberOfInstallment = credit.numberOfInstallments
    )
}

