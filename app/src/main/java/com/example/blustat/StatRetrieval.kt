package com.example.blustat

import android.bluetooth.*
import android.util.Log

// Vars
private const val TAG = "StatRetrieval"

// List of functions to get certain stats from a Bluetooth device
object StatRetrieval {
    // Checks for a delay from device to Bluetooth device
    fun bluetoothDelay(device: BluetoothDevice): Long {
        Log.i(TAG, "Currently running on " + Thread.currentThread().name)
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return (endTime - startTime)
    }

    fun bluetoothBasicInfo(device: BluetoothDevice): Array<String> {
        Log.i(TAG, "Currently running on " + Thread.currentThread().name)
        // Basic info of the device connected
        val deviceName = device.name
        val deviceUUID = device.uuids[0].toString()
        val deviceAddress = device.address
        val deviceClass = device.bluetoothClass.majorDeviceClass
        var deviceType = "Typeless"
        when (deviceClass) {
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

        // Because of how we are returning data, this array will be returned for passing all our data
        val deviceStats = arrayOf(deviceName, deviceUUID, deviceAddress, deviceType)

        return(deviceStats)
    }
}
