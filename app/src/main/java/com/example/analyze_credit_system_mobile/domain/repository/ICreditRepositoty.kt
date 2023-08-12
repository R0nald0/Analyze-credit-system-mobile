package com.example.analyze_credit_system_mobile.domain.repository

import com.example.analyze_credit_system_mobile.domain.model.Credit

interface ICreditRepositoty {
  suspend  fun createCredit(Credit: Credit):Result<Credit>
    suspend fun getAllCredit(customerId:Long):List<Credit>
  suspend  fun findCreditById(idCredit:Long): Credit?
   suspend fun deleteCredit(credit: Credit):Boolean
    suspend  fun updateCredit(idCredit:Long,credit: Credit):Boolean
}
