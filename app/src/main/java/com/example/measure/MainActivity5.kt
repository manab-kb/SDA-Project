package com.example.measure

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener

class MainActivity5 : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        chronometer = findViewById(R.id.chronometer)
        chronometer?.format = "%s"
        chronometer?.base = SystemClock.elapsedRealtime()
        chronometer?.setOnChronometerTickListener(OnChronometerTickListener
        { chronometer ->

        })

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

    fun startChronometer(v: View?)
    {
        if (!running)
        {
            chronometer!!.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer!!.start()
            running = true
        }
    }

    fun pauseChronometer(v: View?)
    {
        if (running)
        {
            chronometer!!.stop()
            pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base
            running = false
        }
    }

    fun resetChronometer(v: View?)
    {
        chronometer!!.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
    }
}
