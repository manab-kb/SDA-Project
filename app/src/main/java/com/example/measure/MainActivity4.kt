package com.example.measure

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity4 : AppCompatActivity(), SensorEventListener
{
    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null
    private lateinit var text: TextView
    private lateinit var text2: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        text = findViewById(R.id.brightness)
        text2 = findViewById(R.id.light_details)
        setUpSensors()

        val button = findViewById<Button>(R.id.speedometer_button)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.compass_button)
        button2.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity3::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.measure_button)
        button3.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button7 = findViewById<Button>(R.id.timer_button)
        button7.setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity5::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }
    }

    private fun setUpSensors()
    {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT)
        {
            val light1 = event.values[0]

            text.text = "$light1"
            text2.text = "${brightness(light1)}"
        }
    }

    private fun brightness(brightness: Float): String
    {
        return when (brightness.toInt())
        {
            0 -> "Pitch Black Light"
            in 1..10 -> "Dark/Very Low Light"
            in 11..50 -> "Low Light"
            in 51..5000 -> "Ambient Light"
            in 5001..25000 -> "Very Bright Light"
            else -> "WARNING !! Blinding Lights, Stay Away !!"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {
        return
    }

    override fun onResume()
    {
        super.onResume()
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_FASTEST)
    }


    override fun onPause()
    {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
