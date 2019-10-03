package com.example.blustat

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile
import android.content.Intent





class StatService {
    var mBluetoothHeadset: BluetoothHeadset? = null
    var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun isBluetoothHeadsetConnected(): Boolean {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED
    }

    fun bluetoothDelay(device: BluetoothDevice): Long {
        val startTime = System.currentTimeMillis()
        device.fetchUuidsWithSdp()
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    fun onHandleIntent(intent: Intent) {
        try {
            println("BlueLag: Bluetooth headset connection: " + isBluetoothHeadsetConnected())
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
                    println("BlueLag: Headset connection: " + headsetList[i].name)
                    println("BlueLag: Delay: " + bluetoothDelay(headsetList[i]) + "ms")
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
