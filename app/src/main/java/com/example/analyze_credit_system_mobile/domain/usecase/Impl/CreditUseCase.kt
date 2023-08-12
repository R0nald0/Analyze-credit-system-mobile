package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import java.util.Calendar
import javax.inject.Inject

class CreditUseCase @Inject constructor(
    private val creditRepository : ICreditRepositoty,

): ICreditUseCase {
    override suspend fun createCredit(credit: Credit): Result<Credit> {
         try {
             val result = creditRepository.createCredit(credit)
             if (result.isSuccess){
                 return result
             }
             return Result.failure(Throwable("customer is null ${result.exceptionOrNull()}"))

         } catch (illegalArgumentException:IllegalArgumentException){
               illegalArgumentException.printStackTrace()
               return Result.failure(IllegalArgumentException("Dados do crédito inválidos,preencha os dados corretamente"))
         }
         catch (e:Exception){
              throw e
         }
    }

    override suspend fun getAllCreditByCustomer(customerId:Long): Result<List<Credit>>{
        try {
            val listCredit = creditRepository.getAllCredit(customerId)
             listCredit.let {
                 return Result.success(it)
             }
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro buscar lista de credito"))
        }
    }

    override suspend fun findCreditById(idCredit: Long): Result<Credit> {
       try {
           val credit = creditRepository.findCreditById(idCredit)
            if (credit != null){
                return Result.success(credit)
            }else{
                return Result.failure(Throwable("nenhum credito encontrado com esse identificador"))
            }
       }catch (e:Exception){
           e.printStackTrace()
           return  Result.failure(Exception("erro buscar credito",e))
       }
    }

    override suspend fun deleteCredit(credit: Credit): Result<Boolean> {
        try {
            val result = creditRepository.deleteCredit(credit)
            return Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro ao deletar",e))
        }
    }

    override suspend fun updateCredit(idCredit: Long): Result<Boolean> {
         try {
              val credit = this.findCreditById(idCredit).getOrThrow()
             val result = creditRepository.updateCredit(idCredit,credit)
              return Result.success(result)
         }catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Throwable("erro ao atualizar ",e))
         }
    }
     override fun calculateInstallment(numberInstallment: Int,valueCredit:Double) :Double{
        return  valueCredit/ numberInstallment
    }
     override fun getLimitsDate(field :Int, amountTime: Int) :Long {
        val dataMinima = Calendar.getInstance()
        dataMinima.time
        dataMinima.add(field,amountTime)
        return  dataMinima.time.time
    }


}