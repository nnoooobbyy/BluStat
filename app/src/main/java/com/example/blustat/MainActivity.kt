package com.example.blustat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.widget.Toast
import android.content.Intent
import android.util.Log

// Vars
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // Check if device supports Bluetooth
        if (mBluetoothAdapter != null) {
            Log.i(TAG, "mBluetoothAdapter : Bluetooth device is detected on the device!")
            // Check if Bluetooth is enabled
            if (mBluetoothAdapter.isEnabled) {
                Log.i(TAG, "mBluetoothAdapter : Device has Bluetooth enabled!")
                // Start stat retrieval service
                Toast.makeText(applicationContext, "This device has bluetooth!", Toast.LENGTH_LONG).show()
            } else {
                Log.w(
                    TAG,
                    "mBluetoothAdapter : Device has Bluetooth, however it is not enabled, attempting to enable..."
                )
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Toast.makeText(applicationContext, "This device does not support Bluetooth!", Toast.LENGTH_LONG).show()
            Log.e(TAG, "mBluetoothAdapter :  This device does not support Bluetooth")
        }
    }
}
