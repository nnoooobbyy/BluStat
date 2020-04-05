package com.example.blustat

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

// Vars
private const val TAG = "MainActivity"

// Controls what the user sees
// NO BLUETOOTH FUNCTIONS SHOULD BE CALLED FROM THIS CLASS
class MainActivity : AppCompatActivity() {

    // On start, establishes that the device is using Bluetooth and then moves onto display services
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Currently running function onCreate on ${Thread.currentThread().name}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkBluetoothCompatibility()
        val textDeviceName: TextView = findViewById(R.id.textDeviceName)
        val textDeviceUUID: TextView = findViewById(R.id.textDeviceUUID)
        val textDeviceType: TextView = findViewById(R.id.textDeviceType)
        val textDeviceBattery: TextView = findViewById(R.id.textDeviceBattery)
        val textDevicePing: TextView = findViewById(R.id.textDevicePing)
    }

    // This check to run though compatibility testing for a bluetooth device
    private fun checkBluetoothCompatibility(){
        Log.i(TAG, "Currently running function checkBluetoothCompatibility on ${Thread.currentThread().name}")
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // Check if device supports Bluetooth
        if (mBluetoothAdapter != null) {
            Log.i(TAG, "Bluetooth supported on the device")
            // Check if Bluetooth is enabled
            if (mBluetoothAdapter.isEnabled) {
                Log.i(TAG, "Device has Bluetooth enabled")
                // Start info display service
                displayService()
            } else {
                Log.w(TAG, "Device has Bluetooth disabled")
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Log.e(TAG, "Device does not support Bluetooth")
            textDeviceName.text = resources.getString(R.string.unsupported_device)
            Toast.makeText(applicationContext, "This device does not support Bluetooth", Toast.LENGTH_LONG).show()
        }
    }

    // Display service for displaying information
    fun displayService() {
        Log.i(TAG, "Currently running function displayService on ${Thread.currentThread().name}")
        try {
            // Getting info about the selected Bluetooth device
            val selectedDevice = DeviceIndexing.deviceIndex()[0]
            val deviceType = StatRetrieval.getType(selectedDevice)
            val currentPing = StatRetrieval.getPing(selectedDevice)

            // User end of viewing data
            textDeviceName.text = ("${selectedDevice.name}")
            textDeviceUUID.text = ("UUIDs: ${selectedDevice.uuids}")
            textDeviceType.text = ("Type: $deviceType")
            textDeviceBattery.text = ("Battery: N/A")
            textDevicePing.text = ("Ping: $currentPing ms")
        } catch (e: NullPointerException){
            Log.w(TAG, "Exception thrown: $e")
        }
    }
}
