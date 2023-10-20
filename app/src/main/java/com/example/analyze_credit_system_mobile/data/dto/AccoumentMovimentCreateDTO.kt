package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.view.enums.TitulosMovimentacao

data class AccoumentMovimentCreateDTO(
    val dateMoviment: Int,
    val idCustomer: Int,
    val movimentValue: Double,
    val type: TitulosMovimentacao
)