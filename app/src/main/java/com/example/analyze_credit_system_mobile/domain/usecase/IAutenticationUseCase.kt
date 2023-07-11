package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import java.math.BigDecimal

interface IAutenticationUseCase {
    fun checkInvalidFormList(): AuthenticationState.InvalidForm

    suspend  fun validForm(nameCustomer:String,
                  lastName:String,
                  cpf:String,
                  income: BigDecimal,
                  email:String,
                  zipCode:String,
                  password:String)

    suspend fun getAddress(zipCode:String):Result<Address>
}