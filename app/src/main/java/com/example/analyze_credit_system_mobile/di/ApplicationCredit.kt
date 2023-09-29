package com.example.analyze_credit_system_mobile.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationCredit : Application(){
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        val importance =  NotificationManager.IMPORTANCE_HIGH
        val channelName= "credito"
        val descricao = "notifica alteracao na mudamçao do credito"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val canal =  NotificationChannel(Consts.CHANNEL_ID,channelName,importance)
             canal.enableVibration(true)
             canal.lightColor = Color.GREEN
             canal.canShowBadge()
             canal.description = descricao

              val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
              notificationManager.createNotificationChannel(canal)
        }
    }


}