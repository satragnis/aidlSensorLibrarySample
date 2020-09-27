package com.example.sensorlibrary

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.IBinder
import android.os.Process

/**
 * This service contains the sensor readings and definitions via aidl
 * the aidl has 3 overridden functions
 */



class SensorService : Service() {
    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var sensorType: Int = 0
    private var sensorReading = ""


    override fun onBind(intent: Intent): IBinder {
        mSensorManager =
            applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        //get instance of rotation based sensors
        // val sensorList = mSensorManager?.getSensorList(Sensor.TYPE_ROTATION_VECTOR)
        sensorType = intent.getIntExtra("sensorType", 0)
        mSensor = mSensorManager?.getDefaultSensor(sensorType)
        return myBinder
    }

    private val myBinder = object : IMyAidlSensorInterface.Stub(), SensorEventListener2 {
        override fun getSensorReading(): String {
            return this@SensorService.sensorReading
        }

        override fun getpId(): Int {
            return Process.myPid()
        }

        override fun registerSensor(timeIntervalMS: Int) {
            mSensor?.let {
                mSensorManager?.registerListener(this, it, timeIntervalMS)//8ms
            }
        }

        override fun unRegisterSensor() {
            mSensorManager?.unregisterListener(this)
        }

        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val sType = event.sensor?.type
                val currentValue = event.values[0]
                if (sType == sensorType) {
                    this@SensorService.sensorReading = currentValue.toString()
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

        override fun onFlushCompleted(p0: Sensor?) {
        }
    }


}
