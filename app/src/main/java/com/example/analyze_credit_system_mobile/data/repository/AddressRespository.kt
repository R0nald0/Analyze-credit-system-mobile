package com.example.analyze_credit_system_mobile.data.repository

import com.example.analyze_credit_system_mobile.data.dto.toAddress
import com.example.analyze_credit_system_mobile.data.remote.service.AddressService
import com.example.analyze_credit_system_mobile.domain.model.Address
import javax.inject.Inject

class AddressRespository @Inject constructor(
     private val  addressService : AddressService
) {
     suspend fun getAddress(zipCode:String):Result<Address>{
           try {
               val resultAddress = addressService.getAddress(zipCode)
               val address =  resultAddress.getOrThrow().toAddress()
               return Result.success(address)
           }catch (nullPointer :NullPointerException){
                return Result.failure(nullPointer)
           }
           catch (ex:Exception){
                return Result.failure(ex)
           }
        }
}