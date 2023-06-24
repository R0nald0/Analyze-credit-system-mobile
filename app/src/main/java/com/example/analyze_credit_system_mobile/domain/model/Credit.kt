package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.view.model.CreditView
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class Credit(
    val creditCode : UUID = UUID.randomUUID(),
    val creditValue : BigDecimal,
    val dayFistInstallment : Long,
    val numberOfInstallments:Int,
    val status : Status,
    var customer: Customer?,
    val id :Long?
)



fun Credit.toDTO() = CreditDTO(
    creditCode = this.creditCode,
    creditValue=this.creditValue,
    dayFistInstallment = this.dayFistInstallment,
    numberOfInstallments= this.numberOfInstallments,
    status = this.status,
    customer = this.customer!!,
    id = this.id!!
)
