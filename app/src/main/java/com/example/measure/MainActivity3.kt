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
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity3 : AppCompatActivity(), SensorEventListener
{
    private lateinit var sensorManager: SensorManager
    private var AcceleroMeter: Sensor? = null
    private var MagneticField: Sensor? = null
    private var Gravity = FloatArray(3)
    private var GeoMagnetic = FloatArray(3)

    private val Orientation = FloatArray(3)
    private val RotationMatrix = FloatArray(9)
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        image = findViewById((R.id.compass_gui))

        setUpSensors()

        val button = findViewById<Button>(R.id.speedometer_button)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity7::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity7::class.java),
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

        AcceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        MagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        val sensorEventListenerAccelerometer: SensorEventListener = object : SensorEventListener
        {
            override fun onSensorChanged(event: SensorEvent)
            {
                Gravity = event.values
                SensorManager.getRotationMatrix(RotationMatrix,null, Gravity, GeoMagnetic)
                SensorManager.getOrientation(RotationMatrix, Orientation)
                image.rotation = (-Orientation[0] * 180 / 3.14159).toFloat()
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
            {
                return
            }
        }

        val sensorEventListenerMagneticField: SensorEventListener = object : SensorEventListener
        {
            override fun onSensorChanged(event: SensorEvent)
            {
                GeoMagnetic = event.values
                SensorManager.getRotationMatrix(RotationMatrix,null, Gravity, GeoMagnetic)
                SensorManager.getOrientation(RotationMatrix, Orientation)
                image.rotation = (-Orientation[0] * 180 / 3.14159).toFloat()
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
            {
                return
            }
        }

        sensorManager.registerListener(sensorEventListenerAccelerometer, AcceleroMeter, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField, MagneticField, SensorManager.SENSOR_DELAY_NORMAL);

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {

    }

    override fun onResume()
    {
        super.onResume()
        sensorManager.registerListener(this, AcceleroMeter, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, MagneticField, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause()
    {
        super.onPause()
        sensorManager.unregisterListener(this, AcceleroMeter)
        sensorManager.unregisterListener(this, MagneticField)
    }
}