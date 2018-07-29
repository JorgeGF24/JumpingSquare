package com.jorgegallego.jumpingsquare.model.Mages;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.state.PlayState;

/**
 * Created by Jorge y Juan on 10/09/2016.
 */
public abstract class Mage {

    PlayState game;
    String ID;
    double redStartTime;
    final int START_X, START_Y;
    final float X_CONSTANT;
    int x, y, width, height, HP;
    double velY, velX, accelX, accelY;
    boolean hitProcessOn, visible = true, red, dead;
    Bitmap mageImage, imageWhenHurt;
    Typeface font = Typeface.create("Arial", Typeface.BOLD);


    public Mage(Bitmap mageImage, Bitmap imageWhenHurt, int width, int height, float xConstant, int HP, PlayState game, String ID) {
        this.width = width;
        this.height = height;

        START_X = GameMainActivity.GAME_WIDTH/2 - width/ 2;
        START_Y = GameMainActivity.GAME_HEIGHT/2 - height/ 2;

        X_CONSTANT = xConstant;

        x = START_X;
        y = START_Y;

        this.mageImage = mageImage;
        this.imageWhenHurt = imageWhenHurt;

        accelX = RandomNumberGenerator.getRandSign() * RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT /1000, 200 * GameMainActivity.GAME_HEIGHT /1000);
        accelY = RandomNumberGenerator.getRandSign() * RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT /1000, 200 * GameMainActivity.GAME_HEIGHT /1000);

        this.HP = HP;

        this.game = game;
    }

    public int getY(){return y;}

    public int getX(){return x;}

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public void whenKilled() {
        accelY = 1000 * GameMainActivity.GAME_HEIGHT /1000;
        dead = true;
    }

    public void whenHit(int damage) {
        if (!visible) {
            return;
        }
        Assets.playSound(Assets.hit, 0);

        HP -= damage;

        getRed();
        if (HP <= 0) {
            HP = 0;
            whenKilled();
        }
    }

    public void getRed() {
        if (!red) {
            redStartTime = System.nanoTime();
            red = true;
        } else {
            if (System.nanoTime() - redStartTime > 50000000) {
                red = false;
            }
        }
    }

    public void enter() {
        y = - height;
        velY = 200;
    }

    public void update(double delta){
        if (!visible) {
            return;
        }

        randomMov(delta);

        if (red){
            getRed();
        }
    }

    public void randomMov(double delta){
        if (!dead) {
            if (x > START_X + 10 * X_CONSTANT && accelX > 0) {
                accelX = -RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT / 1000, 200 * GameMainActivity.GAME_HEIGHT / 1000);
            }
            if (x < START_X - 10 * X_CONSTANT && accelX < 0) {
                accelX = RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT / 1000, 200 * GameMainActivity.GAME_HEIGHT / 1000);
            }
            if (y > START_Y + 10 * X_CONSTANT && accelY > 0) {
                accelY = -RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT / 1000, 200 * GameMainActivity.GAME_HEIGHT / 1000);
            }
            if (y < START_Y - 10 * X_CONSTANT && accelY < 0) {
                accelY = RandomNumberGenerator.getRandIntBetween(100 * GameMainActivity.GAME_HEIGHT / 1000, 200 * GameMainActivity.GAME_HEIGHT / 1000);
            }
        } else {
            if (x > START_X + 5 * X_CONSTANT && accelX > 0) {
                accelX = -RandomNumberGenerator.getRandIntBetween(200 * GameMainActivity.GAME_HEIGHT / 1000, 400 * GameMainActivity.GAME_HEIGHT / 1000);
            }
            if (x < START_X - 5 * X_CONSTANT && accelX < 0) {
                accelX = RandomNumberGenerator.getRandIntBetween(200 * GameMainActivity.GAME_HEIGHT / 1000, 400 * GameMainActivity.GAME_HEIGHT / 1000);
            }
        }

        velX += accelX * delta;

        if (Math.abs(velY) < 200 * X_CONSTANT || dead) {
            velY += accelY * delta;
        } else {
            velY -= 1;
        }

        if (velX > 500 * X_CONSTANT || velX < -500 * X_CONSTANT) {
            accelX = 0;
        }
        if ((velY > 500 * X_CONSTANT || velY < -500 * X_CONSTANT) && !dead) {
            accelY = 0;
        }

        y += velY * delta;
        x += velX * delta;

        if (y > GameMainActivity.GAME_HEIGHT && dead) {
            visible = false;
            game.mageKilled(ID);
        }
    }

    public void render(Painter g) {
        if (!visible) {
            return;
        }
        g.drawImage(red||dead?imageWhenHurt:mageImage, x, y, Math.round(width * X_CONSTANT), Math.round(height* X_CONSTANT));
        int textSize = Math.round(30 * X_CONSTANT);
        g.setFont(font, textSize);
        g.setColor(Assets.mageLifeFontColor);
        int textWidth = g.measureText("" + HP, textSize);
        g.drawImage(Assets.heart, x + Math.round(width * X_CONSTANT/2) + textWidth/2, y - textSize * 2, 30, 30);
        g.drawString("" + HP, x + Math.round(width * X_CONSTANT/2) - (textWidth / 2), y);
    }

    public abstract void renderExtras(Painter g, float xConstant);
}
