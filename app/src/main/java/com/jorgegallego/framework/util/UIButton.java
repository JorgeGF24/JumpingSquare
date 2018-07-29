package com.jorgegallego.framework.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge y Juan on 15/07/2016.
 */
public class UIButton {
    private Rect buttonRect;
    private boolean buttonDown, img, selected, locked;
    private Bitmap buttonImage, buttonDownImage, lock;
    private int fillColor, buttonColor;
    private String ID;

    public UIButton(int left, int top, int width, int height, Bitmap buttonImage, Bitmap buttonDownImage) {
        buttonRect = new Rect(left, top, left + width, top + height);
        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonDownImage;
        img = true;
    }

    public UIButton(int left, int top, int width, int height, int buttonColor, boolean locked, String ID) {
        buttonRect = new Rect(left, top, left + width, top + height);
        this.buttonColor = buttonColor;
        this.ID = ID;
        this.locked = locked;
        if (locked) {
            lock = Assets.lock;
        }
    }

    public UIButton(int left, int top, int width, int height, Bitmap buttonImage, Bitmap buttonDownImage, int color) {
        buttonRect = new Rect(left, top, left + width, top + height);
        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonDownImage;
        fillColor = color;
    }

    public void render(Painter g) {
        if (img) {
            if (fillColor != 0) {
                g.setColor(fillColor);
                g.fillRect(buttonRect.left + 4, buttonRect.top + 4, buttonRect.width() - 8, buttonRect.height() - 8);
            }
            g.drawImage(buttonDown ? buttonDownImage : buttonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
        } else {
            g.setColor(buttonColor);
            g.fillRect(buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
            if (locked) {
                g.drawImage(lock, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
            }
            if(buttonDown) {
                g.setColor(Assets.greySelectT);
                g.fillRect(buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
            }
            if (selected) {
                g.setColor(Color.RED);
                g.drawRect(buttonRect.left +(int) (5 * GameMainActivity.GAME_WIDTH/780f), buttonRect.top + (int) (5 * GameMainActivity.GAME_WIDTH/780f), buttonRect.width() - (int) (8 * GameMainActivity.GAME_WIDTH/780f), buttonRect.height() - (int) (8 * GameMainActivity.GAME_WIDTH/780f), (int) (8 * GameMainActivity.GAME_WIDTH/780f));
            }
            //g.setColor(new Color(140, 140, 140));
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void unlock(){
        GameMainActivity.unlockColor(ID);
        locked = false;
        lock = null;
    }

    public boolean isLocked() {
        return locked;
    }

    public void onTouchDown(int touchX, int touchY) {
        if (buttonRect.contains(touchX, touchY)) {
            Assets.playSound(Assets.click, 0);
            buttonDown = true;
        }
    }

    public boolean onTouchUp(int touchX, int touchY) {
        if (buttonRect.contains(touchX, touchY) && buttonDown) {
            buttonDown = false;
            return true;
        } else {
            buttonDown = false;
            return false;
        }
    }

}
