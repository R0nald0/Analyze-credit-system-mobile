package com.example.analyze_credit_system_mobile.shared.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.ProgressButtonLayoutBinding

class ProgresseButton  @JvmOverloads constructor(
     context: Context,
     attrs: AttributeSet?,
     defStyleAttr:Int =0
): ConstraintLayout(context,attrs,defStyleAttr) {

      private val binding  = ProgressButtonLayoutBinding.inflate(LayoutInflater.from(context),this,true)
      private var state :ProgressButtonState = ProgressButtonState.Normal
          set(value) {
             field = value
              refreshState()
          }

      // declarar atributo que seram passados no xml
        private var title :String? = null
        private var loadingTitle :String? = null

   init {
      setAttibuteLayout(attrs)
      refreshState()
   }

    private fun setAttibuteLayout(attributes: AttributeSet?){

        attributes.let {
              val at = context.theme.obtainStyledAttributes(
                  it, R.styleable.ProgresseButton,0,0
              )

              //configura o backgraound
              setBackgroundResource(R.drawable.progress_button_backgraound)

              //recuparar dados confiturados no xml
               val titleResourse = at.getResourceId(R.styleable.ProgresseButton_title,0)
              if (titleResourse!=  0){
                  title =  context.getString(titleResourse)
              }else{
                  title =  "sem titulo"
              }

              val titleLoadingProgressBarResourse = at.getString(R.styleable.ProgresseButton_title_loading_state)
              if (titleLoadingProgressBarResourse != null){
                  loadingTitle = titleLoadingProgressBarResourse
              }
              //tornar o componente reutilisizável
              at.recycle()
          }
    }

    private fun  refreshState(){
        isEnabled = state.isEnable
        isClickable = state.isEnable

      //atualiza drawable xml do botão

        binding.txtTitle.apply {
              isEnabled =state.isEnable
              isClickable = state.isEnable
        }
        binding.idProgress.visibility =state.progressVisibility

        when(state){
             ProgressButtonState.Normal -> {
                 binding.txtTitle.text = title
                 refreshDrawableState()
             }
             ProgressButtonState.Loading -> {
                 binding.txtTitle.text = loadingTitle
                 refreshDrawableState()
             }
        }

    }
    fun setNormal(){
        state =ProgressButtonState.Normal
    }
    fun setLoading(){
       state = ProgressButtonState.Loading
    }

    sealed class ProgressButtonState (val isEnable :Boolean ,val progressVisibility:Int){
         object Normal : ProgressButtonState(true, View.GONE)
         object Loading : ProgressButtonState(false, View.VISIBLE)
    }
}

