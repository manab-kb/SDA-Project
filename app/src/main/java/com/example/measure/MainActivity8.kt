package com.example.measure

import android.app.Activity


import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.measure.R


class MainActivity8 : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var bufferSize: Int = 0
    private lateinit var audioRecord: AudioRecord
    private lateinit var thread: Thread
    private var isRecording: Boolean = false
    private lateinit var dbTextView: TextView
    private lateinit var intensity: TextView
    var dbval: Double = 0.0
    //private lateinit var text2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)
        // Check if the RECORD_AUDIO permission has been granted
        permissionToRecordAccepted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionToRecordAccepted) {
            // Request the RECORD_AUDIO permission
            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        } else {
            // Permission has been granted, initialize AudioRecord
            bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                bufferSize
            )
        }

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

        val startButton = findViewById<Button>(R.id.record_button)
        // Start recording thread when the button is clicked
        startButton.setOnClickListener {
            if (permissionToRecordAccepted) {
                isRecording = true
                thread = Thread(recordAudio)
                thread.start()
            } else {
                Toast.makeText(this, "Permission denied to record audio", Toast.LENGTH_SHORT).show()
            }
        }

        val stopButton = findViewById<Button>(R.id.stop_button)
        // Stop recording thread when the button is clicked
        stopButton.setOnClickListener {
            isRecording = false
            dbTextView.text = getString(R.string.zero)
            intensity.text = getString(R.string.NN)
        }
        dbTextView = findViewById(R.id.brightness)
        intensity = findViewById(R.id.sound_details)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> {
                permissionToRecordAccepted =
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        true
                    } else {
                        false
                    }
                if (!permissionToRecordAccepted) {
                    Toast.makeText(this, "Permission denied to record audio", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Permission has been granted, initialize AudioRecord
                    bufferSize =
                        AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.RECORD_AUDIO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    audioRecord = AudioRecord(
                        MediaRecorder.AudioSource.MIC,
                        SAMPLE_RATE,
                        CHANNEL_CONFIG,
                        AUDIO_FORMAT,
                        bufferSize
                    )
                }
            }
        }
    }

    private val recordAudio = Runnable {
        val buffer = ShortArray(bufferSize)
        audioRecord.startRecording()
        while (isRecording) {
            // Read audio data into buffer
            val readSize = audioRecord.read(buffer, 0, bufferSize)
            if (readSize != AudioRecord.ERROR_INVALID_OPERATION) {
                // Calculate RMS amplitude of audio signal
                var sum: Long = 0
                for (i in 0 until readSize) {
                    sum += buffer[i].toLong() * buffer[i].toLong()
                }
                val rms = Math.sqrt(sum.toDouble() / readSize.toDouble())

                // Convert RMS amplitude to decibels
                val db = 20 * Math.log10(rms)
                dbval = db

                // Update UI with dB level
                runOnUiThread {
                    if (dbval <= 40)
                    {
                        intensity.text = getString(R.string.FaintNoise)
                    }
                    else if (dbval > 40 && dbval <= 60)
                    {
                        intensity.text = getString(R.string.ModNoise)
                    }
                    else if (dbval > 60 && dbval <= 80)
                    {
                        intensity.text = getString(R.string.LoudNoise)
                    }
                    else if (dbval > 80 && dbval <= 100)
                    {
                        intensity.text = getString(R.string.VLoudNoise)
                    }
                    else
                    {
                        intensity.text = getString(R.string.DeafNoise)
                    }
                    dbTextView.text = String.format("%.2f dB", db)
                }
            }
        }
        audioRecord.stop()
    }

    fun onStart(v: View?) {
        super.onStart()
        if (permissionToRecordAccepted) {
            // Start recording audio
            audioRecord.startRecording()
        }
    }

    fun stopButton(v: View?) {
        super.onStop()
        if (permissionToRecordAccepted) {
            // Stop recording audio
            audioRecord.stop()
            audioRecord.release()
        }
    }

    companion object {
        const val SAMPLE_RATE = 44100
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }
}
