package com.example.analyze_credit_system_mobile.data.remote.service
import com.example.analyze_credit_system_mobile.data.dto.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.dto.CustumerDTO
import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.data.remote.CotacaoApi
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.firabase.MyFirebaseAuthentication
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import retrofit2.Response
import javax.inject.Inject

class CustomerService  @Inject constructor(
      private val myFirebaseAuth : MyFirebaseAuthentication,
      private val customerApi: CustumerApi
) {

    suspend fun registerUser(custumerDTO: CustumerDTO): Result<CustomerViewDTO> {
        try {
            val authCustomer =  myFirebaseAuth.registerCostumer(custumerDTO.email,custumerDTO.passoword)
            val  email = authCustomer.user?.email
                if (email != null) {
                    val custumerDTOFromFirebase = CustumerDTO(
                        custumerDTO.cpf,
                        email,
                        custumerDTO.fistName,-
                        custumerDTO.income,
                        custumerDTO.lastName,
                        custumerDTO.passoword,
                        custumerDTO.street,
                        custumerDTO.zipCode
                    )
                   val resultCustomerView =registerCustomerOnApi(custumerDTOFromFirebase)
                    return resultCustomerView
             }
            return Result.failure(Throwable("erro ao criar usuario") )

         } catch (fbExeception : FirebaseAuthInvalidCredentialsException){
               throw FirebaseAuthInvalidCredentialsException(fbExeception.errorCode,"erro firebase :${fbExeception.message}")
         }
        catch(ex : Exception){
            ex.printStackTrace()
            throw Exception("${ex.message}")
        }
    }

    suspend fun loginCustomer(custumerDTO: CustumerDTO):Result<AuthResult>{
        try {
           val authCustomer =myFirebaseAuth.loginCustomer(custumerDTO.email,custumerDTO.passoword)
            val uuid  = authCustomer.user?.email
            if (uuid != null){
                return  Result.success(authCustomer)
            }else{
                return Result.failure(Throwable("erro ao buscas uuid"))
            }

        }catch (ex :Exception){
            throw Throwable("erro login customer",ex)
        }
    }

    suspend fun registerCustomerOnApi(custumerDTO: CustumerDTO):Result<CustomerViewDTO>{
        try {
            val customerViewDTOResponse =  callApiMethod(custumerDTO)

                if (customerViewDTOResponse.isSuccessful){
                    val custumerVIewDTO = customerViewDTOResponse.body()
                    if (custumerVIewDTO != null) {
                        return Result.success(custumerVIewDTO)
                    }
                    return Result.failure(Throwable("erro customer não foi salvo :is null"))
                }else{
                    return Result.failure(Throwable("erro na api status code ${customerViewDTOResponse.code()}"))
                }
        }catch (e:Exception){
            e.printStackTrace()
             return Result.failure(Throwable("erro ao salvar dados no banco:${e.message}"))
        }
    }

    suspend fun callApiMethod(customerDTO: CustumerDTO): Response<CustomerViewDTO>{
        var response : Response<CustomerViewDTO>
        try {
            response = customerApi.createCustomer(customerDTO)
            return response
        }catch (ex:Exception){
            throw Throwable("erro when call api",ex)
        }
    }
}