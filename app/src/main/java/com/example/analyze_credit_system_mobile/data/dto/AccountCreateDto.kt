package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Account
import java.math.BigDecimal

data class AccountCreateDto(
    val accountBalanceBlocked: BigDecimal,
    val accountFreeBalance: BigDecimal,
)

fun AccountCreateDto.toAccount() = Account(
     accountFreeBalance = this.accountFreeBalance,
    accountBalanceBlocked = this.accountBalanceBlocked
)