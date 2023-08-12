package com.example.analyze_credit_system_mobile.domain.enums

import com.example.analyze_credit_system_mobile.R

enum class Status(val state: String,val color : Int) {
   IN_PROGRESS("Em Análise", color = R.color.blue),
   APPROVED("Aprovado",R.color.myGreen),
   REJECT("Rejeitado",R.color.red)
}
