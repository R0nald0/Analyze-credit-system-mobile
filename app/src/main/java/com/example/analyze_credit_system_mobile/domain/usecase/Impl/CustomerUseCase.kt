package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.data.dto.CustomerCreateDto
import com.example.analyze_credit_system_mobile.domain.model.Customer


import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.net.SocketTimeoutException
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
             return Result.failure(Throwable("nenhum customer foi salvo verifique os campos"))
         }catch (fbExecption : FirebaseAuthInvalidCredentialsException){
             return Result.failure(FirebaseAuthInvalidCredentialsException(fbExecption.errorCode,"erro ao salvar o customer,email deve conter @ e .com"))
         }
         catch (emailFirebaseExeption: FirebaseAuthUserCollisionException){
             return Result.failure(FirebaseAuthUserCollisionException(emailFirebaseExeption.errorCode,"email já existente"))
         }
         catch ( nullPointerException :NullPointerException){
             nullPointerException.printStackTrace()
             return Result.failure(NullPointerException("erro ao retornar dados do customer "))
         }
         catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Exception("erro ao cadastrar,dados inválidos",e))
         }
    }
    override suspend fun login(email:String , password:String): Result<CustomerView> {
          try {
               val resultCustomerLoged  =custumerRepository.loginCustomer(email, password)
               if(resultCustomerLoged.isSuccess){
                   val costomerResul = resultCustomerLoged.getOrThrow()
                   return  Result.success(CustomerView(costomerResul))
               }
              return  Result.failure(resultCustomerLoged.exceptionOrNull()!!)

          }catch (firebaseAuthInvalidUserException: FirebaseAuthInvalidUserException){
              return Result.failure(FirebaseAuthInvalidUserException(
                  firebaseAuthInvalidUserException.errorCode,"Usuario inválido,verifique as credenciais"))

          }catch ( firebaseAuthInvalidCredentialsException :FirebaseAuthInvalidCredentialsException){
              firebaseAuthInvalidCredentialsException.printStackTrace()
               return Result.failure(FirebaseAuthInvalidCredentialsException(
                   firebaseAuthInvalidCredentialsException.errorCode,
                   "senha invaláda"
                   ))
        } catch ( nullPointerException :NullPointerException){
              nullPointerException.printStackTrace()
              return Result.failure(NullPointerException("erro ao retornar dados do customer"))
          }catch (firebaseException: FirebaseException){
                return Result.failure(FirebaseException("erro inteno occorreu"))
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
        catch ( socketTimeoutException : SocketTimeoutException){
            socketTimeoutException.printStackTrace()
            return Result.failure(SocketTimeoutException("falha ao conectar no banco"))
        }
        catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro ao buscar custumer",e ))
        }
    }
    override suspend fun custmerAuth(): Result<CustomerView> {
        try {
            val customerResult = custumerRepository.currentUser()
            val customer = customerResult.getOrThrow()
            val customerView = customer?.let { CustomerView(it) }
            if (customerView != null){
                return Result.success(customerView)
            }
            return Result.failure(Throwable("customer null"))
        }catch ( nullPointerException :NullPointerException){
            nullPointerException.printStackTrace()
            return Result.failure(NullPointerException("erro ao retornar dados do customer"))
        }catch ( socketTimeoutException : SocketTimeoutException){
            socketTimeoutException.printStackTrace()
            return Result.failure(SocketTimeoutException("Não conseguimos conectar no servidor"));
        }
        catch (ex:Exception){
            ex.printStackTrace()
            return Result.failure(Exception("nenhum usuario logado"))
        }
    }
    override suspend fun findCustumerByEmail(email: String): Result<Customer> {
         try {
             if (email.isNotBlank() && email.contains("@") && email.endsWith(".com")){
                    val customer = custumerRepository.findCustumerByEmail(email)
                    return Result.success(customer)
             }
             return Result.failure(Exception("no customers found with this email"))
         }catch (customerException:Exception){
             throw customerException
         }
         catch (ex:Exception){
             return Result.failure(SocketTimeoutException("erro generico"))
         }
    }

    override suspend fun findCustumerByAccountNumber(accountNumber: Long): Result<Customer>  {
            try {
                val byAccountNumber =
                    custumerRepository.findCustumerByAccountNumber(accountNumber)
                    return  byAccountNumber
            }catch (nullPointerExeption :NullPointerException){
                return Result.failure(NullPointerException("chave do destinátario inválida"))
            }
            catch (execption:Exception){
                  execption.printStackTrace()
                 return Result.failure(Exception("${execption.message}"))
            }
    }

    override suspend fun deleteCustumer(customer: Customer): Result<Boolean> {
        try{
            val result = custumerRepository.deleteCustumer(customer)
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("error when delete customer",e))
        }
    }

    override suspend fun updateCustumer(idCustomer: Long): Result<Boolean> {
        try{
            val customer = findCustumerById().getOrThrow()
            val result = custumerRepository.updateCustumer(idCustomer, CustomerCreateDto(customer))
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro atualizar Customer",e))
        }
    }

    override suspend fun logout():Boolean{
        return  custumerRepository.logout()
    }

}