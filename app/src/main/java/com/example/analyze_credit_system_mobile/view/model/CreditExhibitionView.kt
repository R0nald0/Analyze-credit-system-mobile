package com.example.analyze_credit_system_mobile.view.model

import com.example.analyze_credit_system_mobile.view.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Credit
import java.math.BigDecimal
import java.util.UUID

data class CreditExhibitionView (
    val creditCode : UUID?,
    val creditValue : BigDecimal,
    val dayFistInstallment : Long,
    val numberOfInstallments:Int,
    val status : Status,
    var customerView: CustomerView?,
    val id :Long
    ){
    constructor(credit: Credit):this(
          creditValue = credit.creditValue,
          creditCode = credit.creditCode,
          dayFistInstallment =credit.dayFistInstallment,
          status = credit.status!!,
          numberOfInstallments = credit.numberOfInstallments,
          customerView =null,
          id = credit.id!!
    )
}