package com.example.analyze_credit_system_mobile.data.remote.service

import android.util.Log
import com.example.analyze_credit_system_mobile.data.dto.AddressDto
import com.example.analyze_credit_system_mobile.data.remote.ViaCepApi

import retrofit2.Response
import javax.inject.Inject


class AddressService @Inject constructor(
    private val addressApi:ViaCepApi
)  {

    suspend fun getAddress(zipCode:String):Result<AddressDto>{
             try {
                 val response  = addressApi.getAddress(zipCode)
                 val resultApi = callApiMethod(response)
                 if (resultApi.isSuccess){
                      val addressDto = resultApi.getOrThrow().body()
                      if (addressDto !=null){
                          return Result.success(addressDto)
                      }
                     Log.d("INFO_", "getAddress: ${resultApi.getOrThrow().code()}")
                 }
                 return Result.failure(Exception("erro is not succefull ${resultApi.getOrNull()?.code()}"))
             }catch (nullPointer:NullPointerException){
                  throw nullPointer
             }
             catch (ex:Exception){
                 throw Exception("erro on api viacep ${ex.message}")
             }


            /* if (responseAddress.isSuccessful){
                 val address = responseAddress.body()
                 if (address != null) {
                      Result.success(address)
                     Log.d("INFO_", "getAddress: ${address.logradouro}")
                 }
                 else{
                     Log.d("INFO_", "getAddress: ${responseAddress.code()}")
                     Log.d("INFO_", "getAddress: ${responseAddress.errorBody()}")
                 }
                // return Result.failure(Exception("erro ao buscar null o cep ${responseAddress.code()}"))
             }else{
                 Log.d("INFO_", "getAddress: nao teve sucesso}")
             }*/
    }

    suspend fun <T> callApiMethod(response:Response<T>):Result<Response<T> >{
        try {
            return Result.success(response)
        }catch (ex:Exception){
            throw Exception("erro when call api ${ex.message}")
        }
    }

}