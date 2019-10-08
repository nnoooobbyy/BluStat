package com.example.blustat

import android.app.IntentService;
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothProfile
import android.content.Intent
import android.util.Log

// Vars
private const val TAG = "StatService"

class StatService {
    // Checks for a delay from device to Bluetooth device
    fun bluetoothDelay(device: BluetoothDevice): Long {
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    fun bluetoothBasicInfo(device: BluetoothDevice) {
        val uuidList = device.uuids
        val deviceType = device.type
        val deviceBattery = device.
    }
}
