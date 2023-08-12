package com.example.analyze_credit_system_mobile.domain.usecase.Impl



import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.repository.AddressRespository
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import java.math.BigDecimal
import java.net.SocketTimeoutException
import javax.inject.Inject

class AutenticationUseCaseImpl @Inject constructor(
    private val addressRespository: AddressRespository
):IAutenticationUseCase {
    private  val invalidField = mutableSetOf<Pair<String,Int>>()

    override fun checkInvalidFormList(): AuthenticationState.InvalidForm{
        if (invalidField.isNotEmpty()){
            return AuthenticationState.InvalidForm(invalidField)
        }
        return AuthenticationState.InvalidForm(mutableSetOf())
    }

    override suspend fun validForm(
        nameCustomer: String,
        lastName: String,
        cpf: String,
        income: BigDecimal,
        email: String,
        zipCode: String,
        password: String
    ) {
         invalidField.clear()
        if(nameCustomer.isBlank()) invalidField.add(FIRSTNAME_CUSTOMER)
        if(lastName.isBlank()) invalidField.add(LASTNAME_CUSTOMER)
        if(cpf.isBlank() || cpf.length != 11) invalidField.add(CPF_CUSTOMER)
        if (income <= 0.0.toBigDecimal()) invalidField.add(INCOME_CUSTOMER)
        if(email.isBlank()||
            !email.contains("@") ||
            !email.endsWith(".com") ) invalidField.add(EMAIL_CUSTOMER)
        if(zipCode.isBlank() || zipCode.length != 8) invalidField.add(ZIPCODE_CUSTOMER)
        if(password.isBlank() || password.length < 6) invalidField.add(PASSWORD_CUSTOMER)
    }

    override suspend fun getAddress(zipCode:String):Result<Address>{
        try {
           val result = addressRespository.getAddress(zipCode)
            return result
        }
        catch ( sockteTimeOut  : SocketTimeoutException){
             return  Result.failure(SocketTimeoutException("Falha no tempo de espera da conexao,tente novamente"))
        }
        catch (nullpointer: NullPointerException) {
             return Result.failure(NullPointerException("Cep inexistente"))
        } catch (execptionAddress: Exception) {
            return Result.failure(Exception("erro genérico"))
        }
    }


    companion object {
        val FIRSTNAME_CUSTOMER = "NAME_CUSTOMER_KEY" to R.string.erro_first_name_cadastro
        val LASTNAME_CUSTOMER = "LASTNAME_CUSTOMER" to R.string.erro_last_name
        val CPF_CUSTOMER = "CPF_CUSTOMER_KEY" to  R.string.erro_cpf_field
        val INCOME_CUSTOMER = "INCOME_CUSTOMER_KEY" to  R.string.erro_income_field
        val EMAIL_CUSTOMER = "EMAIL_CUSTOMER_KEY" to  R.string.erro_email_field
        val ZIPCODE_CUSTOMER = "ZIPCODE_CUSTOMER_KEY" to  R.string.erro_cep_field
        val ZIPCODE_CUSTOMER_INEXISTENTE = "ZIPCODE_CUSTOMER_KEY" to  R.string.cep_inexistente_erro
        val PASSWORD_CUSTOMER = "PASSWORD_CUSTOMER_KEY" to  R.string.erro_password_field
    }
}