package com.jorgegallego.jumpingsquare.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.model.Mages.SpikeLeaver;

/**
 * Created by Jorge on 29/04/2017.
 */
public class Spike {

    private Bitmap spikeImage;
    private int x, velY, y, diameter;
    private final int GRAVITY = 1700 * GameMainActivity.GAME_HEIGHT /1000;
    private SpikeLeaver creator;
    private Rect spikeRect;

    public Spike(int diameter, SpikeLeaver mage) {
        this.diameter = diameter;
        x = RandomNumberGenerator.getRandInt(GameMainActivity.GAME_WIDTH - diameter);
        y = - diameter;
        velY = 0;
        spikeImage = Assets.spikeImage;

        spikeRect = new Rect(x, y, x + diameter, y + diameter);

        creator = mage;
    }

    public void update(double delta) {
        velY += GRAVITY * delta;
        y += (int) (velY * delta);

        spikeRect.set(x, y, x + diameter, y + diameter);

        if (y > GameMainActivity.GAME_HEIGHT) {
            creator.spikeEnd();
        }
    }

    public void render(Painter g) {
        g.drawImage(spikeImage, x, y, diameter, diameter);
    }

    public Rect getSpikeRect(){ return spikeRect;}
}
