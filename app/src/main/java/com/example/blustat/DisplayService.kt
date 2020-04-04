package com.example.blustat

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

// Vars
private const val TAG = "DisplayService"

object DisplayService : AppCompatActivity() {

    // Display service for displaying information
    fun statDisplay() {
        Log.i(TAG, "Currently running function displayService on ${Thread.currentThread().name}")
        try {
            // Getting info about the selected Bluetooth device
            val selectedDevice = DeviceIndexing.deviceIndex()[0]
            val currentPing = StatRetrieval.bluetoothPing(selectedDevice)

            // User end of viewing data
            Log.i(TAG, "Displaying device info for ${selectedDevice.name}")
            textPlaceholderDeviceInfo.visibility = View.VISIBLE
            textDeviceView.text = ("Connected to ${selectedDevice.name}")
        } catch (e: NullPointerException){
            Log.w(TAG, "No Bluetooth devices connected!")
        }
        statDisplay()
    }
}