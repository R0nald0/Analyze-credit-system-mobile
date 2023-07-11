package com.example.analyze_credit_system_mobile.view.model

import android.os.Parcelable
import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Credit
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

@Parcelize
data class CreditView(
    val creditValue :BigDecimal,
    val numberOfInstallments :Int,
    val dayFistInstallment : Long,
    val customerView: CustomerView
    ):Parcelable

fun CreditView.toCredit(): Credit {
    return Credit(
        creditCode = UUID.randomUUID(),
        creditValue = this.creditValue,
        dayFistInstallment = this.dayFistInstallment,
        numberOfInstallments =this.numberOfInstallments,
        status = Status.IN_PROGRESS,
        customer = customerView.toEntity(),
        id = null
    )
}