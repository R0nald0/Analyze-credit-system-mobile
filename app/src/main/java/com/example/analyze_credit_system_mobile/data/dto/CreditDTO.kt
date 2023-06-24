package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.model.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditDTO(
     val creditCode :UUID  =UUID.randomUUID(),
     val creditValue :BigDecimal,
     val dayFistInstallment :Long,
     val numberOfInstallments:Int,
     val status : Status,
     val customer: Customer,
     val id :Long
)

fun CreditDTO.toCredit() = Credit(
     creditCode = this.creditCode,
     creditValue=this.creditValue,
     dayFistInstallment = this.dayFistInstallment,
     numberOfInstallments= this.numberOfInstallments,
     status = this.status,
     customer = this.customer,
     id = this.id
)
