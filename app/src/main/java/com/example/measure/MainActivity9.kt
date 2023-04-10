package com.example.measure

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity9 : AppCompatActivity() {

    private lateinit var detailtext:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        detailtext = findViewById(R.id.details)

        val detail = Constants.details
        detailtext.text = detail

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

        val button4 = findViewById<Button>(R.id.timer_button)
        button4.setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java)
            val pm = packageManager
            pm.setComponentEnabledSetting(
                ComponentName(this, MainActivity5::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
            )
            startActivity(intent)
        }
    }
}

fun batteryhealth(): String {

    var healthLbl:String = "Battery"

    when (BatteryManager.EXTRA_HEALTH.toInt()) {
        BatteryManager.BATTERY_HEALTH_COLD -> healthLbl = "Cold Battery"
        BatteryManager.BATTERY_HEALTH_DEAD -> healthLbl = "Dead Battery"
        BatteryManager.BATTERY_HEALTH_GOOD -> healthLbl = "Good Battery"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> healthLbl = "Battery Over Voltage"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> healthLbl = "Battery Overheated"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> healthLbl = "Unspecified Battery Failure"
        BatteryManager.BATTERY_HEALTH_UNKNOWN -> {}
        else -> {}
    }
    return healthLbl
}

object Constants {
    val details: String
        get() {
            var details = ""
            try {
                details += """
                       OS version: ${System.getProperty("os.version")}
                       
                       """.trimIndent()
                details += """
                       API Level: ${Build.VERSION.SDK}
                       
                       """.trimIndent()
                details += """
                       Device: ${Build.DEVICE}
                       
                       """.trimIndent()
                details += """
                       Model: ${Build.MODEL}
                       
                       """.trimIndent()
                details += """
                       Product: ${Build.PRODUCT}
                       
                       """.trimIndent()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            try {
                details += """
                       VERSION.RELEASE : ${Build.VERSION.RELEASE}
                       VERSION.INCREMENTAL : ${Build.VERSION.INCREMENTAL}
                       VERSION.SDK.NUMBER : ${Build.VERSION.SDK_INT}
                       BOARD : ${Build.BOARD}
                       BOOTLOADER : ${Build.BOOTLOADER}
                       BRAND : ${Build.BRAND}
                       CPU_ABI : ${Build.CPU_ABI}
                       CPU_ABI2 : ${Build.CPU_ABI2}
                       DISPLAY : ${Build.DISPLAY}
                       FINGERPRINT : ${Build.FINGERPRINT}
                       HARDWARE : ${Build.HARDWARE}
                       HOST : ${Build.HOST}
                       ID : ${Build.ID}
                       MANUFACTURER : ${Build.MANUFACTURER}
                       MODEL : ${Build.MODEL}
                       PRODUCT : ${Build.PRODUCT}
                       SERIAL : ${Build.SERIAL}
                       TAGS : ${Build.TAGS}
                       TIME : ${Build.TIME}
                       TYPE : ${Build.TYPE}
                       UNKNOWN : ${Build.UNKNOWN}
                       USER : ${Build.USER}
                       """.trimIndent()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return details
        }
}
