package com.example.blustat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import android.content.Intent



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Bluetooth service setup
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // Check if device supports Bluetooth
        if (mBluetoothAdapter != null) {
            // Check if Bluetooth is enabled
            if (mBluetoothAdapter.isEnabled) {
                // Start stat retrieval service
            } else {
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Toast.makeText(applicationContext, "This device does not support Bluetooth!", Toast.LENGTH_LONG).show()
            System.err.println("MainScreen: This device does not support Bluetooth");
        }
    }
}
