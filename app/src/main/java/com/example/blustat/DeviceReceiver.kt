package com.example.blustat

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.blustat.MainActivity

// Vars
private const val TAG = "DeviceReceiver"

// Adds all connected Bluetooth devices into a list
class DeviceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR)
        val newDevice = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        val newDeviceName = newDevice.name
        when (state) {
            BluetoothAdapter.STATE_CONNECTED -> {
                Log.i(TAG, "$newDeviceName connected")
                DeviceIndexing.addDeviceToList(newDevice)
            }
            BluetoothAdapter.STATE_DISCONNECTED -> {
                Log.i(TAG, "$newDeviceName disconnected")
                DeviceIndexing.removeDeviceFromList(newDevice)
            }
        }
    }
}