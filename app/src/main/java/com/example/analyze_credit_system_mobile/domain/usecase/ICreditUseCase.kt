package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Credit

interface ICreditUseCase {

    fun createCredit(credit: Credit):Result<Boolean>
    fun getAllCredit():Result<List<Credit>>
    fun findCreditById(idCredit:Long):Result<Credit>
    fun deleteCredit(Credit: Credit):Result<Boolean>
    fun updateCredit(idCredit:Long):Result<Boolean>

}