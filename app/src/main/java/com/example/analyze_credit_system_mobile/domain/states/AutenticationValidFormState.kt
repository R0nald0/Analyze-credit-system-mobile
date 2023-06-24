package com.example.analyze_credit_system_mobile.domain.states
sealed class AutenticationValidFormState(){
    class  InvalidForm(val listInvalidField :MutableList<Pair<String,Int>>) : AutenticationValidFormState()
}