package com.jorgegallego.jumpingsquare.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge y Juan on 15/02/2017.
 */
public class Text {

    private int rextX, rectY, rWidth, rHeight, textX, textY, iX, iY, iWidth, iHeight,
            velY = 0, velX = 0, fontSize, textColor, rectColor, rectDownColor;
    private Rect rect;
    private String string;
    private Typeface font;
    private boolean buttonDown;

    private Bitmap buttonImage, buttonDownImage;

    private final float X_CONSTANT = GameMainActivity.GAME_WIDTH/720f;

    public Text(int rectX, int rectY, int textX, int textY, int width, int height, String string, Typeface font, int fontSize, int rectColor, int textColor) {
        this.textX = textX;
        this.textY = textY;
        this.rextX = rectX;
        this.rectY = rectY;
        rWidth = width;
        rHeight = height;

        rect = new Rect(rectX, this.rectY, rectX + width, this.rectY + height);

        this.string = string;
        this.font = font;
        this.fontSize = (int) (fontSize * X_CONSTANT);
        this.textColor = textColor;
        this.rectColor = rectColor;
    }

    public Text(int imgX, int imgY, int iWidth, int iHeight, int rX, int rY, int rWidth, int rHeight, Bitmap buttonImage, Bitmap buttonDownImage, int rectColor, int rectDownColor) {
        this.rextX = rX;
        this.rectY = rY;
        this.rWidth = rWidth;
        this.rHeight = rHeight;

        this.iX = imgX;
        this.iY = imgY;
        this.iWidth = iWidth;
        this.iHeight = iHeight;

        rect = new Rect(rX, rY, rX + rWidth, rY + rHeight);

        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonDownImage;

        this.rectColor = rectColor;
        this.rectDownColor = rectDownColor;
    }

    public void update(double delta) {
        if (textX != 0) {
            textX += velX * delta;
            textY += velY * delta;
        }

        rextX += velX * delta;
        rectY += velY * delta;

        iX += velX * delta;
        iY += velY * delta;

        rect.set(rextX, rectY, rextX + rWidth, rectY + rHeight);
    }

    public boolean dead() {
        return rextX > GameMainActivity.GAME_WIDTH || rextX + rWidth < 0 || rectY > GameMainActivity.GAME_HEIGHT || rectY + rHeight < 0;
    }

    public void setVelocity(int newVelX, int newVelY) {
        velX = newVelX;
        velY = newVelY;
    }

    public void render(Painter g) {
        if(buttonDown) {
            if (buttonImage != null){
                g.setColor(rectDownColor);
                g.fillRect(rextX, rectY, rWidth, rHeight);
                g.drawImage(buttonDown ? buttonDownImage : buttonImage, iX, iY, iWidth, iHeight);
            } else {
                g.setColor(textColor);
                g.fillRect(rextX, rectY, rWidth, rHeight);
                g.setFont(font, fontSize);
                g.setColor(rectColor);
                g.drawString(string, textX, textY);
            }
        } else {
            if (buttonImage != null){
                g.setColor(rectColor);
                g.fillRect(rextX, rectY, rWidth, rHeight);
                g.drawImage(buttonDown ? buttonDownImage : buttonImage, iX, iY, iWidth, iHeight);
            } else {
                g.setColor(rectColor);
                g.fillRect(rextX, rectY, rWidth, rHeight);
                g.setFont(font, fontSize);
                g.setColor(textColor);
                g.drawString(string, textX, textY);
            }
        }
    }

    public boolean onTouchDown(int touchX, int touchY) {
        if (rect.contains(touchX, touchY)) {
            Assets.playSound(Assets.click, 0);
            buttonDown = true;
            return true;
        } else {
            buttonDown = false;
            return false;
        }
    }

    public boolean onTouchUp(int touchX, int touchY) {
        if (rect.contains(touchX, touchY) && buttonDown) {
            buttonDown = false;
            return true;
        } else {
            buttonDown = false;
            return false;
        }
    }
}
