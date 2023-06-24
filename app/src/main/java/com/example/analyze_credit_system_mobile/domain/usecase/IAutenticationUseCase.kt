package com.example.analyze_credit_system_mobile.domain.usecase

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import java.math.BigDecimal

interface IAutenticationUseCase {
    fun checkInvalidFormList():AutenticationValidFormState.InvalidForm

     fun validForm(nameCustomer:String,
                  lastName:String,
                  cpf:String,
                  income: BigDecimal,
                  email:String,
                  zipCode:String,
                  street:String,
                  password:String) : AutenticationValidFormState.InvalidForm

    suspend fun getAddress(zipCode:String):Result<Address>
}