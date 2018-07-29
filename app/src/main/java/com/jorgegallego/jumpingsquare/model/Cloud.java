package com.jorgegallego.jumpingsquare.model;

import android.graphics.Bitmap;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge on 10/09/2016.
 */
public class Cloud {

    private Bitmap cloudImage;
    private int x, velY, y, width, height, LWB, UPB;

    public Cloud(int[] widthRange) {
        LWB = widthRange[0];
        UPB = widthRange[1];
        width = RandomNumberGenerator.getRandIntBetween(LWB, UPB);
        height = width/2;
        x = RandomNumberGenerator.getRandInt(GameMainActivity.GAME_WIDTH - width);
        y = RandomNumberGenerator.getRandInt(GameMainActivity.GAME_HEIGHT - height);
        velY = 0;
        int i = 1;//RandomNumberGenerator.getRandInt(4);
        switch (i) {
            case 1:
                cloudImage = Assets.cloud1;
                break;
            case 2:
                cloudImage = Assets.cloud2;
                break;
            case 3:
                cloudImage = Assets.cloud3;
                break;
            case 4:
                cloudImage = Assets.cloud4;
                break;
        }
    }

    private void reset() {
        velY = 0;
        width = RandomNumberGenerator.getRandIntBetween(LWB, UPB);
        height = width/2;
        x = RandomNumberGenerator.getRandInt(GameMainActivity.GAME_WIDTH - width);
        y = 0 - height;
        int i = 1;//RandomNumberGenerator.getRandInt(4);
        switch (i) {
            case 1:
                cloudImage = Assets.cloud1;
                break;
            case 2:
                cloudImage = Assets.cloud2;
                break;
            case 3:
                cloudImage = Assets.cloud3;
                break;
            case 4:
                cloudImage = Assets.cloud4;
                break;
        }
    }

    public void update(double delta, int newVelY) {
        if (newVelY != 0) {
            velY = (int) (newVelY * -0.5 + (width - UPB));
            y += (int) (velY * delta);

            if (y > GameMainActivity.GAME_HEIGHT) {
                reset();
            }
        }
    }

    public void render(Painter g) {
        g.drawImage(cloudImage, x, y, width, height);
    }
}
