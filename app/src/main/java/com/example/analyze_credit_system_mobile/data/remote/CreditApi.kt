package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface CreditApi {
    @GET()
    fun getAllCredit() : Response<List<CreditDTO>>
    @GET("/credits?")
    fun findAllCreditByCustomer(@Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @GET("/credit/{creditCode}?")
    fun findByCreditCode(@Path("creditCode") creditId: UUID , @Query("customerId") customerId:Long):Response<List<CreditDTO>>

    @POST("/save")
    fun createCredit(@Body creditDTO: CreditDTO) : Response<String>

    @GET("{creditId}")
    fun findCreditById(@Path("creditId") creditId :Long ) : Response<CreditDTO>

    @PATCH()
    fun updateCredit(@Query("creditId") creditId : Long, @Body creditDTO: CreditDTO): Response<Boolean>

    @DELETE("{CreditId}")
    fun deleteCredit(@Path("creditId")crediId:Long):Response<Boolean>
}