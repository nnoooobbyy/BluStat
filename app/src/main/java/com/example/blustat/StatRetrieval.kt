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

    /* REDUNDANT
    fun bluetoothBasicInfo(device: BluetoothDevice): BluetoothDevice {
        val currentDevice = BluetoothDevice()
        try {
            currentDevice.name = device.name
            currentDevice.UUID = device.uuids[0].toString()
            currentDevice.address = device.address
            when (device.bluetoothClass.majorDeviceClass) {
                BluetoothClass.Device.Major.AUDIO_VIDEO -> currentDevice.type = "Audio video"
                BluetoothClass.Device.Major.COMPUTER -> currentDevice.type = "Computer"
                BluetoothClass.Device.Major.HEALTH -> currentDevice.type = "Health"
                BluetoothClass.Device.Major.IMAGING -> currentDevice.type = "Imaging"
                BluetoothClass.Device.Major.MISC -> currentDevice.type = "Miscellaneous"
                BluetoothClass.Device.Major.NETWORKING -> currentDevice.type = "Networking"
                BluetoothClass.Device.Major.PERIPHERAL -> currentDevice.type = "Peripheral"
                BluetoothClass.Device.Major.PHONE -> currentDevice.type = "Phone"
                BluetoothClass.Device.Major.TOY -> currentDevice.type = "Toy"
                BluetoothClass.Device.Major.UNCATEGORIZED -> currentDevice.type = "Uncategorized"
                BluetoothClass.Device.Major.WEARABLE -> currentDevice.type = "Wearable"
            }
        } catch (e: NullPointerException) {
            Log.w(TAG, "NullPointerException thrown")
        }
        return (device)
    }
     */
}
