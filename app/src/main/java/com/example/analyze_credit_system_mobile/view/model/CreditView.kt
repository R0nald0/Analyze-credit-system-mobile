package com.example.analyze_credit_system_mobile.view.model

import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditView(
    val creditValue :BigDecimal,
    val numberOfInstallments :Int,
    val  dayFistInstallment : Long,
    )

fun CreditView.toCredit(): Credit {
    return Credit(
        creditCode = UUID.randomUUID(),
        creditValue = this.creditValue,
        dayFistInstallment = this.dayFistInstallment,
        numberOfInstallments =this.numberOfInstallments,
        status = Status.IN_PROGRESS,
        customer = null,
        id = null
    )
}