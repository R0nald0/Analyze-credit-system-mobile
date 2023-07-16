package com.example.analyze_credit_system_mobile.domain.states

import com.example.analyze_credit_system_mobile.view.model.CreditCreateView
import com.example.analyze_credit_system_mobile.view.model.CreditExhibitionView

sealed class StateCredit {
    object Loading :StateCredit()
    class Sucecss (val creditCreateView: CreditCreateView ):StateCredit()
    object Loaded :StateCredit()
    class Error(val menssage :String):StateCredit()
    class InvalidCreditfields(val fildsCredit :MutableSet<Pair<String,Int>>) :StateCredit()
}