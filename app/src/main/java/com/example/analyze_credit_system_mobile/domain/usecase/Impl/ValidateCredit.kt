package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import java.math.BigDecimal

class ValidateCredit {
    val lisInvalidFieldsCredit = mutableSetOf<Pair<String,Int>>()

    fun validCredit( creditValue :BigDecimal,numberOfInstallments:Int,dayFistInstallment:Long,idCustomer:Long
             ) : StateCredit.InvalidCreditfields{

        if (creditValue <= BigDecimal.valueOf(0)) lisInvalidFieldsCredit.add(INVALID_CREDIT_VALUE)
        if ( numberOfInstallments != null && numberOfInstallments <= 0) lisInvalidFieldsCredit.add(
            INVALID_NUMBER_OF_INSTALLMENT)
        if ( dayFistInstallment == null) lisInvalidFieldsCredit.add(INVALID_DATE_FIRST_INSTALLMENT)
        if ( idCustomer == null) lisInvalidFieldsCredit.add(INVALID_CUSTOMER)

        if (lisInvalidFieldsCredit.isNotEmpty()){
             return StateCredit.InvalidCreditfields(lisInvalidFieldsCredit)
        }
        return  StateCredit.InvalidCreditfields(mutableSetOf())
    }

    companion object{
        val INVALID_CREDIT_VALUE ="CREDIT_VALUE" to R.string.credito_invalido
        val INVALID_NUMBER_OF_INSTALLMENT ="NUMBER_OF_INSTALLMENT" to R.string.numero_de_parcela_est_invalido
        val INVALID_DATE_FIRST_INSTALLMENT="INVALID_DATE" to R.string.data_invalido
        val INVALID_CUSTOMER ="CUSTOMER_FIELD" to R.string.valid_credit_customer_erro
    }
}