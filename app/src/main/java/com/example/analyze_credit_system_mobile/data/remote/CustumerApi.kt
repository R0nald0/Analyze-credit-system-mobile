package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustumerApi  {
  @GET
 suspend  fun getAllCustomers() : Response<List<CustumerDTO>>

  @POST("customers/save")
 suspend  fun createCustomer(@Body customerDTO: CustumerDTO) : Response<CustomerViewDTO>

  @GET("customers/{customerId}")
 suspend  fun findCustumerById(@Path("customerId") customerId :Long ) :Response<CustumerDTO>

  @PATCH("customers/?")
  suspend fun updateCustomer(@Query("customerId") custumerId : Long,@Body custumerDTO: CustumerDTO):Response<Boolean>

  @DELETE("customers/{customerId}")
 suspend  fun deleteCustomer(@Path("customerId") custumerId:Long):Response<Boolean>
}