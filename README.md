# aidlSensorLibrarySample
The project is 100% Kotlin
This is a sample project showcasing the use of AIDL via library to get sensor readings 

Library usage:-

1. Implement SensorUtilListener
2. Initialise the Sensor by 
SensorUtil.initialiseSensor(CONTEXT, SENSOR_TYPE, SENSORUTILLISTENER)
3. Register sensor by 
SensorUtil.registerSensor(TIME_INTERVAL)
4. unRegister Sensor by
SensorUtil.unregisterSensor()


