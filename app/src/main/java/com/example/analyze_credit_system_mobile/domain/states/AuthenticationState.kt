package com.example.analyze_credit_system_mobile.domain.states

import com.example.analyze_credit_system_mobile.view.model.CustomerView

sealed class AuthenticationState{
    object Unlogged :AuthenticationState()
    object Loading :AuthenticationState()
    object Loaded :AuthenticationState()
    class  InvalidForm(val listInvalidField :MutableSet<Pair<String,Int>>) : AuthenticationState()
    class  Logged(val customerView : CustomerView) :AuthenticationState()
    class  InvalidAuthentication (val fields : List<Pair<String,Int>>) :AuthenticationState()
    class errorState (val mensageError: String) :AuthenticationState()
}

