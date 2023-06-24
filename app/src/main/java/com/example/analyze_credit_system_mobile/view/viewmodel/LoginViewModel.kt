package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor() : ViewModel() {
    sealed class AuthenticationState{
        object Unlogged :AuthenticationState()
        object Logged :AuthenticationState()
        class InvalidAuthentication (val fields : List<Pair<String,Int>>) :AuthenticationState()
    }

      private val _userloggedData =MutableLiveData<CustomerView?>()
       val userloggedData : LiveData<CustomerView?>
           get() = _userloggedData

      private val _authenticationStateEvent = MutableLiveData<AuthenticationState>()
      val authenticationState : LiveData<AuthenticationState>
          get() = _authenticationStateEvent
    init {
        _authenticationStateEvent.value =AuthenticationState.Unlogged
    }

    fun authentication(email: String,password: String){
         if (validForm(email,password)){
             _authenticationStateEvent.value = AuthenticationState.Logged
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


    fun delsogar(){
        _authenticationStateEvent.value =AuthenticationState.Unlogged
    }


    companion object{
        val INPUT_EMAIL ="INPUT_EMAIL" to R.string.erro_massage_invalid_email
        val INPUT_PASSWORD ="INPUT_PASSWORD" to R.string.erro_message_invalid_password
    }
}