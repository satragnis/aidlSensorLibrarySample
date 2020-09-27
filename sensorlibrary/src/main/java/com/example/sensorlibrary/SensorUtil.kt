package com.example.sensorlibrary

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

object SensorUtil {
    init {
        println("SensorUtil Singleton class invoked.")
    }

    private var iMyAidlSensorInterface: IMyAidlSensorInterface? = null
    private var sensorUtilListener: SensorUtilListener? = null
    private var context: Context? = null
    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var sensorType: Int = -999
    private const val TAG = "++++++"
    lateinit var mainHandler: Handler


    fun initialiseSensor(
        context: Context,
        sensorType: Int,
        sensorUtilListener: SensorUtilListener
    ) {
        this.sensorUtilListener = sensorUtilListener
        this.context = context
        this.sensorType = sensorType
        initiateService()
        mainHandler = Handler(Looper.getMainLooper())
    }

    private fun initiateService() {
        val multiplyServiceIntent = Intent(context, SensorService::class.java)
        multiplyServiceIntent.putExtra("sensorType", sensorType)
        context?.bindService(
            multiplyServiceIntent,
            getServiceConnection(),
            Context.BIND_AUTO_CREATE
        )
    }

    fun getProcessId(): Int {
        return iMyAidlSensorInterface?.getpId() ?: -1
    }

    // to get the service connection
    private fun getServiceConnection(): ServiceConnection {
        return object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
                service?.let {
                    iMyAidlSensorInterface = IMyAidlSensorInterface.Stub.asInterface(it)
                }
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.e(TAG, "Service is disconnected")
                iMyAidlSensorInterface = null
            }
        }
    }

    //register the sensor via aidl
    fun registerSensor(intervalInMS: Int) {
        iMyAidlSensorInterface?.registerSensor(intervalInMS)
        mainHandler.post(readingSensorHandler)
    }

    //handler to ask for latest readings every 8MS
    private val readingSensorHandler = object : Runnable {
        override fun run() {
            getSensorReading()
            mainHandler.postDelayed(this, 8L)
        }
    }

    //unregister sensor via aidl
    fun unregisterSensor() {
        iMyAidlSensorInterface?.unRegisterSensor()
        mainHandler.removeCallbacks(readingSensorHandler)
    }

    //readings from aidl
    fun getSensorReading() {
        iMyAidlSensorInterface?.sensorReading?.let {
            sensorUtilListener?.onSensorDataUpdated(it, sensorType)
        }
    }


}