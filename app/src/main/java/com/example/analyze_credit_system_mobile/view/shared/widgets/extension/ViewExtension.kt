package com.example.analyze_credit_system_mobile.view.shared.widgets.extension

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder

fun Context.createNavigationPending(destination:Int,navGraph :Int ,args : Bundle?):PendingIntent{
    return NavDeepLinkBuilder(this)
        .setGraph(navGraph)
        .setDestination(destination)
        .setArguments(args)
        .createPendingIntent()
}

fun Context.createNotification(
    title: String,
    icon: Int,
    contentText: String,
    chaneId: String,
    pendingIntent: PendingIntent
):NotificationCompat.Builder{
    return  NotificationCompat.Builder(this, chaneId)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentIntent(pendingIntent)
        .setContentText(contentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
}


fun View.hideKeyboard(){
     val inputMehod = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
     if (inputMehod.isAcceptingText){
         inputMehod.hideSoftInputFromWindow(windowToken,0)
     }

}