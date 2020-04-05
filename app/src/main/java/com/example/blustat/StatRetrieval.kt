package com.example.blustat

import android.bluetooth.*
import android.util.Log

// Vars
private const val TAG = "StatRetrieval"

// List of functions to get certain stats from a Bluetooth device
object StatRetrieval {

    // Checks the ping from device to Bluetooth device by asking the Bluetooth device for its UUID
    fun getPing(device: BluetoothDevice): Long {
        Log.i(TAG, "Currently running function bluetoothPing on ${Thread.currentThread().name}")
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return (endTime - startTime)
    }

    fun getType(device: BluetoothDevice): String {
        var deviceType = "Typeless"
        when (device.bluetoothClass.majorDeviceClass) {
            BluetoothClass.Device.Major.AUDIO_VIDEO -> deviceType = "Audio video"
            BluetoothClass.Device.Major.COMPUTER -> deviceType = "Computer"
            BluetoothClass.Device.Major.HEALTH -> deviceType = "Health"
            BluetoothClass.Device.Major.IMAGING -> deviceType = "Imaging"
            BluetoothClass.Device.Major.MISC -> deviceType = "Miscellaneous"
            BluetoothClass.Device.Major.NETWORKING -> deviceType = "Networking"
            BluetoothClass.Device.Major.PERIPHERAL -> deviceType = "Peripheral"
            BluetoothClass.Device.Major.PHONE -> deviceType = "Phone"
            BluetoothClass.Device.Major.TOY -> deviceType = "Toy"
            BluetoothClass.Device.Major.UNCATEGORIZED -> deviceType = "Uncategorized"
            BluetoothClass.Device.Major.WEARABLE -> deviceType = "Wearable"
        }
        return deviceType
    }
}
