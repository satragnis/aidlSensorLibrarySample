// IMyAidlSensorInterface.aidl
package com.example.sensorlibrary;

// Declare any non-default types here with import statements

interface IMyAidlSensorInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    String getSensorReading();

    int getpId();

    void registerSensor(int timeIntervalMS);

    void unRegisterSensor();

    }
