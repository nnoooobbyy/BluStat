package com.example.blustat

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.example.blustat.DeviceIndexing.connectedDevices
import kotlinx.coroutines.*

// Vars
private const val TAG = "MainActivity"
var currentDevice = 0
var startTime: Long = 0

// Controls what the user sees
// NO BLUETOOTH FUNCTIONS SHOULD BE CALLED FROM THIS CLASS
class MainActivity : AppCompatActivity() {

    // On start, establishes that the device is using Bluetooth and then moves onto display services
    override fun onCreate(savedInstanceState: Bundle?) {
        // Setting up receiver
        val receiver = DeviceReceiver()
        val filter = IntentFilter().apply {
        addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
        }
        registerReceiver(receiver, filter)

        // Setting up content
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val textDeviceName: TextView = findViewById(R.id.textDeviceName)
        val textDeviceUUID: TextView = findViewById(R.id.textDeviceUUID)
        val textDeviceType: TextView = findViewById(R.id.textDeviceType)
        val textDeviceBattery: TextView = findViewById(R.id.textDeviceBattery)
        val textDevicePing: TextView = findViewById(R.id.textDevicePing)
         */




        // Determine if you should run code or not
        if (checkBluetoothCompatibility()) {
            DeviceIndexing.refreshList()
            //onDeviceChange()
            GlobalScope.launch {
                constantRefresh()
            }
        }
    }

    // This check to run though compatibility testing for a bluetooth device
    private fun checkBluetoothCompatibility(): Boolean{
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // Check if device supports Bluetooth
        if (mBluetoothAdapter != null) {
            Log.i(TAG, "Device supports Bluetooth")
            // Check if Bluetooth is enabled
            return if (mBluetoothAdapter.isEnabled) {
                Log.i(TAG, "Device has Bluetooth enabled")
                // Start info display service
                true
            } else {
                Log.w(TAG, "Device has Bluetooth disabled")
                // Ask to enable Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 2)
                Toast.makeText(applicationContext, "Please restart the app after enabling Bluetooth", Toast.LENGTH_LONG).show()
                false
            }
        } else {
            // Inform the user that Bluetooth isn't supported
            Log.e(TAG, "Device does not support Bluetooth")
            textDeviceName.text = resources.getString(R.string.unsupported_device)
            Toast.makeText(applicationContext, "This device does not support Bluetooth", Toast.LENGTH_LONG).show()
            return false
        }
    }

    // Constantly updates info on the selected Bluetooth device
    fun constantRefresh() {

        while (true) {
            var currentTime = System.currentTimeMillis()
            if ((currentTime - startTime) > 5000) {
                if (connectedDevices != null) {
                    val deviceType = StatRetrieval.getType(connectedDevices[currentDevice])
                    //Log.i(TAG, StatRetrieval.getBattery(this, connectedDevices[currentDevice]))
                    val currentPing = StatRetrieval.getPing(connectedDevices[currentDevice])
                    textDeviceBattery.text = ("Battery: N/A")
                    textDevicePing.text = ("Ping: $currentPing ms")
                    textDeviceName.text = ("${connectedDevices[currentDevice].name}")
                    textDeviceUUID.text = ("UUIDs: ${connectedDevices[currentDevice].uuids}")
                    textDeviceType.text = ("Type: $deviceType")
                    startTime = System.currentTimeMillis()
                    Log.i(TAG, "Time restarted")
                }
            } else {
                //Log.i(TAG, (currentTime - startTime).toString())
            }
        }
    }
}
