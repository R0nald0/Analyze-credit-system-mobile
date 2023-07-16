package com.example.analyze_credit_system_mobile.view.model

import android.os.Parcelable
import com.example.analyze_credit_system_mobile.domain.model.Credit

import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class CreditCreateView(
    val creditValue:BigDecimal,
    val numberOfInstallments:Int,
    val dayFistInstallment: Long,
    val customerView: CustomerView?
    ):Parcelable

fun CreditCreateView.toCredit()= Credit(
         creditCode = null,
         creditValue =this.creditValue,
         dayFistInstallment = this.dayFistInstallment,
         numberOfInstallments = this.numberOfInstallments,
         id = null,
         status = null,
         idCusotmer = customerView?.id
     )
