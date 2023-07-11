package com.example.analyze_credit_system_mobile.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApiClient {
     fun <T> createApi(classApi :Class<T>,baseUrl:String):T{
          return  Retrofit.Builder()
              .baseUrl(baseUrl)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
              .create(classApi)
     }

    fun <T> consultApi(response: Response<T>):Response<T>{
           try {
               return response
           }catch (execptionAPi : Exception){
               throw execptionAPi
           }
    }
}