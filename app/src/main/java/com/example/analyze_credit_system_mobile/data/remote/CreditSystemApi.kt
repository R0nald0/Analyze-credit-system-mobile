package com.example.analyze_credit_system_mobile.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CreditSystemApi  {

     fun <T> createApi(classApi :Class<T>) :T{
          return  Retrofit.Builder()
              .baseUrl("")
              .addConverterFactory(GsonConverterFactory.create())
              .build()
              .create(classApi)
     }
}