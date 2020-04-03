package com.example.blustat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Toast
import android.util.Log

// Vars
private const val TAG = "MainActivity"

// Data classes
//data class Device(var name: String = "None", var UUID: String = "None", var address: String = "None", var type: String = "Typeless")

// Controls what the user sees
// NO BLUETOOTH FUNCTIONS SHOULD BE CALLED FROM THIS CLASS
class MainActivity : AppCompatActivity() {

    // On start, establishes that the device is using Bluetooth and then moves onto display services
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Currently running function onCreate on " + Thread.currentThread().name)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // Check if device supports Bluetooth
        if (mBluetoothAdapter != null) {
            Log.i(TAG, "mBluetoothAdapter : Bluetooth supported on the device")
            // Check if Bluetooth is enabled
            if (mBluetoothAdapter.isEnabled) {
                Log.i(TAG, "mBluetoothAdapter : Device has Bluetooth enabled")
                // Start info display service
                displayService()
            } else {
                Log.w(TAG, "mBluetoothAdapter : Device has Bluetooth disabled")
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
                onCreate(savedInstanceState)
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Log.e(TAG, "mBluetoothAdapter : Device does not support Bluetooth")
            Toast.makeText(applicationContext, "This device does not support Bluetooth", Toast.LENGTH_LONG).show()
        }
    }

    // Display service
    fun displayService() {
        Log.i(TAG, "Currently running function displayService on " + Thread.currentThread().name)
        val selectedDevice = DeviceIndexing.deviceIndex()[0]
        val currentPing = StatRetrieval.bluetoothPing(selectedDevice)

        /* REDUNDANT
        var currentDevice = Device()
        currentDevice = StatRetrieval.bluetoothBasicInfo(selectedDevice)
         */
    }
}
