package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import android.util.Log
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.remote.ViaCepApi
import com.example.analyze_credit_system_mobile.domain.model.Address

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import retrofit2.Response
import java.math.BigDecimal
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val custumerRepository: ICustomerRepository,

): ICustomerUseCase {

    override suspend fun createCustumer(customer: Customer): Result<Customer> {
         try {
             val customer = custumerRepository.createCustumer(customer)
                 if (customer != null ){
                     return Result.success(customer)
                 }
             return Result.failure(Throwable("nenhum customer foi salvo verifica os campos"))
         }catch (fbExecption : FirebaseAuthInvalidCredentialsException){
             return Result.failure(FirebaseAuthInvalidCredentialsException(fbExecption.errorCode,"erro ao salvar o customer,email deve conter @ e .com"))
         }
         catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Exception("erro ao cadastrar,dados inválidos",e))
         }
    }

    override suspend fun getAllCustomer(): Result<List<Customer>> {
        TODO("Not yet implemented")
    }

    override suspend fun findCustumerById(idCustomer: Long): Result<Customer> {
        try {
            val customer =custumerRepository.findCustumerById(idCustomer)
             if (customer != null){
                 return Result.success(customer)
             }else{
                 return Result.failure(Throwable("Identificador inválido"))
             }
        }
        catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro ao buscar custumer",e ))
        }
    }

    override suspend fun deleteCustumer(customer: Customer): Result<Boolean> {
        try{
            val result = custumerRepository.deleteCustumer(customer)
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro when delete customer",e))
        }
    }

    override suspend fun updateCustumer(idCustomer: Long): Result<Boolean> {
        try{
            val customer = findCustumerById(idCustomer).getOrThrow()
            val result = custumerRepository.updateCustumer(idCustomer,customer.toDTO())
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro atualizar Customer",e))
        }
    }



}