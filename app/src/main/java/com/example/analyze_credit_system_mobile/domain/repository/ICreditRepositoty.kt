package com.example.analyze_credit_system_mobile.domain.repository

import com.example.analyze_credit_system_mobile.domain.model.Credit

interface ICreditRepositoty {
    fun createCredit(Credit: Credit):Boolean
    fun getAllCredit():List<Credit>
    fun findCreditById(idCredit:Long): Credit?
    fun deleteCredit(credit: Credit):Boolean
    fun updateCredit(idCredit:Long,credit: Credit):Boolean
}
