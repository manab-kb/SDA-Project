package com.example.measure

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate


class MainActivity2 : AppCompatActivity(), SensorEventListener
{
    private lateinit var sensorManager: SensorManager
    private var dimensionlength: Sensor? = null
    private lateinit var text: TextView
    public var time_1: Long = 0
    public var time_2: Long = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        text = findViewById(R.id.length)

        setUpSensors()

        val button = findViewById<Button>(R.id.speedometer_button)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
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

        val button4 = findViewById<Button>(R.id.Start_Button)
        button4.setOnClickListener{
            time_1 = System.currentTimeMillis()
        }

        val button5 = findViewById<Button>(R.id.Pause_Button)
        button5.setOnClickListener{
            time_2 = System.currentTimeMillis()
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
        dimensionlength = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        if (event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            val lengths = event.values
            val velocityx = event.values[0]
            val distancex = (velocityx * velocityx) / (2 * event.values[0]).toInt()

            text.text = "$distancex"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {
        return
    }

    override fun onResume()
    {
        super.onResume()
        sensorManager.registerListener(this, dimensionlength, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause()
    {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
