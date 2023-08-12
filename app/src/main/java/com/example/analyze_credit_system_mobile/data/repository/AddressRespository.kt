package com.example.analyze_credit_system_mobile.data.repository

import com.example.analyze_credit_system_mobile.data.remote.service.AddressService
import com.example.analyze_credit_system_mobile.domain.model.Address
import java.net.SocketTimeoutException
import javax.inject.Inject

class AddressRespository @Inject constructor(
     private val  addressService : AddressService
) {
     suspend fun getAddress(zipCode:String):Result<Address>{
           try {
               val resultAddress = addressService.getAddress(zipCode)
               val address =  resultAddress.getOrThrow()
               return Result.success(address)
           }
           catch ( sockteTimeOut  : SocketTimeoutException){
               throw sockteTimeOut
           }
           catch (nullPointer :NullPointerException){
                throw nullPointer
           }
           catch (ex:Exception){
                throw ex
           }
        }
}