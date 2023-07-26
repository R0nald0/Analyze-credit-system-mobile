package com.example.analyze_credit_system_mobile.domain.model

import com.example.analyze_credit_system_mobile.data.dto.AccountCreateDto
import java.math.BigDecimal

data class Account(
    var accountFreeBalance: BigDecimal = BigDecimal.ZERO,
    var accountBalanceBlocked :BigDecimal = BigDecimal.ZERO,
    val numberAccount:Long ?= null,
    var movements :MutableList<AccountMovement> = mutableListOf()
    )

fun Account.toAccountCreate()=AccountCreateDto(
    accountBalanceBlocked = this.accountBalanceBlocked ,
    accountFreeBalance = this.accountFreeBalance
)
