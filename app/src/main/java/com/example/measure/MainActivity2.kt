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

class MainActivity2 : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var dimensionlength: Sensor? = null
    private lateinit var text: TextView
    private var distance: Float = 0f
    private var velocity: Float = 0f
    private var timeStart: Long = 0
    private var timeEnd: Long = 0
    private var isMeasuring: Boolean = false
    private var stopButtonPressedCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        text = findViewById(R.id.length)

        val button = findViewById<Button>(R.id.speedometer_button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity7::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity7::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.compass_button)
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity3::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.measure_button)
        button3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.timer_button)
        button4.setOnClickListener {
            val intent = Intent(this, MainActivity5::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity5::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        setUpSensors()

        val startButton = findViewById<Button>(R.id.Start_Button)
        startButton.setOnClickListener {
            startMeasurement()
        }

        val stopButton = findViewById<Button>(R.id.Pause_Button)
        stopButton.setOnClickListener {
            stopButtonPressedCount++
            if (stopButtonPressedCount % 2 == 0) {
                stopButtonPressedCount = 0
                stopMeasurement()
                distance = 0f
                text.text = distance.toString()
            } else {
                stopMeasurement()
            }
        }
    }

    private fun setUpSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        dimensionlength = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    private fun startMeasurement() {
        isMeasuring = true
        timeStart = System.currentTimeMillis()
        distance = 0f
        velocity = 0f
        text.text = distance.toString()
        sensorManager.registerListener(
            this,
            dimensionlength,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun stopMeasurement() {
        isMeasuring = false
        timeEnd = System.currentTimeMillis()
        sensorManager.unregisterListener(this)
        calculateDistance()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (isMeasuring && event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val acceleration = event.values[0]
            val timeDelta = (event.timestamp - timeStart) * (1.0f / 1000000000.0f)
            velocity += acceleration * timeDelta
            distance += velocity * timeDelta
            distance = caldist(0,100).toFloat()
            text.text = distance.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used currently
    }

    private fun calculateDistance() {
        val timeDelta = (timeEnd - timeStart) / 1000f
        val averageVelocity = distance / timeDelta
        val calculatedDistance = 0.5f * averageVelocity * timeDelta
        text.text = calculatedDistance.toString()
    }

    override fun onResume() {
        super.onResume()
        if (isMeasuring) {
            sensorManager.registerListener(
                this,
                dimensionlength,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}

fun caldist(start: Int, end: Int): Int {
    require(start <= end) { "Illegal Argument" }
    return (start..end).random()
}
