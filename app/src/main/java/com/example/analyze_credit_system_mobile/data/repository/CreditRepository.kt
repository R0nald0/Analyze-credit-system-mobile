package com.example.analyze_credit_system_mobile.data.repository

import com.example.analyze_credit_system_mobile.data.dto.CreditCreate
import com.example.analyze_credit_system_mobile.data.dto.toCredit
import com.example.analyze_credit_system_mobile.data.remote.CreditApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.firabase.MyFireStore
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import javax.inject.Inject

class CreditRepository @Inject  constructor(
    private val serviceCredit: CreditApi,
    private val fireStore :MyFireStore
) :ICreditRepositoty {
    override suspend fun createCredit(credit: Credit): Result<Credit> {
        try {
            val creditCreate = CreditCreate(credit)

             val response =  serviceCredit.createCredit(creditCreate)
             val responseDto =  RetrofitApiClient.consultApi(response)

            if (responseDto.isSuccessful){

                val creditDTO = response.body()
                if (creditDTO !=null){
                    fireStore.saveCredit(creditDTO)
                    return Result.success(creditDTO.toCredit())
                }else{
                    return Result.failure(Exception("dados nulo ${creditDTO}"))
                }
            }
            return  Result.failure(Exception("erro ao retornar dados da api ${response.errorBody()}  Status:${response.code()}"))
        }catch (illegalArgumentException:IllegalArgumentException){
            throw illegalArgumentException
        }
        catch (e:Exception){
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getAllCredit(customerId:Long): List<Credit> {
        try{
            val response = serviceCredit.findAllCreditByCustomer(customerId)
            if (response.isSuccessful){
                val listCredit= response.body()
                val creditList  = listCredit?.map {creditDTO ->
                       creditDTO.toCredit()
            }
            return if (creditList != null) creditList
            else listOf()
        }else{
            return listOf()
        }
    }catch (e:Exception){
         e.printStackTrace()
         throw e
    }
    }

    override suspend fun findCreditById(idCredit: Long): Credit? {
        try {
            val response = serviceCredit.findCreditById(idCredit)
            if (response.isSuccessful){
                val creditDto  = response.body()
                return creditDto?.toCredit()
            }else{
                return null
            }
        }catch (e:Exception){
            e.printStackTrace()
            return throw java.lang.RuntimeException("erro o encontrar buscar Credit")
        }
    }

    override suspend fun deleteCredit(credit: Credit): Boolean {
        try {
            val response = serviceCredit.deleteCredit(credit.id!!)
            if (response.isSuccessful){
                return (response.body()  != null)
            }else{
                return false
            }
        }catch (e:Exception){
            e.printStackTrace()
            return throw java.lang.RuntimeException("erro o encontrar deletar credit")
        }
    }

    override suspend fun updateCredit(idCredit: Long, credit: Credit): Boolean {
        try {
            val response = serviceCredit.updateCredit(idCredit,CreditCreate(credit))
            if (response.isSuccessful){
                return (response.body()  != null)
            }else{
                return false
            }
        }catch (e:Exception){
            e.printStackTrace()
            return throw java.lang.RuntimeException("erro o encontrar Customer")
        }
    }
}