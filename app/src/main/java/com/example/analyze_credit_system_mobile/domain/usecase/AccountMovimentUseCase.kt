package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import com.example.analyze_credit_system_mobile.data.repository.AccountMovimentRepository
import javax.inject.Inject

class AccountMovimentUseCase @Inject constructor(
    private val accountMovimentRepository: AccountMovimentRepository
) {
  suspend  fun getAccountMovimentsCustomer(idCustomer:Long):List<AccountMovimentView>{
       try {
           val movimentViewList = accountMovimentRepository.getMovimentsCustomer(idCustomer)
           return movimentViewList
       }
       catch (illegalArgumentExceptionException:IllegalArgumentException){
           illegalArgumentExceptionException.printStackTrace()
           throw IllegalArgumentException("illegal argument")
       }
       catch (execption:Exception){
           execption.printStackTrace()
           throw Exception("erro ao buscar lista moviments")
       }
    }

}