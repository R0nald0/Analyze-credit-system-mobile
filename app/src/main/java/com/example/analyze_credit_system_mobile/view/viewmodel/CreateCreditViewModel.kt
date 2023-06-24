package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.view.model.CreditView
import com.example.analyze_credit_system_mobile.view.model.toCredit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateCreditViewModel @Inject constructor(
    private val creditUseCase: ICreditUseCase
) : ViewModel() {
    private val _resultCreatCredit = MutableLiveData<Result<String>>()
     val resultCreatCredit : LiveData<Result<String>>
         get() = _resultCreatCredit

    fun createCredit(creditView : CreditView){
         val credit = creditView.toCredit()
        viewModelScope.launch {
            val result = creditUseCase.createCredit(credit)
            _resultCreatCredit.postValue(result)
        }
    }

}