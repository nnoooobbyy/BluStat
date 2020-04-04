package com.example.blustat

import android.bluetooth.*
import android.util.Log

// Vars
private const val TAG = "StatRetrieval"

// List of functions to get certain stats from a Bluetooth device
object StatRetrieval {

    // Checks the ping from device to Bluetooth device by asking the Bluetooth device for its UUID
    fun bluetoothPing(device: BluetoothDevice): Long {
        Log.i(TAG, "Currently running function bluetoothPing on ${Thread.currentThread().name}")
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return (endTime - startTime)
    }
}
