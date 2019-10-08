package com.example.blustat

import android.bluetooth.*
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log
import android.widget.Toast;

// Vars
private const val TAG = "DeviceIndexing"

// Adds all connected Bluetooth devices into a list
object DeviceIndexing {
    // Vars
    private val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun deviceIndex(): MutableList<BluetoothDevice> {
        Log.i(TAG, "Currently running on " + Thread.currentThread().name)
        val connectedDevices = mutableListOf<BluetoothDevice>()
        try {
            val bondedDevices = mBluetoothAdapter.bondedDevices
            if (bondedDevices != null) {
                for (device in bondedDevices) {
                    if(device.address != null) {
                        connectedDevices.add(device)
                        Log.i(TAG, device.name + " added to connectedDevices list.")
                    } else {
                        Log.i(TAG, device.name + " not added to connectedDevices list.")
                    }
                }
            }
        } catch (e: InterruptedException) {
            // Restore interrupt status
            Thread.currentThread().interrupt()
        }
        return connectedDevices
    }
}