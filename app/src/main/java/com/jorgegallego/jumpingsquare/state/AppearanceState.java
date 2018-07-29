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

import java.util.ArrayList;

/**
 * Created by Jorge on 18/02/2017.
 */
public class AppearanceState extends State {

    private ArrayList<UIButton> outsideColorButs, insideColorButs, faceButs, facesColorButs;
    private final int B_ROWS = 8;
    private UIButton homeBut, larrow, rarrow;


    private int oSelectedColor, iSelectedColor;
    private String titleText = "Appearance";
    private int tWidth, tX, tY;

    private  int currencyWidth;
    private long startErrorTime;
    private boolean errorMsgOn;

    @Override
    public void init() {
        Painter g = new Painter(new Canvas());
        g.setFont(Assets.titlesFont, 1);
        tWidth = g.measureText(titleText, (int) (120 * X_CONSTANT));
        tX = GameMainActivity.GAME_WIDTH/2  - tWidth/4;
        tY = (int) (100 * Y_CONSTANT);

        g.setFont(Assets.currencyFont, 1);
        currencyWidth = g.measureText("" + DataManager.getCurrency() +"$", (int) (30 * X_CONSTANT));

        homeBut = new UIButton(GameMainActivity.GAME_WIDTH/2 - (int) (72 * Y_CONSTANT), GameMainActivity.GAME_HEIGHT - (int) (244 * Y_CONSTANT), (int) (144 * Y_CONSTANT), (int) (126 * Y_CONSTANT), Assets.homeButton, Assets.homeButtonDown);
        larrow = new UIButton(GameMainActivity.GAME_WIDTH/4 - (int) (50 * X_CONSTANT), (int) (250 * X_CONSTANT), (int) (100 * Y_CONSTANT), (int) (100 * Y_CONSTANT), Assets.larrow, Assets.larrowDown);
        rarrow = new UIButton(GameMainActivity.GAME_WIDTH * 3 / 4 - (int) (65 * X_CONSTANT), (int) (250 * X_CONSTANT), (int) (100 * Y_CONSTANT), (int) (100 * Y_CONSTANT), Assets.rarrow, Assets.rarrowDown);

        outsideColorButs = new ArrayList<>();

        for (int i = 0, u = GameMainActivity.retrieveOutColor(), B_MARGINGS = 0; i < B_ROWS * 2; i++) {
            int width = (int) ((GameMainActivity.GAME_WIDTH - 40 * X_CONSTANT)/B_ROWS - B_MARGINGS * X_CONSTANT);
            int x, y;
            if (i < B_ROWS) {
                x = (int) (20 * X_CONSTANT + (width + B_MARGINGS * X_CONSTANT) * i);
                y = (int) (470 * X_CONSTANT);
            } else {
                x = (int) (20 * X_CONSTANT + (width + B_MARGINGS * X_CONSTANT) * (i - B_ROWS));
                y = (int) (470 * X_CONSTANT + width);
            }
            if (u == i) {
                UIButton currentB = new UIButton(x, y, width, width, Assets.oColor[i], GameMainActivity.retriveIsColorLocked("o" + i), "o" + i);
                currentB.setSelected(true);
                oSelectedColor = i;
                outsideColorButs.add(currentB);
            } else {
                outsideColorButs.add(new UIButton(x, y, width, width, Assets.oColor[i], GameMainActivity.retriveIsColorLocked("o" + i), "o" + i));
            }
        }

        insideColorButs = new ArrayList<>();
        for (int i = 0, u = GameMainActivity.retrieveInColor(), B_MARGINGS = 0; i < B_ROWS * 2; i++) {
            int width = (int) ((GameMainActivity.GAME_WIDTH - 40 * X_CONSTANT)/B_ROWS - B_MARGINGS * X_CONSTANT);
            int x, y;
            if (i < B_ROWS) {
                x = (int) (20 * X_CONSTANT + (width + B_MARGINGS * X_CONSTANT) * i);
                y = (int) (770 * X_CONSTANT);
            } else {
                x = (int) (20 * X_CONSTANT + (width + B_MARGINGS * X_CONSTANT) * (i - B_ROWS));
                y = (int) (770 * X_CONSTANT + width);
            }
            if (u == i) {
                UIButton currentB = new UIButton(x, y, width, width, Assets.iColor[i], GameMainActivity.retriveIsColorLocked("i" + i), "i" + i);
                currentB.setSelected(true);
                iSelectedColor = i;
                insideColorButs.add(currentB);
            } else {
                insideColorButs.add(new UIButton(x, y, width, width, Assets.iColor[i], GameMainActivity.retriveIsColorLocked("i" + i), "i" + i));
            }
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
        g.setFont(Assets.titlesFont, 120 * X_CONSTANT);
        g.setColor(Color.BLACK);
        g.drawString(titleText, tX - 9, tY + 9);
        g.setColor(Color.WHITE);
        g.drawString(titleText, tX, tY);

        //draw squaredy
        g.setColor(Assets.oColor[oSelectedColor]);
        g.fillRect((int) (GameMainActivity.GAME_WIDTH/2 - 50 * X_CONSTANT), (int) (250 * X_CONSTANT), (int) (100 * X_CONSTANT), (int) (100 * X_CONSTANT));
        g.setColor(Assets.iColor[iSelectedColor]);
        g.fillRect((int) (GameMainActivity.GAME_WIDTH/2 - 32 * X_CONSTANT), (int) (268 * X_CONSTANT), (int) (64 * X_CONSTANT), (int) (64 * X_CONSTANT));

        g.setColor(Color.BLACK);
        g.setFont(Assets.currencyFont, (int) (30 * X_CONSTANT));
        g.drawRect((int) (GameMainActivity.GAME_WIDTH - currencyWidth - 57 * X_CONSTANT), (int) (160 * X_CONSTANT),  (int) (currencyWidth + 22 * X_CONSTANT), (int) (80 * X_CONSTANT), (int) (10  * X_CONSTANT));
        g.drawString("" + DataManager.getCurrency() + "$", (int) (GameMainActivity.GAME_WIDTH - currencyWidth - 47 * X_CONSTANT), (int) (220 * X_CONSTANT));

        g.setColor(Assets.iBoxColor);
        g.fillRect((int) (18 * X_CONSTANT), (int) (385 * X_CONSTANT), GameMainActivity.GAME_WIDTH - (int) (36 * X_CONSTANT), (int) (280 * X_CONSTANT));
        g.fillRect((int) (18 * X_CONSTANT), (int) (685 * X_CONSTANT), GameMainActivity.GAME_WIDTH - (int) (36 * X_CONSTANT), (int) (280 * X_CONSTANT));
        g.setColor(Color.BLACK);
        g.drawRect((int) (15 * X_CONSTANT), (int) (380 * X_CONSTANT), GameMainActivity.GAME_WIDTH - (int) (36 * X_CONSTANT), (int) (280 * X_CONSTANT), (int) (12 * X_CONSTANT));
        g.drawRect((int) (15 * X_CONSTANT), (int) (680 * X_CONSTANT), GameMainActivity.GAME_WIDTH - (int) (36 * X_CONSTANT), (int) (280 * X_CONSTANT), (int) (12 * X_CONSTANT));
        g.setFont(Assets.subTitlesFont, (int) (70 * X_CONSTANT));
        g.drawString("Border Color", (int) (30 * X_CONSTANT), (int) (460 * X_CONSTANT));
        g.drawString("Inside Color", (int) (30 * X_CONSTANT), (int) (760 * X_CONSTANT));
        g.setFont(Assets.testFont, (int) (32 * X_CONSTANT));
        g.drawString("30$", (int) (640 * X_CONSTANT), (int) (425 * X_CONSTANT));
        g.drawString("each", (int) (620 * X_CONSTANT), (int) (455 * X_CONSTANT));
        g.drawString("30$", (int) (640 * X_CONSTANT), (int) (725 * X_CONSTANT));
        g.drawString("each", (int) (620 * X_CONSTANT), (int) (755 * X_CONSTANT));

        for (int i = 0; i < B_ROWS * 2; i++) {
            outsideColorButs.get(i).render(g);
            insideColorButs.get(i).render(g);
        }
        homeBut.render(g);
        larrow.render(g);
        rarrow.render(g);

        if (errorMsgOn) {
            error(g);
        }
    }

    private void sError() {
        startErrorTime = System.nanoTime();
        errorMsgOn = true;
    }

    private void error(Painter g) {
        g.setColor(Color.RED);
        g.setFont(Assets.textFont, 30);
        g.drawString("Not Enough Money!", 115, GameMainActivity.GAME_HEIGHT / 2 + 40);

        if (startErrorTime + 900000000 < System.nanoTime()) {
            startErrorTime = 0;
            errorMsgOn = false;
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                homeBut.onTouchDown(scaledX, scaledY);
                for (int i = 0; i < outsideColorButs.size(); i++) {
                   outsideColorButs.get(i).onTouchDown(scaledX, scaledY);
                   insideColorButs.get(i).onTouchDown(scaledX, scaledY);
                }
            case (MotionEvent.ACTION_UP):
                if (homeBut.onTouchUp(scaledX, scaledY)) {
                    setCurrentState(new MenuState());
                    return true;
                } else {
                    for (int i = 0; i < outsideColorButs.size(); i++) {
                        if (outsideColorButs.get(i).onTouchUp(scaledX, scaledY)) {
                            if (outsideColorButs.get(i).isLocked()) {
                                if (30 > DataManager.getCurrency()) {
                                    sError();
                                    return true;
                                } else {
                                    outsideColorButs.get(i).unlock();
                                    DataManager.changeCurrency(-30);

                                    Painter g = new Painter(new Canvas());
                                    g.setFont(Assets.currencyFont, 30);
                                    currencyWidth = g.measureText("" + DataManager.getCurrency() +"$", (int) (30 * X_CONSTANT));
                                }
                            }
                            outsideColorButs.get(oSelectedColor).setSelected(false);
                            oSelectedColor = i;
                            outsideColorButs.get(i).setSelected(true);
                            outsideColorButs.get(i).unlock();
                            GameMainActivity.oColor(i);
                        }
                        if (insideColorButs.get(i).onTouchUp(scaledX, scaledY)) {
                            if (insideColorButs.get(i).isLocked()) {
                                if (30 > DataManager.getCurrency()) {
                                    sError();
                                    return true;
                                } else {
                                    insideColorButs.get(i).unlock();
                                    DataManager.changeCurrency(-30);

                                    Painter g = new Painter(new Canvas());
                                    g.setFont(Assets.currencyFont, 30);
                                    currencyWidth = g.measureText("" + DataManager.getCurrency() +"$", (int) (30 * X_CONSTANT));
                                }
                            }
                            insideColorButs.get(iSelectedColor).setSelected(false);
                            iSelectedColor = i;
                            insideColorButs.get(i).setSelected(true);
                            insideColorButs.get(i).unlock();
                            GameMainActivity.iColor(i);
                        }
                    }
                return true;
                }
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public String name() {
        return "Appearance State";
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
