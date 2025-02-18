package com.example.composetutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import java.io.File
import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var luxSensor: Sensor? = null

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED)

        {
            val requestPermissionLauncher =  registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun notifyTest() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, "channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Light detected")
            .setContentText("Light sensor has detected over 30 000 lux, woah!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }
            notify(1, builder.build())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()

        setContent {
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            luxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

            val notifyChannel =
                NotificationChannel("channel", "default", NotificationManager.IMPORTANCE_DEFAULT).apply{}
            val notificationManager: NotificationManager =
                LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notifyChannel)

            ComposeTutorialTheme {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    NavHost()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        luxSensor?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lux = event.values?.get(0)

            if (lux != null) {
                if (lux > 30000) {
                    notifyTest()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}

@Serializable
object Conversation
@Serializable
object Profile

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Conversation
    ) {

        composable<Conversation> {
            ConversationScreen(
                onNavigateToProfile = {
                    navController.navigate(route = Profile) {
                        popUpTo(Profile) { inclusive = true }
                    }
                }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onNavigateToConversation = {
                    navController.navigate(route = Conversation) {
                        popUpTo(Conversation) { inclusive = true }
                    }
                }
            )
        }
    }
}
