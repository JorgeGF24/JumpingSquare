package com.jorgegallego.jumpingsquare.state;

import android.hardware.SensorEvent;
import android.view.MotionEvent;


import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.framework.util.Painter;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class LoadState extends State {
    @Override
    public void init() {
        Assets.load();
    }

    @Override
    public void update(double delta) {
        setCurrentState(new MenuState());
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    public String name() {
        return "LoadState";
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
