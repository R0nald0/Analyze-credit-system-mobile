package com.example.analyze_credit_system_mobile.data.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.shared.extensions.convertDateLongToString
import java.math.BigDecimal
import java.time.LocalDate
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

