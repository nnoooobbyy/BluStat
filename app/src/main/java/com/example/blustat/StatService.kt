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
    var mBluetoothHeadset: BluetoothHeadset? = null
    var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    // Checks if Bluetooth device is a headset
    fun isBluetoothHeadsetConnected(): Boolean {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED
    }

    // Checks for a delay from device to Bluetooth device
    fun bluetoothDelay(device: BluetoothDevice): Long {
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    fun onHandleIntent(intent: Intent) {
        try {
            Log.i(TAG, "BlueLag : Bluetooth headset connection: " + isBluetoothHeadsetConnected())
            // Proxy setup
            val mProfileListener = object : BluetoothProfile.ServiceListener {
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                    if (profile == BluetoothProfile.HEADSET) {
                        mBluetoothHeadset = proxy as BluetoothHeadset
                    }
                }

                override fun onServiceDisconnected(profile: Int) {
                    if (profile == BluetoothProfile.HEADSET) {
                        mBluetoothHeadset = null
                    }
                }
            }
            // Establish connection to the proxy.
            mBluetoothAdapter.getProfileProxy(this, mProfileListener, BluetoothProfile.HEADSET)
            // Check if there is a proxy
            if (mBluetoothHeadset != null) {
                val headsetList = mBluetoothHeadset!!.getConnectedDevices()
                for (i in headsetList.indices) {
                    Log.i(TAG, "BlueLag : Headset connection: " + headsetList[i].name)
                    Log.i(TAG, "BlueLag : Delay: " + bluetoothDelay(headsetList[i]) + "ms")
                    DeviceIndexing.latencyAppend(headsetList[i].name, bluetoothDelay(headsetList[i]))
                }
            }
            // Sleep for 5 seconds
            Thread.sleep(5000)
            startService(intent)
        } catch (e: InterruptedException) {
            // Restore interrupt status
            Thread.currentThread().interrupt()
        }
    }
}
