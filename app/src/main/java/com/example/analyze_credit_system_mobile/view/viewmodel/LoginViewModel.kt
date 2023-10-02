package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val customerUseCase: ICustomerUseCase,
) : ViewModel() {

    private val _authenticationStateEvent = MutableLiveData<AuthenticationState>()
    val authenticationState : LiveData<AuthenticationState>
       get() = _authenticationStateEvent


    init {
        customerLogged()
    }

    fun customerLogged(){
        _authenticationStateEvent.value =AuthenticationState.Loading
        viewModelScope.launch {
            val resultAuth = customerUseCase.custmerAuth()

             if (resultAuth.isSuccess){
                 _authenticationStateEvent.value =AuthenticationState.Loaded
                   _authenticationStateEvent.value =AuthenticationState.Logged(resultAuth.getOrThrow())
              }else{
                  _authenticationStateEvent.value =AuthenticationState.Loaded
                  _authenticationStateEvent.value =AuthenticationState.Unlogged
                  resultAuth.getOrElse {error->
                      _authenticationStateEvent.postValue(AuthenticationState.errorState("${error.message}"))
                  }
              }

        }
    }
    fun authentication(email: String,password: String){
        _authenticationStateEvent.value =AuthenticationState.Loading
         if (validForm(email,password)){
             viewModelScope.launch {
                val result =  customerUseCase.login(email, password)
                 _authenticationStateEvent.value =AuthenticationState.Loaded
                if (result.isSuccess){
                    _authenticationStateEvent.value =AuthenticationState.Logged(result.getOrThrow())
                }else{

                     result.getOrElse {erroMensage->

                         _authenticationStateEvent.value = AuthenticationState.errorState(erroMensage.message!!)
                     }
                }
             }

         }
    }

    private fun validForm(email:String,password:String) : Boolean{
             val invalidField = arrayListOf<Pair<String,Int>>()
        if (email.isEmpty()) invalidField.add(INPUT_EMAIL)
        if (password.isEmpty()) invalidField.add(INPUT_PASSWORD)
        if (invalidField.isNotEmpty()){
           _authenticationStateEvent.value = AuthenticationState.InvalidAuthentication(invalidField)
         return false
        }
        return true
    }

    fun logout(){
        viewModelScope.launch {
            customerUseCase.logout()
            _authenticationStateEvent.value =AuthenticationState.Unlogged
        }
    }


    companion object{
        val INPUT_EMAIL ="INPUT_EMAIL" to R.string.erro_massage_invalid_email
        val INPUT_PASSWORD ="INPUT_PASSWORD" to R.string.erro_message_invalid_password
    }
}