package com.example.analyze_credit_system_mobile.view.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.ValidateCredit
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.model.toCredit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class CreateCreditViewModel @Inject constructor(
    private val creditUseCase: ICreditUseCase,
    private val validateCredit: ValidateCredit,
    private val customerUseCase: CustomerUseCase
) : ViewModel() {
    private val _resultCreatCredit = MutableLiveData<Result<String>>()
     val resultCreatCredit : LiveData<Result<String>>
         get() = _resultCreatCredit

    private val _stateCreditLiveData = MutableLiveData<StateCredit>()
    val stateCreditLiveData : LiveData<StateCredit>
        get() =  _stateCreditLiveData
    fun createCredit(creditView : CreditView){
         val credit = creditView.toCredit()
        viewModelScope.launch {
            val result = creditUseCase.createCredit(credit)
               if (result.isSuccess){
                   _stateCreditLiveData.value = StateCredit.Loaded
                   //TODo recuperrar resultado
               }else{
                   result.getOrElse {
                       _stateCreditLiveData.value = it.message?.let { it1 -> StateCredit.error(it1) }
                   }
               }
        }
    }
     fun validCredit(creditView: CreditView){
         viewModelScope.launch {
             _stateCreditLiveData.value =StateCredit.Loading
             val custumerById = customerUseCase.findCustumerById()

              if (custumerById.isSuccess){
                  val validStateCredit  = validateCredit.validCredit(creditView.creditValue, creditView.numberOfInstallments, creditView.dayFistInstallment,
                      custumerById.getOrThrow().id!!)
                  if (validStateCredit.fildsCredit.isEmpty()){
                      createCredit(creditView)
                  }else{
                      _stateCreditLiveData.value = validStateCredit
                  }
              }else{
                  _stateCreditLiveData.value =StateCredit.error("customer erro ${custumerById.exceptionOrNull()?.message} ")
              }

             _stateCreditLiveData.value =StateCredit.Loaded
         }
    }
    fun getInstallments(numberOfInstallment: String,creditValue:Double):String{
            val installment = numberOfInstallment.toInt()
            if (installment > 0){
                val totalValueInstallment = creditUseCase.calculateInstallment(installment,creditValue)
                val resul = String.format("x R$ %.4s por mês",totalValueInstallment)
                return resul
            }
        return "parcela não pode ser menor 1"
    }
    fun getLimitsDate( field :Int , amountTime: Int) :Long{
         return  creditUseCase.getLimitsDate(field,amountTime)
    }

}