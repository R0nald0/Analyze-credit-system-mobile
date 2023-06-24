package com.example.analyze_credit_system_mobile.data.repository


import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.service.CustomerService
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import retrofit2.Response
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerService: CustomerService
) :ICustomerRepository{
    override suspend fun createCustumer(customer: Customer): Customer? {
        val customerDt =customer.toDTO()
         try {
               val custumerApi = customerService.registerUser(customerDt)
              /* if (custumerApi != null){
                   return custumerApi.toCustomer()
               }*/
             return custumerApi.getOrThrow().toCustomer()
         }catch (fbExecption : FirebaseAuthInvalidCredentialsException){
             throw FirebaseAuthInvalidCredentialsException(fbExecption.errorCode,"erro repo firebase :${fbExecption.message}")
         }
         catch (e:Exception){
              e.printStackTrace()
               throw Exception("${e.message}")
         }
    }

    override suspend fun findCustumerById(idCustomer: Long): Customer? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustumer(customer: Customer): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateCustumer(idCustomer: Long, custumerDTO: CustumerDTO): Boolean {
        TODO("Not yet implemented")
    }


}