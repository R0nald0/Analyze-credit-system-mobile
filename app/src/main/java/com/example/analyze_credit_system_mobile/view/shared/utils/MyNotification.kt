package com.example.analyze_credit_system_mobile.view.shared.utils


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.view.activity.MainActivity

class MyNotification(val context :Context) {

    fun createNotification(title :String,icon :Int,contentText : String,chaneId:String ,pendingIntent: PendingIntent ):NotificationCompat.Builder{
      /*  val inttent = Intent(context,MainActivity::class.java).apply {
              flags =Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val peddingIntent = PendingIntent.getActivity(context,0,inttent,PendingIntent.FLAG_UPDATE_CURRENT)
*/
      return  NotificationCompat.Builder(context,Consts.CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
          .setAutoCancel(true)
    }
}