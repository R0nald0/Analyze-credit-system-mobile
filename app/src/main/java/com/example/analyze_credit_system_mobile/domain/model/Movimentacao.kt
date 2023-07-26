package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.domain.enums.TitulosMovimentacao
import java.math.BigDecimal

data class Movimentacao(
    val descricao :TitulosMovimentacao,
    val valor :BigDecimal,
    val data : Long
)
