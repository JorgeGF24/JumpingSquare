/*package com.jorgegallego.framework.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge y Juan on 12/07/2016.

public class Text {

    private String textString;
    private int x, y, size, oVelY, oVelX, velX, velY, color, accelX, accelY, width;
    private Typeface font;
    private Paint.Style style;
    private Rect textBounds;

    public Text(String string, int x, int y, Typeface font, int size, int color) {
        textString = string;
        this.x = x;
        this.y = y;
        this.font = font;
        this.size = size;
        width = Painter.measureText(string, size, font);
        style = null;
        velX = 0;
        velY = 0;
    }

    public Text(String string, int centerType, Typeface font, int size, int color) {
        switch (centerType) {
            case 1: //fully centered
                width = Painter.measureText(string, size, font);
                x = GameMainActivity.GAME_WIDTH/2 - width/2;
                Paint paintTest = new Paint();
                paintTest.getTextBounds(string, 0, string.length() + 1, textBounds);
                y = GameMainActivity.GAME_HEIGHT/2 - textBounds.height()/2;
                break;
            case 2:
                width = Painter.measureText(string, size, font);
                x = GameMainActivity.GAME_WIDTH/2 - width/2;
                y = 0;
                break;
            case 3:
                x = 0;
                width = Painter.measureText(string, size, font);
                paintTest = new Paint();
                paintTest.getTextBounds(string, 0, string.length() + 1, textBounds);
                y = GameMainActivity.GAME_HEIGHT/2 - textBounds.height()/2;
                break;
        }
        textString = string;
        this.font = font;
        this.size = size;
        style = null;
        velX = 0;
        velY = 0;
    }

    public Text(String string, int x, int y, Typeface font,int size, Paint.Style newStyle) {
        textString = string;
        this.x = x;
        this.y = y;
        this.font = font;
        width = Painter.measureText(string, size, font);
        this.size = size;
        style = newStyle;
        velX = 0;
        velY = 0;
    }
    public Text(String string, int centerType, Typeface font,int size, Paint.Style newStyle) {
        textString = string;
        switch (centerType) {
            case 1: //fully centered
                int width = Painter.measureText(string, size, font);
                x = GameMainActivity.GAME_WIDTH/2 - width/2;
                Paint paintTest = new Paint();
                paintTest.getTextBounds(string, 0, string.length() + 1, textBounds);
                y = GameMainActivity.GAME_HEIGHT/2 - textBounds.height()/2;
                break;
            case 2: // x centered y = 0
                width = Painter.measureText(string, size, font);
                x = GameMainActivity.GAME_WIDTH/2 - width/2;
                y = 0;
                break;
            case 3: //y centered x = 0
                x = 0;
                width = Painter.measureText(string, size, font);
                paintTest = new Paint();
                paintTest.getTextBounds(string, 0, string.length() + 1, textBounds);
                y = GameMainActivity.GAME_HEIGHT/2 - textBounds.height()/2;
                break;
        }
        this.font = font;
        this.size = size;
        style = newStyle;
        velX = 0;
        velY = 0;
    }

    public void update(double delta) {
        if (accelX != 0 && velX < oVelX) {
            velX += accelX+delta;
        } else if (accelY != 0 && velY < oVelY) {
            velX += accelY*delta;
        }
        x += velX*delta;
        y += velY*delta;
    }

    public void render(Painter g) {
        g.setColor(color);
        g.setFont(font, size, style);
        g.drawString(textString, x, y);
    }

    public void setVelX(int velX, boolean accel) {
        if (accel) {
            accelX = velX/5;
        }
        oVelX = velX;
    }

    public void setVelY(int velY, boolean accel) {
        if (accel) {
            accelY = velY/5;
        }
        oVelY = velY;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setStyle(Paint.Style style) {
        this.style = style;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public int getWidth() {
        return width;
    }
}
*/