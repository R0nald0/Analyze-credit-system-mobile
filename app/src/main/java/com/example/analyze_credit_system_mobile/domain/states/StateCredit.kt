package com.example.analyze_credit_system_mobile.domain.states

sealed class StateCredit {
    object Loading :StateCredit()
    object Sucecss :StateCredit()
    object Loaded :StateCredit()
    class  error(val menssage :String):StateCredit()
    class InvalidCreditfields(val fildsCredit :MutableSet<Pair<String,Int>>) :StateCredit()
}