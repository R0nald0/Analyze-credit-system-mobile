package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.ValidateCredit
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import com.example.analyze_credit_system_mobile.view.model.CreditCreateView
import com.example.analyze_credit_system_mobile.view.model.CreditExhibitionView
import com.example.analyze_credit_system_mobile.view.model.toCredit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
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

    private val _listCredit =MutableLiveData<List<CreditExhibitionView>>()
     val listCredit :LiveData<List<CreditExhibitionView>>
         get() = _listCredit

    fun createCredit(creditCreateView : CreditCreateView){
         val credit = creditCreateView.toCredit()
        viewModelScope.launch {
            val result = creditUseCase.createCredit(credit)
               if (result.isSuccess){

                   val credit =  result.getOrThrow()
                   _stateCreditLiveData.value = StateCredit.Loaded
               }else{
                   result.getOrElse {
                       _stateCreditLiveData.value = it.message?.let { it1 -> StateCredit.Error(it1) }
                   }

               }
            _stateCreditLiveData.value = StateCredit.Loaded
        }
    }
    fun validCredit(creditCreateView: CreditCreateView){
         viewModelScope.launch {
             _stateCreditLiveData.value =StateCredit.Loading
              val custumerById = customerUseCase.findCustumerById()

              if (custumerById.isSuccess){
                  val invalidCreditfields  = validateCredit.validCredit(creditCreateView.creditValue, creditCreateView.numberOfInstallments, creditCreateView.dayFistInstallment,
                      custumerById.getOrThrow().id!!
                  )
                  if (invalidCreditfields.fildsCredit.isEmpty()){
                           _stateCreditLiveData.value = StateCredit.Sucecss(creditCreateView)
                  }else{
                      _stateCreditLiveData.value = invalidCreditfields
                  }
              }else{
                  _stateCreditLiveData.value =StateCredit.Error("customer erro ${custumerById.exceptionOrNull()?.message} ")
              }
             _stateCreditLiveData.value =StateCredit.Loaded
         }
    }

    fun validateInstallments(numberOfInstallment: String):String?{
        val validateAmountPerInstalmment =
            validateCredit.validateAmountPerInstalmment(numberOfInstallment.toInt())
        if (validateAmountPerInstalmment != null){
            return validateAmountPerInstalmment
        }
        return null
    }
    fun getInstallments(numberOfInstallment: String,creditValue:BigDecimal):String{
        val installment = numberOfInstallment.toInt()
            val totalValueInstallment = creditUseCase.calculateInstallment(installment,creditValue)
            return totalValueInstallment.formatCurrency()
    }
    fun getLimitsDate( field :Int , amountTime: Int) :Long{
         return  creditUseCase.getLimitsDate(field,amountTime)
    }
    fun getAllOrderCreditFromCustomer(customerId :Long){
         viewModelScope.launch {
             val allCreditResult = creditUseCase.getAllCreditByCustomer(customerId)

             if (allCreditResult.isSuccess){
                 val creditList = allCreditResult.getOrThrow()
                   val listCreditExhibition = creditList.map {credit->
                        CreditExhibitionView(credit)
                    }
                  _listCredit.postValue(listCreditExhibition)
             }
             else{
                allCreditResult.getOrElse {
                     _stateCreditLiveData.postValue(StateCredit.Error("${it.message}"))
                 }
             }

         }
    }

     fun calculateInstallment(numberInstallment: Int,valueCredit:BigDecimal) :BigDecimal{
        return  creditUseCase.calculateInstallment(numberInstallment,valueCredit)
    }

}