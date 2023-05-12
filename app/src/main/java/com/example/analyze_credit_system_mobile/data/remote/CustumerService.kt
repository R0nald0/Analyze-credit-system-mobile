package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import retrofit2.Response
import retrofit2.http.GET

interface CustumerService  {
  @GET
  fun gelAllCustomers() : Response<List<CustumerDTO>>

}