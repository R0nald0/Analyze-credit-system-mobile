package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CreditCreate
import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface CreditApi {
    @GET()
   suspend fun getAllCredit() : Response<List<CreditDTO>>
    @GET("credit/credits?")
  suspend  fun findAllCreditByCustomer(@Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @GET("/credit/{creditCode}?")
 suspend    fun findByCreditCode(@Path("creditCode") creditId: UUID , @Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @POST("credit/save")
    suspend fun createCredit(@Body creditCredit: CreditCreate) : Response<CreditDTO>

    @GET("{creditId}")
    suspend   fun findCreditById(@Path("creditId") creditId :Long ) : Response<CreditDTO>

    @PATCH()
    suspend  fun updateCredit(@Query("creditId") creditId : Long, @Body creditCredit: CreditCreate): Response<CreditDTO>

    @DELETE("{CreditId}")
    suspend fun deleteCredit(@Path("creditId")crediId:Long):Response<Boolean>
}