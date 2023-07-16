package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.domain.model.Credit
import java.math.BigDecimal
import java.util.UUID


data class CreditDTO(
    var creditCode :UUID?,
    var creditValue: BigDecimal,
    var numberOfInstallment : Int,
    var status: Status?,
    val dayFirstOfInstallment : Long,
    val idCustomer: Long,
    val id :Long?
)


fun CreditDTO.toCredit() = Credit(
    creditValue = this.creditValue,
    creditCode = this.creditCode,
    numberOfInstallments = this.numberOfInstallment,
    status = this.status,
    dayFistInstallment = this.dayFirstOfInstallment,
    id = this.id,
    idCusotmer = this.idCustomer
)
