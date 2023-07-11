package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Credit

interface ICreditUseCase {

  suspend  fun createCredit(credit: Credit):Result<String>
    suspend fun getAllCredit():Result<List<Credit>>
   suspend fun findCreditById(idCredit:Long):Result<Credit>
    suspend fun deleteCredit(Credit: Credit):Result<Boolean>
   suspend  fun updateCredit(idCredit:Long):Result<Boolean>
   fun  calculateInstallment(numberInstallment: Int,valueCredit:Double) :Double
    fun getLimitsDate( field :Int , amountTime: Int) :Long


}