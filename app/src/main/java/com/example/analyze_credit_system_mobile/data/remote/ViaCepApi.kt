package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.AddressDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {

 /*   @GET("{zipCode}/json/")
    suspend fun getAddress(@Path("zipCode") zipCode:String):Response<AddressDto> */
    @GET("{zipCode}/json")
    suspend fun getAddress(@Path("zipCode") zipCode:String):Response<AddressDto>
}