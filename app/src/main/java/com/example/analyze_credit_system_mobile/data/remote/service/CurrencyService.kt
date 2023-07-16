package com.example.analyze_credit_system_mobile.data.remote.service

import android.util.Log
import com.example.analyze_credit_system_mobile.data.dto.MoedaDto
import com.example.analyze_credit_system_mobile.data.dto.Post
import com.example.analyze_credit_system_mobile.data.remote.CotacaoApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class CurrencyService @Inject constructor(
    private val cotacaoApi : CotacaoApi
)  {
   private lateinit var response : Response<MoedaDto>

   suspend  fun getCurrecys() : Result<MoedaDto?>{
            try {
                response =  cotacaoApi.getContacoes()
                if (response.isSuccessful){
                  if (response.body() != null){
                        val moedaDto =  response.body()
                        return Result.success(moedaDto)
                    }else{
                        return Result.failure(Throwable("erro ${response.code()}  "))
                    }
                }else{
                    return Result.failure(Throwable("not sucesseful ${response.code()} "))
                }
            } catch (execption: Exception) {
                execption.stackTrace
                throw execption
            }

    }

    /*
        suspend fun getCurrecys() {
            var response : Response<List<MoedaDto>>? = null
            try {
                response = cotacaoApi.getContacoes()
            } catch (e: Exception) {
                e.stackTrace
                throw Exception("erro ao buscas na api")
            }

            if (response.isSuccessful) {
                val currencys = response.body()
              if (currencys != null) {
                   currencys.forEach {
                        Log.i("INFO_", "getCurrecys: ${it.USDBRL.high}")
                    }
                } else {
                    Log.i("INFO_", "getCurrecys: erro currenccy null")
                }
            } else {
                Log.i("INFO_", "getCurrecys: erro currenccy null")
            }

    }
     */
}
