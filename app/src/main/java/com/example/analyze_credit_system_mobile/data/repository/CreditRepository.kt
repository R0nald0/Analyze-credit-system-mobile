package com.example.analyze_credit_system_mobile.data.repository

import com.example.analyze_credit_system_mobile.data.dto.toCredit
import com.example.analyze_credit_system_mobile.data.remote.CreditApi
import com.example.analyze_credit_system_mobile.domain.model.Credit
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import javax.inject.Inject

class CreditRepository @Inject  constructor(
    private val serviceCredit: CreditApi
) :ICreditRepositoty {
    override fun createCredit(credit: Credit): String {
        try {
            val response =  serviceCredit.createCredit(credit.toDTO())
            if (response.isSuccessful){
                val result = response.body()
                if (result !=null){
                    return result
                }else return "Credito nao criado"
            }else{
                return "sem sucesso para criar o credit ${response.code()}"
            }

        }catch (e:Exception){
            e.printStackTrace()
            throw e
        }
    }

    override fun getAllCredit(): List<Credit> {try{
        val response = serviceCredit.getAllCredit()
        if (response.isSuccessful){
            val listCredit= response.body()
            val creditList  = listCredit?.map {creditDTO ->
                   creditDTO.toCredit()
            }
            if (creditList != null) return creditList
            else  return listOf()
        }else{
            return listOf()
        }
    }catch (e:Exception){
        e.printStackTrace()
        return throw java.lang.RuntimeException("falha ao buscar dados")
    }
    }

    override fun findCreditById(idCredit: Long): Credit? {
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

    override fun deleteCredit(credit: Credit): Boolean {
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

    override fun updateCredit(idCredit: Long, credit: Credit): Boolean {
        try {
            val response = serviceCredit.updateCredit(idCredit,credit.toDTO())
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