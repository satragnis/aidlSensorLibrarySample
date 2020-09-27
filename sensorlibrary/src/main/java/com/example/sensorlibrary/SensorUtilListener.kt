package com.example.sensorlibrary


interface SensorUtilListener {
    fun onSensorDataUpdated(sensorData:String,sensorType:Int)
}