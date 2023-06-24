package com.example.analyze_credit_system_mobile.shared.extensions

import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearFieldsError(){
        this.error= ""
        this.isErrorEnabled = false
}
