package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.MoedaDto
import com.example.analyze_credit_system_mobile.data.dto.Post
import com.example.analyze_credit_system_mobile.data.dto.asd
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CotacaoApi {

    @GET("last/USD,EUR,BTC")
   suspend  fun getContacoes() :Response<MoedaDto>
    @GET("posts")
    suspend  fun post() :Response<List<Post>>
}