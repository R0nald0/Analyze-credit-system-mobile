package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.view.enums.TitulosMovimentacao
import java.math.BigDecimal

data class AccountMovement (
    val id: Long? =null,
    val customer: Customer,
    val dateMoviment : Long,
    val type : TitulosMovimentacao,
    val movimentValue : BigDecimal = BigDecimal.valueOf(0)
)
