package com.example.analyze_credit_system_mobile.data.dto.response

import com.example.analyze_credit_system_mobile.view.enums.TitulosMovimentacao
import com.example.analyze_credit_system_mobile.domain.model.AccountMovement
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.convertDateLongToString
import java.math.BigDecimal
import java.util.Date

data class AccountMovimentView(
    val dateMoviment : String,
    val type : TitulosMovimentacao,
    val movimentValue : BigDecimal,
){
    constructor(accountMovement: AccountMovement):this(
        dateMoviment = Date().convertDateLongToString(accountMovement.dateMoviment)!!,
        movimentValue = accountMovement.movimentValue,
        type = accountMovement.type
    )
}

