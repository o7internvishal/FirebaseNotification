package com.example.firebasenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
class MyFirebaseMessagingServices : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token:$token")
        sendRegistrationToServer(token)
    }
    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")
        Log.d(TAG, "From: ${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }
        val intent = Intent(this, MainActivity::class.java);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        val channelId = "Default";
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val manager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(channelId, "$channelId channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

        }
            manager.notify(0, builder.build());

//            if (true) {
//                scheduleJob()
//            } else {
//                handleNow()
//            }
        }
//        remoteMessage.notification?.let {
//            Log.d(TAG, "Message Notification Body: ${it.body}")
//        }
//    }
//    private fun scheduleJob() {
//    }
//    private fun handleNow() {
//    }
}


