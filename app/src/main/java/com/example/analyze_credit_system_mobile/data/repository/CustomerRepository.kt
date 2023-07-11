package com.example.analyze_credit_system_mobile.data.repository


import com.example.analyze_credit_system_mobile.data.dto.CustomerDTO
import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.data.remote.service.CustomerService
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
         }catch (firebaseAuthInvalidCredentialsException : FirebaseAuthInvalidCredentialsException){
             throw firebaseAuthInvalidCredentialsException
         }catch (emailFirebaseExeption: FirebaseAuthUserCollisionException){
              throw emailFirebaseExeption
         }
         catch (e:Exception){
              e.printStackTrace()
               throw Exception("${e.message}")
         }
    }

    override suspend fun loginCustomer(email:String , password:String): Result<Customer> {
          try {
             val customerViewDTOResult  = customerService.loginCustomer(email,password)
             val customerResult = customerViewDTOResult.getOrThrow()
             return   Result.success(customerResult.toCustomer())
          }catch (firebaseAuthInvalidUserException: FirebaseAuthInvalidUserException){
              throw firebaseAuthInvalidUserException
          }
          catch (ex :Exception){
              throw ex
          }
    }

    override suspend fun currentUser(): Result<Customer> {

        try {
            val customerViewDTOResult = customerService.getCurrentuser()
              if (customerViewDTOResult.isSuccess){
                    val customerViewDTO  = customerViewDTOResult.getOrNull()
                      if (customerViewDTO != null){
                          val customer = customerViewDTO.toCustomer()
                          return  Result.success(customer)
                      }
              }
                return Result.failure(Exception("customer não encontrado"))
        }catch (exeption:Exception){
            throw exeption
        }
    }

    override suspend fun findCustumerById(): Result<Customer?> {
        try {
            val currentuser = customerService.getCurrentuser()
                if (currentuser.isSuccess){
                    val toCustomer = currentuser.getOrThrow()?.toCustomer()
                    return Result.success(toCustomer)
                }
                return Result.failure(Exception(currentuser.exceptionOrNull()?.message))
        }catch (ex:Exception){
            throw ex
        }

    }

    override suspend fun findCustumerByEmail(email: String): Customer {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustumer(customer: Customer): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateCustumer(idCustomer: Long, customerDTO: CustomerDTO): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun logout():Boolean{
        return customerService.logout()
    }


}