package com.jorgegallego.jumpingsquare.state;

import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.UIButton;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.DataManager;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

/**
 * Created by Jorge on 10/02/2017.
 */
public class ShopState extends State {

    private boolean eMode, errorMsgOn;

    private int textX, textY, currencyWidth;

    private long startErrorTime;

    private String text = "Shop";

    private UIButton homeBut, moreRedBallsUpBut, moreBallsUpBut, lessLifeMageBut;

    //Upgrades
    private int RBLVL = GameMainActivity.retrieveUpLvl("RBLVL");
    private int MBLVL = GameMainActivity.retrieveUpLvl("MBLVL");
    private int LLMLVL = GameMainActivity.retrieveUpLvl("LLMLVL");

    private int redBallUpCost = RBLVL * 50;
    private int moreBallsUpCost = 30 + MBLVL * 120;
    private int lessLifeMageCost = 150 + LLMLVL * 100;

    @Override
    public void init() {
        moreRedBallsUpBut = new UIButton(GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT/4 /*8/32*/, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT), Assets.boxButton, Assets.boxButtonDown);
        moreBallsUpBut = new UIButton(GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT*13/32, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT), Assets.boxButton, Assets.boxButtonDown);
        lessLifeMageBut = new UIButton(GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT*9/16 /*18/32*/, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT), Assets.boxButton, Assets.boxButtonDown);
        homeBut = new UIButton(GameMainActivity.GAME_WIDTH/2 - (int) (72 * Y_CONSTANT), GameMainActivity.GAME_HEIGHT - (int) (244 * Y_CONSTANT), (int) (144 * Y_CONSTANT), (int) (126 * Y_CONSTANT), Assets.homeButton, Assets.homeButtonDown);

        initText(new Painter(new Canvas()));

        Painter g = new Painter(new Canvas());
        g.setFont(Assets.currencyFont, 30);
        currencyWidth = g.measureText("" + DataManager.getCurrency() +"$", (int) (30 * X_CONSTANT));
    }


    private void sError(boolean mode) {
        startErrorTime = System.nanoTime();
        eMode = mode;
        errorMsgOn = true;
    }

    private void error(Painter g) {
        g.setColor(Color.RED);
        g.setFont(Assets.textFont, 30);
        if (eMode) {
            g.drawString("Not Enough Money!", 115, GameMainActivity.GAME_HEIGHT / 2 + 40);
        } else {
            g.drawString("Max level reached!", 125, GameMainActivity.GAME_HEIGHT / 2 + 40);
        }
        if (startErrorTime + 900000000 < System.nanoTime()) {
            startErrorTime = 0;
            errorMsgOn = false;
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Painter g) {
        //g.setColor(Assets.backgroundColor);
        //g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(Assets.background6, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        g.setFont(Assets.titlesFont, 150 * X_CONSTANT);
        g.setColor(Color.BLACK);
        g.drawString(text, textX - (int) (15 * X_CONSTANT), textY + (int) (15 *X_CONSTANT));
        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);

        homeBut.render(g);

        g.setColor(Color.BLACK);
        g.setFont(Assets.currencyFont, (int) (60 * X_CONSTANT));
        g.drawRect((int) (GameMainActivity.GAME_WIDTH - currencyWidth - 42 * X_CONSTANT), (int) (20 * X_CONSTANT),  (int) (currencyWidth + 22 * X_CONSTANT), (int) (80 * X_CONSTANT), (int) (10  * X_CONSTANT));
        g.drawString("" + DataManager.getCurrency() + "$", (int) (GameMainActivity.GAME_WIDTH - currencyWidth - 32 * X_CONSTANT), (int) (80 * X_CONSTANT));

        moreRedBallsUpBut.render(g);
        g.drawImage(Assets.moreRedBallsImage, GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT/4, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT));
        g.setFont(Assets.textFont, 40 * X_CONSTANT);
        g.setColor(Color.BLACK);
        g.drawString("INCREASE RED BALL %           " + redBallUpCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT) - 1, GameMainActivity.GAME_HEIGHT/4 + (int) (82 * X_CONSTANT) + 2);
        g.setColor(Color.WHITE);
        g.drawString("INCREASE RED BALL %           " + redBallUpCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT), GameMainActivity.GAME_HEIGHT/4 + (int) (82 * X_CONSTANT));
        g.setFont(Assets.textFont, 35 * X_CONSTANT);
        g.setColor(Color.BLACK);
        if (RBLVL > 8) {
            g.drawString("MAX", GameMainActivity.GAME_WIDTH/16 + (int) (30 * X_CONSTANT), GameMainActivity.GAME_HEIGHT/4 - (int) (8 * X_CONSTANT));
        } else {
            g.drawString("LEVEL " + RBLVL, GameMainActivity.GAME_WIDTH / 16 + (int) (10 * X_CONSTANT), GameMainActivity.GAME_HEIGHT / 4 - (int) (8 * X_CONSTANT));
        }

        moreBallsUpBut.render(g);
        g.setFont(Assets.textFont, 40 * X_CONSTANT);
        g.drawImage(Assets.moreBallsImage, GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT*13/32, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT));
        g.drawString("MORE BALLS IN GENERAL     " + moreBallsUpCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT) - 1, GameMainActivity.GAME_HEIGHT*13/32 + (int) (82 * X_CONSTANT) + 2);
        g.setColor(Color.WHITE);
        g.drawString("MORE BALLS IN GENERAL     " + moreBallsUpCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*13/32 + (int) (82 * X_CONSTANT));
        g.setFont(Assets.currencyFont, 35 * X_CONSTANT);
        g.setColor(Color.BLACK);
        if (MBLVL > 2) {
            g.drawString("MAX", GameMainActivity.GAME_WIDTH/16 + (int) (30 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*13/32 - (int) (8 * X_CONSTANT));
        } else {
            g.drawString("LEVEL " + MBLVL, GameMainActivity.GAME_WIDTH/16 + (int) (10 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*13/32 - (int) (8 * X_CONSTANT));
        }

        lessLifeMageBut.render(g);
        g.setFont(Assets.textFont, 40 * X_CONSTANT);
        g.drawImage(Assets.lessLifeMageUp, GameMainActivity.GAME_WIDTH/16, GameMainActivity.GAME_HEIGHT*9/16, (int) (144 * X_CONSTANT), (int) (144 * X_CONSTANT));
        g.drawString("MAGE HAS LESS HP                " + lessLifeMageCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT) - 1, GameMainActivity.GAME_HEIGHT*9/16 + (int) (82 * X_CONSTANT) + 2);
        g.setColor(Color.WHITE);
        g.drawString("MAGE HAS LESS HP                " + lessLifeMageCost + "$", GameMainActivity.GAME_WIDTH/16 + (int) (164 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*9/16 + (int) (82 * X_CONSTANT));
        g.setFont(Assets.currencyFont, 35 * X_CONSTANT);
        g.setColor(Color.BLACK);
        if (LLMLVL > 4) {
            g.drawString("MAX", GameMainActivity.GAME_WIDTH/16 + (int) (30 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*9/16 - (int) (8 * X_CONSTANT));
        } else {
            g.drawString("LEVEL " + LLMLVL, GameMainActivity.GAME_WIDTH/16 + (int) (10 * X_CONSTANT), GameMainActivity.GAME_HEIGHT*9/16 - (int) (8 * X_CONSTANT));
        }

        if (errorMsgOn) {
            error(g);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                homeBut.onTouchDown(scaledX, scaledY);
                moreRedBallsUpBut.onTouchDown(scaledX, scaledY);
                moreBallsUpBut.onTouchDown(scaledX, scaledY);
                lessLifeMageBut.onTouchDown(scaledX, scaledY);
            case MotionEvent.ACTION_UP:
                if (homeBut.onTouchUp(scaledX, scaledY)) {
                    setCurrentState(new MenuState());
                    return true;
                } else if (moreRedBallsUpBut.onTouchUp(scaledX, scaledY)) {
                    if (redBallUpCost <= DataManager.getCurrency() && RBLVL < 10) {
                        DataManager.changeCurrency(-redBallUpCost);
                        GameMainActivity.upgradeLvlUp("RBLVL");
                        RBLVL += 1;
                        redBallUpCost = RBLVL * 50;
                    } else if (RBLVL != 9) {
                        sError(true);
                    } else {
                        sError(false);
                    }
                } else if (moreBallsUpBut.onTouchUp(scaledX, scaledY)) {
                    if (moreBallsUpCost <= DataManager.getCurrency() && MBLVL < 4) {
                        DataManager.changeCurrency(-moreBallsUpCost);
                        GameMainActivity.upgradeLvlUp("MBLVL");
                        MBLVL += 1;
                        moreBallsUpCost = 50 + MBLVL * 120;
                    } else if (MBLVL != 3) {
                        sError(true);
                    } else {
                        sError(false);
                    }
                } else if (lessLifeMageBut.onTouchUp(scaledX, scaledY)) {
                    if (lessLifeMageCost <= DataManager.getCurrency() && LLMLVL < 6) {
                        DataManager.changeCurrency(-lessLifeMageCost);
                        GameMainActivity.upgradeLvlUp("LLMLVL");
                        LLMLVL += 1;
                        lessLifeMageCost = 50 + LLMLVL * 150;
                    } else if (LLMLVL != 4) {
                        sError(true);
                    } else {
                        sError(false);
                    }
                } else {
                    return true;
                }
            Painter g = new Painter(new Canvas());
            g.setFont(Assets.currencyFont, 30);
            currencyWidth = g.measureText("" + DataManager.getCurrency() +"$", (int) (30 * X_CONSTANT));
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public String name() {
        return "Shop State";
    }

    private void initText(Painter g){
        g.setFont(Assets.titlesFont, 100 * X_CONSTANT);
        int width = g.measureText(text, (int) (100 * X_CONSTANT));
        textX = GameMainActivity.GAME_WIDTH/2  - width/4;
        textY = (int) (200 * Y_CONSTANT);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}

