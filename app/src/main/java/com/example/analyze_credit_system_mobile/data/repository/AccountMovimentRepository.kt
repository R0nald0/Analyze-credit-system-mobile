package com.example.analyze_credit_system_mobile.data.repository

import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import com.example.analyze_credit_system_mobile.data.remote.AccountMovimentsApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import retrofit2.Response
import javax.inject.Inject

class AccountMovimentRepository @Inject constructor(
    private val accountMovimentsApi: AccountMovimentsApi
) {
   suspend fun getMovimentsCustomer(idCustomer:Long):List<AccountMovimentView>{
        var response : Response<List<AccountMovimentView>>? =null
        try {
            val allMovimentationAccountCustomer = accountMovimentsApi.getAllMovimentationAccountCustomer(idCustomer)
           response = RetrofitApiClient.consultApi(allMovimentationAccountCustomer)

        }catch (illegalArgumentExceptionException:IllegalArgumentException){
            illegalArgumentExceptionException.printStackTrace()
            throw illegalArgumentExceptionException
        }
        catch (execption:Exception){
            execption.printStackTrace()
            throw execption
        }

        if (response.isSuccessful){
            val body = response.body()
            if (body != null ) return body
        }
        return listOf()
    }
}