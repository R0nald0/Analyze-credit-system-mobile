package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.view.enums.Status
import java.math.BigDecimal
import java.util.UUID

data class Credit(
    val creditCode : UUID?,
    val creditValue : BigDecimal,
    val dayFistInstallment : Long,
    val numberOfInstallments:Int,
    val status : Status?,
    var idCusotmer: Long?,
    val id :Long?
)
