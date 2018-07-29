package com.jorgegallego.jumpingsquare.model.Mages;

import android.graphics.Bitmap;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.model.Plataform;
import com.jorgegallego.jumpingsquare.state.PlayState;

import java.util.ArrayList;

/**
 * Created by Jorge y Juan on 10/09/2016.
 */
public class PlatDestroyer extends Mage{

    private ArrayList<ArrayList<Plataform>> platforms;
    private ArrayList<Integer> doublePlatLvls;
    private int platNumOfLevels, firstRemPlatNum, secondRemPlatNum, thirdRemPlatNum;
    private boolean removeProcessOn, crossesVisible;

    private double startRemovingTime;

    public PlatDestroyer(Bitmap mageImage, Bitmap imageWhenHurt, int width, int height,float xConstant, int HP, PlayState game, String ID,
                         ArrayList<ArrayList<Plataform>> platforms, int numOfPlats, ArrayList<Integer> doublePlatLvls) {
        super(mageImage, imageWhenHurt, width, height, xConstant, HP, game, ID);
        this.platforms = platforms;
        platNumOfLevels = numOfPlats;
        this.doublePlatLvls = doublePlatLvls;
    }

    @Override
    public void whenKilled() {
        super.whenKilled();
    }

    @Override
    public void whenHit(int damage) {
        super.whenHit(damage);
    }

    @Override
    public void update(double delta) {
        if (!visible) {
            return;
        }
        super.update(delta);
        if (removeProcessOn || (HP % 4 == 0 && HP != 48)) {
            removePlats();
        }
    }

    @Override
    public void render(Painter g) {
        super.render(g);
    }

    public void renderExtras(Painter g, float xConstant){
        if (!visible) {
            return;
        }
        for (int i = 0; i < platforms.size(); i++) {
            for (int a = 0; a < platforms.get(i).size(); a++) {
                if (removeProcessOn && (i == firstRemPlatNum || i == secondRemPlatNum || i == thirdRemPlatNum) && crossesVisible){
                    float crossX = platforms.get(i).get(a).getX();
                    float crossY = platforms.get(i).get(a).getY();
                    g.drawImage(Assets.platRed, (int) crossX, (int) crossY, platforms.get(i).get(a).getWidth(), platforms.get(i).get(a).getHeight());
                }
            }
        }
    }

    private void removePlats() {
        if (!visible) {
            return;
        }
        boolean visible = true;
        if (startRemovingTime == 0) {
            Assets.playSound(Assets.evilLaugh, 0);
            visible = true;
            startRemovingTime = System.nanoTime();
            removeProcessOn = true;
            crossesVisible = true;
            platNumOfLevels--;
            firstRemPlatNum = RandomNumberGenerator.getRandInt(platNumOfLevels) - 1;
            switch (platNumOfLevels - firstRemPlatNum){
                case 0:case 1:case 2:
                    secondRemPlatNum = firstRemPlatNum + 3 - platNumOfLevels;
                    thirdRemPlatNum = secondRemPlatNum + 3;
                    break;
                case 3:case 4:case 5:
                    secondRemPlatNum = firstRemPlatNum + 3;
                    thirdRemPlatNum = secondRemPlatNum + 3 - platNumOfLevels;
                    break;
                default:
                    secondRemPlatNum = firstRemPlatNum + 3;
                    thirdRemPlatNum = secondRemPlatNum + 3;
                    break;
            }

            platNumOfLevels++;

        } else if (startRemovingTime + 3000000000d < System.nanoTime()) {
            visible = false;
            startRemovingTime = 0;
            crossesVisible = false;
            removeProcessOn = false;
        } else if (startRemovingTime + 2500000000d < System.nanoTime()) {
            visible = true;
            crossesVisible = true;
        } else if (startRemovingTime + 2000000000d < System.nanoTime()) {
            visible = false;
            crossesVisible = false;
        } else if (startRemovingTime + 1500000000d < System.nanoTime()) {
            visible = true;
            crossesVisible = true;
        } else if (startRemovingTime + 1000000000d < System.nanoTime()) {
            visible = false;
            crossesVisible = false;
        } else if (startRemovingTime + 500000000d < System.nanoTime()) {
            visible = true;
            crossesVisible = true;
        }
        platforms.get(firstRemPlatNum).get(0).removed(visible);
        if (doublePlatLvls.contains(firstRemPlatNum)){
            platforms.get(firstRemPlatNum).get(1).removed(visible);
        }
        platforms.get(secondRemPlatNum).get(0).removed(visible);
        if (doublePlatLvls.contains(secondRemPlatNum)){
            platforms.get(secondRemPlatNum).get(1).removed(visible);
        }
        platforms.get(thirdRemPlatNum).get(0).removed(visible);
        if (doublePlatLvls.contains(thirdRemPlatNum)){
            platforms.get(thirdRemPlatNum).get(1).removed(visible);
        }

    }
}
