package com.example.blustat

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log

// Vars
private const val TAG = "DeviceIndexing"

object DeviceIndexing {
    private val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var connectedDevices = mutableListOf<BluetoothDevice>()

    // Checks to see if a Bluetooth device is connected
    private fun isConnected(device: BluetoothDevice): Boolean {
        try {
            val m = device.javaClass.getMethod("isConnected")
            return m.invoke(device) as Boolean
        } catch (e: Exception) {
            Log.w(TAG, "Exception thrown: $e")
            throw IllegalStateException(e)
        }
    }

    // Gets all bonded Bluetooth devices and adds them into a list if they're connected
    fun refreshList() {
        Log.i(TAG, "Refreshing connectedDevices")
        /*
        val mBluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        connectedDevices = mBluetoothManager.getConnectedDevices(BluetoothProfile.STATE_CONNECTED)
        Log.i(TAG, "connectedDevices: $connectedDevices")
         */
        connectedDevices = mutableListOf()
        try {
            val bondedDevices = mBluetoothAdapter.bondedDevices
            if (bondedDevices != null) {
                for (device in bondedDevices) {
                    if(isConnected((device))) {
                        connectedDevices.add(device)
                        Log.i(TAG, "${device.name} added to connectedDevices")
                    }
                }
            }
        } catch (e: InterruptedException) {
            // Restore interrupt status
            Log.w(TAG, "Exception thrown: $e")
            Thread.currentThread().interrupt()
        }
    }

    fun addDeviceToList(device: BluetoothDevice) {
        connectedDevices.add(device)
        Log.i(TAG, "${device.name} added to connectedDevices")
    }

    fun removeDeviceFromList(device: BluetoothDevice) {
        connectedDevices.remove(device)
        Log.i(TAG, "${device.name} removed from connectedDevices")
    }
}