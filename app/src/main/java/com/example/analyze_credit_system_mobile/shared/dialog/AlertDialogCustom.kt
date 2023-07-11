package com.example.analyze_credit_system_mobile.shared.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View


class AlertDialogCustom(private val  acttivity : Activity,val layout : View?) {
    var dialog : AlertDialog? = null

    fun exibirDiaolog(){
        val alertDialog = AlertDialog.Builder(acttivity)
            .setView(layout)
            .setCancelable(false)
        if (dialog != null){
            fecharDialog()
        }else{
            dialog = alertDialog.create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.show()
        }

    }
    fun fecharDialog(){
        dialog?.dismiss()
    }
    }