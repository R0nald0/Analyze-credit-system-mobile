package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Credit
import java.math.BigDecimal

interface ICreditUseCase {

  suspend  fun createCredit(credit: Credit):Result<Credit>
    suspend fun getAllCreditByCustomer(customerId:Long):Result<List<Credit>>
   suspend fun findCreditById(idCredit:Long):Result<Credit>
    suspend fun deleteCredit(Credit: Credit):Result<Boolean>
   suspend  fun updateCredit(idCredit:Long):Result<Boolean>
   fun  calculateInstallment(numberInstallment: Int,valueCredit:BigDecimal) :BigDecimal
    fun getLimitsDate( field :Int , amountTime: Int) :Long


}