package com.example.analyze_credit_system_mobile.view.shared.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.EditTextCustomLayoutBinding

class EditTextCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr:Int =0
): ConstraintLayout(context, attrs, defStyleAttr) {
   private val binding =EditTextCustomLayoutBinding.inflate(LayoutInflater.from(context),this,true)

   private var hint :String? = null
    private var state : EditTextCustomState = EditTextCustomState.Activated
        set(value) {
            field =value
            refreshState()
        }

    init {
        setAtributeLayout(attrs)
        refreshState()
    }

    private fun refreshState() {
          isActivated = state.isActivated
          isClickable = state.isActivated


        refreshDrawableState()

        binding.progressBarCustom.visibility =state.isProgressVisible

        when(state){
            EditTextCustomState.Activated ->{
                binding.textInputLayout.setHint(hint)
                Log.d("INFO_", "refreshState: Activated")
            }
            EditTextCustomState.Deactivated ->{
                Log.d("INFO_", "refreshState: Deactivated")
            }
        }
    }
    fun onTextChange(callback:(Int)->Unit){
       binding.edtTxtCustom.addTextChangedListener {
            val lenght = binding.edtTxtCustom.text.toString().length
           Log.d("INFO_", "onTextChange: $lenght")
           callback(lenght)
        }
  }
    fun setActivated(){
        state = EditTextCustomState.Activated
    }
    fun setDeactivated(){
        state = EditTextCustomState.Deactivated
    }


    private fun setAtributeLayout(attributes: AttributeSet?){
           if (attributes !=null){
             val atr = context.theme.obtainStyledAttributes(attributes, R.styleable.EditTextCustom,0,0)
               setBackgroundResource(R.drawable.edit_txt_selector_bg)

               val hintWidget = atr.getString(R.styleable.EditTextCustom_hint)
               val typeInputWidget = atr.getInteger(R.styleable.EditTextCustom_inputType,0)
               val maskedField = atr.getString(R.styleable.EditTextCustom_masked)

                //TODO rever cor de fundo quando muda estado

               if (hintWidget != null){
                   hint = hintWidget
               }
               if (typeInputWidget !=null ){
                   binding.edtTxtCustom.inputType = typeInputWidget
               }

               if (maskedField != null){
                   binding.edtTxtCustom.setMask(maskedField)
               }
               atr.recycle()
           }
    }



    sealed class EditTextCustomState(val isActivated :Boolean , val isProgressVisible:Int){
        object Activated : EditTextCustomState(true, View.GONE)
        object Deactivated : EditTextCustomState(false, View.VISIBLE)
    }
}