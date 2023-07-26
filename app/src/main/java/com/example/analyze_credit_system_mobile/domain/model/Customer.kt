package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import java.math.BigDecimal

data class Customer(
    val id:Long?,
    val firstName :String,
    val lastName :String,
    val cpf :String,
    val email :String,
    val password :String,
    val address : Address,
    val income : BigDecimal,
    val listCredits:MutableList<CreditDTO>,
    val account :Account,
 )
