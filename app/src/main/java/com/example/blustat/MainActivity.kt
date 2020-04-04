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
        val textDeviceView: TextView = findViewById(R.id.textDeviceView)
        val textPlaceholderDeviceInfo: TextView = findViewById(R.id.textPlaceholderDeviceInfo)
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
                DisplayService.statDisplay()
            } else {
                Log.w(TAG, "Device has Bluetooth disabled")
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Log.e(TAG, "Device does not support Bluetooth")
            textDeviceView.text = resources.getString(R.string.unsupported_device)
            Toast.makeText(applicationContext, "This device does not support Bluetooth", Toast.LENGTH_LONG).show()
        }
    }


}
