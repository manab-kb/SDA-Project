package com.example.measure

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupPermissions()

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

        val button4 = findViewById<Button>(R.id.inclination_button)
        button4.setOnClickListener {
            val intent = Intent(this, MainActivity6::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity6::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.dimensions_button)
        button5.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity2::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button6 = findViewById<Button>(R.id.light_button)
        button6.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity4::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        val button7 = findViewById<Button>(R.id.timer_button)
        button7.setOnClickListener {
            val intent = Intent(this, MainActivity5::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity5::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }

        if (Build.BRAND == "samsung") {
//        WORKS FOR SAMSUNG PHONES ONLY - BLOCKED BY OTHER ANDROID SKINS / CONNECTION PROVIDERS
            val mobileStatisticsButton = findViewById<Button>(R.id.MobileStatistics)
            mobileStatisticsButton.setOnClickListener {
                val code = "*#0*#"
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + Uri.encode(code.trim()))
                startActivity(intent)
            }
        } else {
            // WORKS ON ALL DEVICES
            val mobileStatisticsButton = findViewById<Button>(R.id.MobileStatistics)
            mobileStatisticsButton.setOnClickListener {
                val intent = Intent(this, MainActivity9::class.java)
                val pm = packageManager
                pm.setComponentEnabledSetting(
                    ComponentName(this, MainActivity9::class.java),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
                )
                startActivity(intent)
            }
        }

        val soundbutton = findViewById<Button>(R.id.sound_button)
        soundbutton.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity8::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }
    }

    private val CALLREQCODE = 100
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CALL_PHONE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Permissions:", "Permission to call denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CALL_PHONE),
            CALLREQCODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALLREQCODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permissions:", "Permission has been denied by user")
                    Toast.makeText(this, "Permission to make calls denied", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.i("Permissions:", "Permission has been granted by user")
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}
