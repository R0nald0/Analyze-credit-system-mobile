package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.model.toView
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
         }catch (emailFirebaseExeption: FirebaseAuthUserCollisionException){
             return Result.failure(FirebaseAuthUserCollisionException(emailFirebaseExeption.errorCode,"email já existente"))
         }
         catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Exception("erro ao cadastrar,dados inválidos",e))
         }
    }


    override suspend fun login(email:String , password:String): Result<CustomerView> {
          try {
               val resultCustomerLoged  =custumerRepository.loginCustomer(email, password)
               val costomerResul = resultCustomerLoged.getOrThrow()
                return  Result.success(costomerResul.toView())
          }catch (firebaseAuthInvalidUserException: FirebaseAuthInvalidUserException){
              return Result.failure(FirebaseAuthInvalidUserException(
                  firebaseAuthInvalidUserException.errorCode,"Usuario inválido,verifique as credenciais"))
          }
          catch (execption : Exception){
              throw execption
          }
    }
    override suspend fun findCustumerById(): Result<Customer> {
        try {
            val customerCurrenty  = custumerRepository.currentUser()
            val customer = customerCurrenty.getOrThrow()
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
    override suspend fun custmerAuth(): Result<CustomerView?> {
        try {
            val customerResult = custumerRepository.currentUser()
            val customer = customerResult.getOrNull()
            val customerView = customer?.toView()
            return Result.success(customerView)
        }catch (ex:Exception){
            throw ex
        }
    }
     suspend fun findCustumerByEmail(email: String): Result<Customer> {
         try {
             if (email.isNotBlank() && email.contains("@") && email.endsWith(".com")){
                    val customer = custumerRepository.findCustumerByEmail(email)
                    return Result.success(customer)
             }
             return Result.failure(Exception("no customers found with this email"))
         }catch (customerException:Exception){
             throw customerException
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
            val customer = findCustumerById().getOrThrow()
            val result = custumerRepository.updateCustumer(idCustomer,customer.toDTO())
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro atualizar Customer",e))
        }
    }

    suspend fun logout():Boolean{
        return  custumerRepository.logout()
    }

}