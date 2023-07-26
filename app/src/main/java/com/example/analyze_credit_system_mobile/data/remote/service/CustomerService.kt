package com.example.analyze_credit_system_mobile.data.remote.service
import com.example.analyze_credit_system_mobile.data.dto.CustomerCreateDto
import com.example.analyze_credit_system_mobile.data.dto.response.CustomerViewDTO
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.firabase.MyFirebaseAuthentication
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import retrofit2.Response
import javax.inject.Inject

class CustomerService  @Inject constructor(
      private val myFirebaseAuth : MyFirebaseAuthentication,
      private val customerApi: CustumerApi
) {

    suspend fun registerUser(customerDTO: CustomerCreateDto): Result<CustomerViewDTO> {
        try {
            val authCustomer =  myFirebaseAuth.registerCostumer(customerDTO.email,customerDTO.password)
            val  email = authCustomer.user?.email
                if (email != null) {
                    val customerDTOFromFirebase = CustomerCreateDto(
                       cpf =  customerDTO.cpf,
                       email =  email,
                       fistName =  customerDTO.fistName,
                       income =  customerDTO.income,
                       lastName =  customerDTO.lastName,
                       password =  customerDTO.password,
                       street =   customerDTO.street,
                       zipCode =  customerDTO.zipCode,
                       account = customerDTO.account
                    )
                return  registerCustomerOnApi(customerDTOFromFirebase)
             }
            return Result.failure(Throwable("erro ao criar usuario") )

         } catch (firebaseAuthInvalidCredentialsException : FirebaseAuthInvalidCredentialsException){
               throw firebaseAuthInvalidCredentialsException
         }catch (emailFirebaseExeption: FirebaseAuthUserCollisionException){
            throw emailFirebaseExeption
        }
        catch(ex : Exception){
            ex.printStackTrace()
            throw ex
        }
    }

    suspend fun loginCustomer(email:String , password:String):Result<CustomerViewDTO>{
        try {
           val authCustomer =myFirebaseAuth.loginCustomer(email,password)
            val email  = authCustomer.user?.email
            if (email != null){
            findCustomerByEmail(email)?.let { customerViewDTO->
                   return Result.success(customerViewDTO)
               }
            }
            return Result.failure(Throwable("erro ao buscar customer"))
        }
        catch (firebaseAuthInvalidUser: FirebaseAuthInvalidUserException){
            throw firebaseAuthInvalidUser
        }
        catch ( firebaseAuthInvalidCredentialsException :FirebaseAuthInvalidCredentialsException){
            firebaseAuthInvalidCredentialsException.printStackTrace()
            throw firebaseAuthInvalidCredentialsException
        }
        catch ( firebaseException: FirebaseException){
             throw firebaseException
        }
        catch ( nullPointerException :NullPointerException){
            nullPointerException.printStackTrace()
            throw nullPointerException
        }
        catch (firebaseException: FirebaseException){
            throw firebaseException
        }
        catch (execption:Exception){
            throw execption
        }
    }
    suspend fun getCurrentuser():Result<CustomerViewDTO?>{
        try {
            val email = myFirebaseAuth.getCurrentUser()?.email
            if (email != null) {
                val customerByEmail = customerApi.findCustomerByEmail(email)
                if(customerByEmail.isSuccessful){
                    val customerViewDTO = customerByEmail.body()
                    return Result.success(customerViewDTO)
                }
                return Result.failure(Exception("customer not found ${customerByEmail.code()} erroBody : ${customerByEmail.errorBody()}"))
            }
            return Result.success(null)
        }
        catch ( nullPointerException :NullPointerException){
            nullPointerException.printStackTrace()
            throw nullPointerException
        }
    }
    suspend fun findCustomerByEmail(email: String): CustomerViewDTO?{
           try {
              val response =customerApi.findCustomerByEmail(email)
               val dto = RetrofitApiClient.consultApi(response)
               if (dto.isSuccessful){
                     val customerDto = dto.body()
                   return customerDto
               }
               return null
           }catch (ex:Exception){
               throw ex
           }
    }

    suspend fun registerCustomerOnApi(customerDTO: CustomerCreateDto):Result<CustomerViewDTO>{
        try {
            val customerViewDTOResponse =  callApiMethod(customerDTO)
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
            throw e
        }
    }

    suspend fun callApiMethod(customerDTO: CustomerCreateDto): Response<CustomerViewDTO>{
        //TODO tornar este metodo reutilizavel

        var response : Response<CustomerViewDTO>
        try {
            response = customerApi.createCustomer(customerDTO)
            return response
        }catch (ex:Exception){
            throw Throwable("erro when call api",ex)
        }
    }

    suspend fun logout():Boolean{
        return myFirebaseAuth.logout()
    }
}