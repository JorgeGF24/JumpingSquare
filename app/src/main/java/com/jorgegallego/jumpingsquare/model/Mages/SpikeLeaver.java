package com.jorgegallego.jumpingsquare.model.Mages;

import android.graphics.Bitmap;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.model.Spike;
import com.jorgegallego.jumpingsquare.model.Squaredy;
import com.jorgegallego.jumpingsquare.state.PlayState;

import java.util.ArrayList;

/**
 * Created by Jorge on 29/04/2017.
 */
public class SpikeLeaver extends Mage {

    private boolean spiked;
    private int spikeNum;
    private ArrayList<Spike> spikes;
    private Squaredy hero;
    private PlayState game;

    public SpikeLeaver(Bitmap mageImage, Bitmap imageWhenHurt, int width, int height, float xConstant, int HP, Squaredy ball, PlayState game, String ID) {
        super(mageImage, imageWhenHurt, width, height, xConstant, HP, game, ID);
        hero = ball;
        this.game = game;
    }

    @Override
    public void renderExtras(Painter g, float xConstant) {
        if (spiked) {
            for (int i = 0; i < spikeNum; i++) {
                spikes.get(i).render(g);
            }
        }
    }

    public void update(double delta) {
        super.update(delta);
        if (spiked) {
            for (int i = 0; i < spikeNum; i++) {
                spikes.get(i).update(delta);
                if (hero.getRect().intersect(spikes.get(i).getSpikeRect())) {
                    game.die();
                }
            }
        }
    }

    @Override
    public void whenHit(int damage){
        super.whenHit(damage);
        if (HP % 2 == 1) {
            leaveSpike();
        }
    }

    public void leaveSpike() {
        spiked = true;
        spikeNum = RandomNumberGenerator.getRandInt(3);
        spikes = new ArrayList<>();
        for (int i = 0; i < spikeNum; i++) {
            spikes.add(new Spike((int) (100 * X_CONSTANT), this));
        }
    }

    public void spikeEnd() {
        spiked = false;
    }
}
