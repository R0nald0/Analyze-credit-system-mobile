package com.example.analyze_credit_system_mobile.shared.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.analyze_credit_system_mobile.databinding.AlertCustomLayoutBinding


class AlertDialogCustom(private val  acttivity : Activity, private val layout : Int?) {
   private var dialog : AlertDialog? = null
   private  val alertDialogLayout = layout?.let { acttivity.layoutInflater.inflate(it,null) }
  private  val layoutBinding  by lazy {
      AlertCustomLayoutBinding.inflate(acttivity.layoutInflater)
  }
    fun exibirDiaolog(mensenger:String?){
        val alertDialog = AlertDialog.Builder(acttivity)
            .setView(alertDialogLayout)
            .setMessage(mensenger)
            .setCancelable(false)
        if (dialog != null){
            fecharDialog()
        }else{
            dialog = alertDialog.create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.show()
        }

    }
    fun exibirDiaolog(
        mensenger:String,
        isCancelable:Boolean=false,
        positiveActionMensager: String?,
        negativeActionMensager: String?,
        positiveAction:()->Unit?,
        nagativeAction: () -> Unit?,
    ){
        val alertDialog = AlertDialog.Builder(acttivity)
            .setView(layoutBinding.root)
            .setCancelable(isCancelable)

        layoutBinding.txvTitleMessage.setText(mensenger)
        layoutBinding.btnPositive.setOnClickListener {
            positiveAction()
            fecharDialog()
        }
        layoutBinding.btnPositive.setText(positiveActionMensager)

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