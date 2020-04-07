package com.example.blustat

import android.bluetooth.*
import android.content.Context
import android.util.Log
import java.util.*
import kotlin.coroutines.coroutineContext

// Vars
private const val TAG = "StatRetrieval"

// List of functions to get certain stats from a Bluetooth device
object StatRetrieval {
    private val gattCallback = object : BluetoothGattCallback() {

        override fun onServicesDiscovered(
            gatt: BluetoothGatt,
            status: Int
        ) {
            Log.i(TAG, "Services discovered with status $status... attempting to read battery level")
            Log.i(TAG, "Services: ${gatt.services}")
            for (service in gatt.services) {
                Log.i(TAG, "Service UUID: ${service.uuid}")
                for (characteristic in service.characteristics) {
                    Log.i(TAG, "Characteristic: ${gatt.readCharacteristic(characteristic)}")
                }
            }


            /*

            val Battery_Service_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
            val Battery_Level_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")

            val batteryService = gatt.getService(Battery_Service_UUID)
            if (batteryService == null) {
                Log.w(TAG, "Battery service not found!")
                return
            }

            val batteryLevel = batteryService.getCharacteristic(Battery_Level_UUID)
            if (batteryLevel == null) {
                Log.w(TAG, "Battery level not found!")
                return
            }

            gatt.readCharacteristic(batteryLevel);
            Log.v(TAG, "batteryLevel: " + gatt.readCharacteristic(batteryLevel));
            */
        }

        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTING -> {
                    Log.i(TAG, "Connecting to GATT server of device ${gatt.device.name} with status $status")
                }
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.i(TAG, "Connected to GATT server of device ${gatt.device.name} with status $status")
                    /*
                    if (gatt.discoverServices()) {
                        Log.i(TAG, "Remote service discovery started")
                    } else {
                        Log.w(TAG, "Remote service discovery not started")
                    }

                     */
                }
                BluetoothProfile.STATE_DISCONNECTING -> {
                    Log.i(TAG, "Disconnecting from GATT server of device ${gatt.device.name} with status $status")
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.i(TAG, "Disconnected from GATT server of device ${gatt.device.name} with status $status")
                    if (status == 133) {
                        Log.i(TAG, "Retrying connection...")
                        gatt.connect()
                    } else {
                        gatt.close()
                    }
                }
            }
        }
    }

    // Checks the ping from device to Bluetooth device by asking the Bluetooth device for its UUID
    fun getPing(device: BluetoothDevice): Long {
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

    fun getBattery(context: Context, device: BluetoothDevice) : String {
        var batteryResults = "N/A"
        val batteryGattCallback = object : BluetoothGattCallback() {
            override fun onServicesDiscovered(
                gatt: BluetoothGatt,
                status: Int
            ) {
                Log.i(TAG, "Services discovered with status $status... attempting to read battery level")
                batteryResults = "made it this far"
                val Battery_Service_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
                val Battery_Level_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")

                val batteryService = gatt.getService(Battery_Service_UUID)
                if (batteryService == null) {
                    batteryResults = "Battery service not found"
                    Log.w(TAG, batteryResults)
                    return
                }

                val batteryLevel = batteryService.getCharacteristic(Battery_Level_UUID)
                if (batteryLevel == null) {
                    batteryResults = "Battery level not found"
                    Log.w(TAG, batteryResults)
                    return
                }

                gatt.readCharacteristic(batteryLevel)
                return
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                status: Int
            ) {
                batteryResults = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0).toString()
                return
            }

            override fun onConnectionStateChange(
                gatt: BluetoothGatt,
                status: Int,
                newState: Int
            ) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.i(TAG, "Connected to GATT server of device ${gatt.device.name} with status $status, discovering services...")
                        if (gatt.discoverServices()) {
                            Log.i(TAG, "Remote service discovery has been started")
                        } else {
                            Log.w(TAG, "Remote service discovery failed to start")
                            return
                        }
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.i(TAG, "Disconnected from GATT server of device ${gatt.device.name} with status $status")
                    }
                }
            }
        }
        val bluetoothGatt = device.connectGatt(context, false, batteryGattCallback)
        return batteryResults
    }
}
