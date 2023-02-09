package com.example.firebasenotification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder:Notification.Builder
    private val channelID="apps.notification"
    private val channelDescription="Test Notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val pendingIntent: PendingIntent =
                Intent(this, MyFirebaseMessagingServices::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(
                        this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                }

//            val notification: Notification = Notification.Builder(this)
//                .setContentTitle(getText(R.string.notification_title))
//                .setContentText(getText(R.string.notification_message))
//                .setSmallIcon(R.drawable.icon)
//                .setContentIntent(pendingIntent)
//                .setTicker(getText(R.string.ticker_text))
//                .build()

        }

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {

            }
        }

//        fun askNotificationPermission() {
//            // This is only necessary for API level >= 33 (TIRAMISU)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                if (ContextCompat.checkSelfPermission(
//                        this, android.Manifest.permission.POST_NOTIFICATIONS
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
////                    Firebase.messaging.isAutoInitEnabled : true
////                    setAnalyticsCollectionEnabled(true);
//                    // FCM SDK (and your app) can post notifications.
//                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                } else {
//                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//
//        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = resources.getString(R.string.msg_token_fmt, token)
            Log.d(TAG, token)
//            Log.d(TAG, msg)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

    }


}