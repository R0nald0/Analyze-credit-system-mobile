package com.example.analyze_credit_system_mobile.data.remote

import com.example.analyze_credit_system_mobile.data.dto.response.MoedaDto
import retrofit2.Response
import retrofit2.http.GET


interface CotacaoApi {

    @GET("last/USD,EUR,BTC")
   suspend  fun getContacoes() :Response<MoedaDto>

}