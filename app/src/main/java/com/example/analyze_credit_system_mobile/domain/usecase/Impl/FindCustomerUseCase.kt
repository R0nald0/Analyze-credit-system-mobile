package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.IFindCustomerUseCase
import java.net.SocketTimeoutException
import javax.inject.Inject

class FindCustomerUseCase @Inject constructor(
    private val custumerRepository :ICustomerRepository
) :IFindCustomerUseCase {
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
        catch (ex:Exception) {
            return Result.failure(SocketTimeoutException("Nâo conseguimos encotrar o usuario $email"))
          }
        }

    override suspend fun findCustumerByAccountNumber(accountNumber: Long): Result<Customer> {
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
}