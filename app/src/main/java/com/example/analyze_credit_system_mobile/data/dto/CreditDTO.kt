package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditDTO(
     private val creditCode :UUID  =UUID.randomUUID(),
     private val creditValue :BigDecimal,
     private val dayFistInstallment :LocalDate,
     private val numberOfInstallments:Int,
     private val status : Status,
     private val customer: Customer,
     private val id :Long
) {

}