package com.example.analyze_credit_system_mobile.data.remote.service

import android.util.Log
import com.example.analyze_credit_system_mobile.data.dto.toAddress
import com.example.analyze_credit_system_mobile.data.remote.ViaCepApi
import com.example.analyze_credit_system_mobile.domain.model.Address
import retrofit2.Response
import javax.inject.Inject


class AddressService @Inject constructor(
    private val addressApi:ViaCepApi
)  {

    suspend fun getAddress(zipCode:String):Result<Address>{
             try {
                 val response  = addressApi.getAddress(zipCode)
                 val resultApi = callApiMethod(response)
                 if (resultApi.isSuccess){
                      val addressDto = resultApi.getOrThrow().body()
                      if (addressDto !=null){
                          return Result.success(addressDto.toAddress())
                      }
                     Log.d("INFO_", "getAddress: ${resultApi.getOrThrow().code()}")
                 }
                 return Result.failure(Exception("erro is not succefull ${resultApi.getOrNull()?.code()}"))
             }catch (nullPointer:NullPointerException){
                  throw nullPointer
             }
             catch (ex:Exception){
                 throw ex
             }
    }

    suspend fun <T> callApiMethod(response:Response<T>):Result<Response<T> >{
        try {
            return Result.success(response)
        }catch (ex:Exception){
            throw Exception("erro when call api ${ex.message}")
        }
    }

}