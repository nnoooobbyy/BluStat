package com.example.blustat

import android.bluetooth.*
import android.util.Log
import android.bluetooth.BluetoothDevice



// Vars
private const val TAG = "DeviceIndexing"

// Adds all connected Bluetooth devices into a list
object DeviceIndexing {
    // Vars
    private val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    // Checks to see if a Bluetooth device is connected
    private fun isConnected(device: BluetoothDevice): Boolean {
        Log.i(TAG, "Currently running function isConnected on ${Thread.currentThread().name}")
        try {
            val m = device.javaClass.getMethod("isConnected")
            return m.invoke(device) as Boolean
        } catch (e: Exception) {
            Log.w(TAG, "Exception thrown: $e")
            throw IllegalStateException(e)
        }
    }

    // Gets all bonded Bluetooth devices and adds them into a list if they're connected
    fun deviceIndex(): MutableList<BluetoothDevice> {
        Log.i(TAG, "Currently running function deviceIndex on ${Thread.currentThread().name}")
        val connectedDevices = mutableListOf<BluetoothDevice>()
        try {
            val bondedDevices = mBluetoothAdapter.bondedDevices
            if (bondedDevices != null) {
                for (device in bondedDevices) {
                    if(isConnected((device))) {
                        connectedDevices.add(device)
                        Log.i(TAG, "${device.name} added to connectedDevices list.")
                    } else {
                        Log.i(TAG, "${device.name} not added to connectedDevices list.")
                    }
                }
            }
        } catch (e: InterruptedException) {
            // Restore interrupt status
            Log.w(TAG, "Exception thrown: $e")
            Thread.currentThread().interrupt()
        }
        return connectedDevices
    }
}