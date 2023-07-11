package com.example.analyze_credit_system_mobile.data.remote.firabase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class MyFirebaseAuthentication @Inject constructor(
      private val auth: FirebaseAuth
) {

    suspend fun registerCostumer(email: String,password: String):AuthResult{
        return suspendCoroutine{ continuationAuth->
              auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                  continuationAuth.resumeWith(Result.success(it))
               }.addOnFailureListener {
                  continuationAuth.resumeWith(Result.failure(it))
               }

       }

      /* return try {
            auth.createUserWithEmailAndPassword(email, password).await()
        }catch (fb:FirebaseAuthException){
            throw FirebaseAuthException("erro : ${fb.errorCode}","meesage: ${fb.message}")
        }
         catch(ex:Exception){
            throw Exception("erro when register customer ${ex.message} ${ex.stackTrace} ")
        }*/
    }

    suspend fun loginCustomer(email:String, password:String) : AuthResult{
       return  try {
         auth.signInWithEmailAndPassword(email, password).await()
       }catch (emailFirebaseExeption: FirebaseAuthUserCollisionException){
           throw emailFirebaseExeption
       } catch (firebaseAuthInvalidUserException: FirebaseAuthInvalidUserException){
           throw firebaseAuthInvalidUserException
       }
       catch(e :Exception){
         throw e
     }
  }
    suspend fun getCurrentUser():FirebaseUser?{
         try {
             if (auth.currentUser !=null){
                  return auth.currentUser
             }else{
                 return null
             }
         }catch (e:Exception){
              throw e
         }
    }

   suspend fun logout():Boolean{
       auth.signOut()
       return true
    }

}