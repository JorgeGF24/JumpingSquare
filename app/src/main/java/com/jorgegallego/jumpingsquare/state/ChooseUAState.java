package com.jorgegallego.jumpingsquare.state;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.UIButton;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.DataManager;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.model.Text;

import java.util.Locale;

/**
 * Created by Jorge on 10/02/2017.
 */
public class ChooseUAState extends State {

    private boolean notChoosen = true, moving;

    private Typeface font;

    private Text upgradesBut, appearanceBut, homeBut;

    private State nextState;

    @Override
    public void init() {
        homeBut = new Text(GameMainActivity.GAME_WIDTH/2 - (int) (72 * Y_CONSTANT), GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT), (int) (144 * Y_CONSTANT), (int) (126 * Y_CONSTANT),
                0, GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT), GameMainActivity.GAME_WIDTH, (int) (126 * Y_CONSTANT),
                Assets.homeButton, Assets.homeButtonDown, Color.BLACK, Color.WHITE);

        initText(new Painter(new Canvas()));
    }

    @Override
    public void update(double delta) {
        if (notChoosen) {
            appearanceBut.update(delta);
            upgradesBut.update(delta);
            homeBut.update(delta);

            if (upgradesBut.dead()) {
                setCurrentState(nextState);
            }
        }
    }

    @Override
    public void render(Painter g) {
        if (nextState != null) {
            nextState.render(g);
        }
        if (notChoosen) {
            upgradesBut.render(g);
            appearanceBut.render(g);
        }
        homeBut.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        if (e.getAction() == MotionEvent.ACTION_DOWN && !moving) {
            if (notChoosen) {
                if (homeBut.onTouchDown(scaledX, scaledY)) {
                    return  true;
                }
                upgradesBut.onTouchDown(scaledX, scaledY);
                appearanceBut.onTouchDown(scaledX, scaledY);
                return true;
            }

        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (notChoosen && !moving) {
                if (homeBut.onTouchUp(scaledX, scaledY)) {
                    nextState = new MenuState();
                } else if (appearanceBut.onTouchUp(scaledX, scaledY)) {
                    nextState = new AppearanceState();
                } else if (upgradesBut.onTouchUp(scaledX, scaledY)) {
                    nextState = new ShopState();
                } else {
                    return true;
                }
                moving = true;
                nextState.init();
                upgradesBut.setVelocity(0, (int) (-1200 * GameMainActivity.GAME_HEIGHT / 1280));
                appearanceBut.setVelocity(0, (int) (1200 * GameMainActivity.GAME_HEIGHT / 1280));
                homeBut.setVelocity(0, (int) (1200 * GameMainActivity.GAME_HEIGHT / 1280));
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
        return "ChooseUA State";
    }

    private void initText(Painter g){
        AssetManager am = GameMainActivity.sGame.getActivity().getApplicationContext().getAssets();
        font = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "pricedown.ttf"));

        g.setFont(font, 100 * X_CONSTANT);

        int uTWidth = g.measureText("Upgrades", (int) (100 * X_CONSTANT));
        upgradesBut = new Text(0, 0, GameMainActivity.GAME_WIDTH/2 - uTWidth/4, (int) ((GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT))/4 + 75 * X_CONSTANT),
                GameMainActivity.GAME_WIDTH, (GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT))/2, "Upgrades", font, (int) (100 * X_CONSTANT), Color.BLACK, Color.WHITE);

        int aTWidth = g.measureText("Appearance", (int) (100 * X_CONSTANT));
        appearanceBut = new Text(0, (GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT))/2, GameMainActivity.GAME_WIDTH/2 - aTWidth/4,
                (int) ((GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT))*3/4 + 75 * X_CONSTANT), GameMainActivity.GAME_WIDTH, (GameMainActivity.GAME_HEIGHT - (int) (126 * Y_CONSTANT))/2,
                "Appearances", font, (int) (100 * X_CONSTANT), Color.WHITE, Color.BLACK);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
