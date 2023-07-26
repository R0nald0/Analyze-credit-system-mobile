package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountMovimentsApi {

    @GET("movimentation/{idCustomer}")
  suspend  fun getAllMovimentationAccountCustomer(@Path("idCustomer") idCustomer:Long):Response<List<AccountMovimentView>>
}