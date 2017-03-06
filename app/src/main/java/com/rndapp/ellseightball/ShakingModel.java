package com.rndapp.ellseightball;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by ell on 7/11/15.
 */
public class ShakingModel implements SensorEventListener {
    protected ShakingCallback callback;
    protected SensorManager manager;
    protected boolean shaking;

    public ShakingModel(ShakingCallback callback, SensorManager manager) {
        this.callback = callback;
        this.manager = manager;

        setup();
    }

    private void setup(){
        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accelerometer, 10000000);
    }

    public void finish(){
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shaking && !isShaking(event)){
            if (callback != null) callback.didStopShaking();
            shaking = false;
        }else if (!shaking && didShakingStart(event)){
            if (callback != null) callback.didStartShaking();
            shaking = true;
        }
    }

    private boolean didShakingStart(SensorEvent event){
        return Math.pow(dotProduct(event.values,event.values), .5) > 10 + 9.8;
    }

    private boolean isShaking(SensorEvent event){
        return Math.pow(dotProduct(event.values, event.values), .5) > 9.9;
    }

    private float dotProduct(float[] values1, float[] values2){
        if (values1.length != values2.length) throw new IllegalArgumentException("Vectors of different length");

        float sum = 0;
        for (int i = 0; i < values1.length; i++){
            sum += values1[i] * values2[i];
        }

        return sum;
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
