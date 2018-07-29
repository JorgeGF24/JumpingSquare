package com.jorgegallego.jumpingsquare.state;

/**
 * Created by Jorge on 11/07/2016.
 */

import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.framework.util.Painter;

public abstract class State {

    final float X_CONSTANT = GameMainActivity.GAME_WIDTH/780f;
    final float Y_CONSTANT = GameMainActivity.GAME_HEIGHT/1280f;

    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }

    public abstract void init();

    public abstract void update(double delta);

    public abstract void render(Painter g);

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

    public abstract void onSensorChanged(SensorEvent sensorEvent);

    public abstract String name();

    public abstract void onPause();

    public abstract void onResume();

}

