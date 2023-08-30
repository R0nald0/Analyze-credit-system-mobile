package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CustomerCreateDto
import com.example.analyze_credit_system_mobile.data.dto.response.CustomerViewDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustumerApi  {

  @POST("customers/save")
 suspend  fun createCustomer(@Body customerDTO:CustomerCreateDto) : Response<CustomerViewDTO>

 @GET("customers/findemail")
 suspend fun findCustomerByEmail(@Query("email")  email:String) :Response<CustomerViewDTO>

 @GET("customers/findaccountnumber?")
 suspend  fun findCustomerByAccountNumber(@Query("accountNumber") accountNumber:Long):Response<CustomerViewDTO>
  @GET("customers/{customerId}")
 suspend  fun findCustomerById(@Path("customerId") customerId :Long ) :Response<CustomerViewDTO>

  @PATCH("customers?")
  suspend fun updateCustomer(@Query("customerId") custumerId : Long,@Body customerDTO: CustomerCreateDto):Response<Boolean>

  @DELETE("customers/{customerId}")
 suspend  fun deleteCustomer(@Path("customerId") custumerId:Long):Response<Boolean>
}