package com.example.sensorsampleaidl

import android.hardware.Sensor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sensorlibrary.SensorUtil
import com.example.sensorlibrary.SensorUtilListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorUtilListener {
    companion object {
        var sensor_error: String = "No sensor found! Press on Start Sensor"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resetSensor()
        //call the initialiseSensor function passing context, sensor type, listener
        SensorUtil.initialiseSensor(this, Sensor.TYPE_ROTATION_VECTOR, this)

        registerSensorBTN.setOnClickListener {
            SensorUtil.registerSensor(8000)
        }

        unRegisterSensorBTN.setOnClickListener {
            SensorUtil.unregisterSensor()
        }
    }

    override fun onResume() {
        super.onResume()
        resetSensor()
    }

    override fun onStop() {
        super.onStop()
        SensorUtil.unregisterSensor()
    }

    override fun onSensorDataUpdated(sensorData: String, sensorType: Int) {
        if (sensorType == Sensor.TYPE_ROTATION_VECTOR) {
            rotationReadings.text = sensorData
        }else{
            resetSensor()
        }
    }

    private fun resetSensor(){
        rotationReadings.text = sensor_error
    }
}