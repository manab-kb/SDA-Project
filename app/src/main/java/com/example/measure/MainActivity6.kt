package com.example.measure

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import java.lang.Math.toDegrees
import kotlin.math.acos
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.sqrt

class MainActivity6 : AppCompatActivity(), SensorEventListener
{
    private lateinit var sensorManager: SensorManager
    private var rotation: Sensor? = null
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        text = findViewById(R.id.inclination)

        setUpSensors()

        val button = findViewById<Button>(R.id.speedometer_button)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.compass_button)
        button2.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.measure_button)
        button3.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val button7 = findViewById<Button>(R.id.timer_button)
        button7.setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }

    }

    private fun setUpSensors()
    {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        if (event?.sensor?.type == Sensor.TYPE_GAME_ROTATION_VECTOR)
        {
            val rotation = event.values

            val normOfg = sqrt((event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2]).toDouble());
            event.values[0] = (event.values[0] / normOfg).toFloat()
            event.values[1] = (event.values[1] / normOfg).toFloat()
            event.values[2] = (event.values[2] / normOfg).toFloat()

            val inclination = toDegrees(acos(event.values[2].toDouble()));

            text.text = "${inclination.toInt()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {
        return
    }

    override fun onResume()
    {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause()
    {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    fun openActivity()
    {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}