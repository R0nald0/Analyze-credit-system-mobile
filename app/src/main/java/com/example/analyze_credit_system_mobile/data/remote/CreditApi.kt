package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CreditCreate
import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface CreditApi {
    @GET()
    fun getAllCredit() : Response<List<CreditDTO>>
    @GET("credit/credits?")
    fun findAllCreditByCustomer(@Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @GET("/credit/{creditCode}?")
    fun findByCreditCode(@Path("creditCode") creditId: UUID , @Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @POST("credit/save")
    suspend fun createCredit(@Body creditCredit: CreditCreate) : Response<CreditDTO>

    @GET("{creditId}")
    fun findCreditById(@Path("creditId") creditId :Long ) : Response<CreditDTO>

    @PATCH()
    fun updateCredit(@Query("creditId") creditId : Long, @Body creditCredit: CreditCreate): Response<CreditDTO>

    @DELETE("{CreditId}")
    fun deleteCredit(@Path("creditId")crediId:Long):Response<Boolean>
}