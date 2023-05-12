package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import java.math.BigDecimal

data class Customer(
 private val fistName :String,
 private val lastName :String,
 private val cpf :String,
 private val email :String,
 private val password :String,
 private val address : Address,
 private val income : BigDecimal,
 private val listCredits:MutableList<CreditDTO>,
 private val id : Long
 ) {
}