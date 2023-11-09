package com.example.analyze_credit_system_mobile.data.remote.firabase

import android.util.Log
import com.example.analyze_credit_system_mobile.data.dto.CreditDTO
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MyFireStore @Inject constructor(
    private val myStore : FirebaseFirestore
) {

  suspend  fun saveCredit(credit :CreditDTO){
        myStore.collection("credit")
            .document("${credit.idCustomer}")
            .set(credit).addOnSuccessListener {
                Log.i("INFO_", "saveCredit: ${it} ")
            }.addOnFailureListener {
                Log.i("INFO_", "saveCredit: ${it.message} ")
            }
    }
}