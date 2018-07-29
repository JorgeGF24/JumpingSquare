package com.jorgegallego.framework.util;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.state.State;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class InputHandler implements View.OnTouchListener {

    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);
    }
}
