package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CustumerService  {
  @GET
  fun gelAllCustomers() : Response<List<CustumerDTO>>

  @POST
  fun createUser(@Body customerDTO: CustumerDTO) : Response<String>

}