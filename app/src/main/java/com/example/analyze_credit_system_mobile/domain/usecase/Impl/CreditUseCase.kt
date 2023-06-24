package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.data.repository.AddressRespository
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.model.toCredit
import javax.inject.Inject

class CreditUseCase @Inject constructor(
    private val creditRepository : ICreditRepositoty,

): ICreditUseCase {
    override suspend fun createCredit(credit: Credit): Result<String> {
         try {
             val customer = Customer("miau","silva","16225601082","miau@gmail.com","23123", Address("40226","433"),133.0.toBigDecimal(),
                 mutableListOf()
             )
             credit.customer = customer
             if (credit.customer !=null){
                 val result = "Sucesso ao criar"//creditRepository.createCredit(credit)
                 return Result.success(result)
             }else{
                 return Result.failure(Throwable("customer is null"))
             }
         }catch (e:Exception){
             return Result.failure(Throwable("erro ao criar Credito",e))
         }
    }

    override suspend fun getAllCredit(): Result<List<Credit>> {
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
           return  Result.failure(Throwable("erro buscar credito",e))
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


}