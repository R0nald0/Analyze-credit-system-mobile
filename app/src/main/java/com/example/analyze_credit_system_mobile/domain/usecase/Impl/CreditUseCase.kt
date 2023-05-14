package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import javax.inject.Inject

class CreditUseCase @Inject constructor(
    private val creditRepository : ICreditRepositoty
): ICreditUseCase {
    override fun createCredit(credit: Credit): Result<Boolean> {
         try {
             val result = creditRepository.createCredit(credit)
             return Result.success(result)
         }catch (e:Exception){
             return Result.failure(Throwable("erro ao criar Credito",e))
         }
    }

    override fun getAllCredit(): Result<List<Credit>> {
        try {
            val listCredit = creditRepository.getAllCredit()
             listCredit.let {
                 return Result.success(it)
             }
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro buscar lista de credito"))
        }
    }

    override fun findCreditById(idCredit: Long): Result<Credit> {
       try {
           val credit = creditRepository.findCreditById(idCredit)
            if (credit != null){
                return Result.success(credit)
            }else{
                return Result.failure(Throwable("nenhum credito encontrado com esse identificador"))
            }
       }catch (e:Exception){
           e.printStackTrace()
           return  Result.failure(Throwable("erro buscar credito",e))
       }
    }

    override fun deleteCredit(credit: Credit): Result<Boolean> {
        try {
            val result = creditRepository.deleteCredit(credit)
            return Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro ao deletar",e))
        }
    }

    override fun updateCredit(idCredit: Long): Result<Boolean> {
         try {
              val credit = this.findCreditById(idCredit).getOrThrow()
             val result = creditRepository.updateCredit(idCredit,credit)
              return Result.success(result)
         }catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Throwable("erro ao atualizar ",e))
         }
    }
}