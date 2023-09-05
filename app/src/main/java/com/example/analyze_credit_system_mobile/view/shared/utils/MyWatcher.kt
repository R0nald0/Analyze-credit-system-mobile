package com.example.analyze_credit_system_mobile.view.shared.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class MyWatcher(val editText: EditText) :TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {

        try {
            val textString = cs.toString()
            if (textString.isEmpty())  return

            editText.removeTextChangedListener(this)

            val valueClear = textString.replace("[R$.,\u00A0]".toRegex(),"")

            val value =  BigDecimal(valueClear)
                            .setScale(2,RoundingMode.HALF_DOWN)
                            .divide(BigDecimal(100),2,RoundingMode.DOWN)

             val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt","BR")) as DecimalFormat
             val formatted = decimalFormat.format(value)
             val textFinal  = formatted.replace("R$\u00A0".toRegex(),"")

            editText.setText(textFinal)
            editText.setSelection(textFinal.length)
            editText.addTextChangedListener(this)

        }catch (exception : Exception){
            Log.i("INFO_", "${exception.message} ")
        }
    }

    override fun afterTextChanged(edited: Editable?) {}
    fun unmsk(): String {
        val tex = this.editText.text.toString()

        return tex.replace("[R$.\u00A0]".toRegex(), "").replace(",", ".")
    }

}