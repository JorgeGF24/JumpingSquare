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
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

import java.util.Locale;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class MenuState extends State {

    private UIButton playBut, shopBut, skinBut, musicBut;
    private boolean music = GameMainActivity.retreiveMusicMode();

    AssetManager am = GameMainActivity.sGame.getActivity().getApplicationContext().getAssets();
    private Typeface font = Typeface.createFromAsset(am,
            String.format(Locale.US, "fonts/%s", "pricedown.ttf"));
    //Typeface font = Typeface.create("Arial", Typeface.BOLD);
    //private Text menuText = new Text("Jumping Square", 1, font, 20, Assets.menuTextColor);

    @Override
    public void init() {
        musicBut = new UIButton(GameMainActivity.GAME_WIDTH  - (int) (110 * X_CONSTANT), GameMainActivity.GAME_HEIGHT - (int) (106  * X_CONSTANT), (int) (90 * X_CONSTANT), (int) (86  * X_CONSTANT), Assets.musicButton, Assets.musicButtonDown);
        playBut = new UIButton(GameMainActivity.GAME_WIDTH/2  - (int) (452 * X_CONSTANT)/2, GameMainActivity.GAME_HEIGHT/2 - (int) (415 * X_CONSTANT), (int) (452 * X_CONSTANT), (int) (205  * X_CONSTANT), Assets.playButton, Assets.playButtonDown);
        shopBut = new UIButton(GameMainActivity.GAME_WIDTH/2  - (int) (452 * X_CONSTANT)/2, GameMainActivity.GAME_HEIGHT/2 - (int) (110 * X_CONSTANT), (int) (452 * X_CONSTANT), (int) (205  * X_CONSTANT), Assets.shopButton, Assets.shopButtonDown);
        skinBut = new UIButton(GameMainActivity.GAME_WIDTH/2  - (int) (400 * X_CONSTANT)/2, GameMainActivity.GAME_HEIGHT/2 + (int) (235 * X_CONSTANT), (int) (400 * X_CONSTANT), (int) (163  * X_CONSTANT), Assets.skinButton, Assets.skinButtonDown);
    }

    @Override
    public void update(double delta) {
        //textX += velX * delta;
        //if (textX + width < 0) {
        //    setCurrentState(new PlayState());
        //}
    }

    @Override
    public void render(Painter g) {
        //g.setColor(Assets.backgroundColor);
        //g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(Assets.background, 0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        musicBut.render(g);
        if (!music) {
            g.drawImage(Assets.cross, GameMainActivity.GAME_WIDTH  - (int) (110 * X_CONSTANT), GameMainActivity.GAME_HEIGHT - (int) (106  * X_CONSTANT), (int) (90 * X_CONSTANT), (int) (86  * X_CONSTANT));
        }
        shopBut.render(g);
        playBut.render(g);
        skinBut.render(g);
        //g.setColor(Assets.backgroundScoreColor);
        //g.setColor(Color.WHITE);
        //g.drawString(text, textX, textY);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        //if (!transStarted) {
        //  startTransition();
        //}
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            skinBut.onTouchDown(scaledX, scaledY);
            shopBut.onTouchDown(scaledX, scaledY);
            playBut.onTouchDown(scaledX, scaledY);
            musicBut.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            if (shopBut.onTouchUp(scaledX, scaledY)) {
                setCurrentState(new ShopState());
            } else if (playBut.onTouchUp(scaledX, scaledY)) {
                setCurrentState(new PlayState());
            } else if (skinBut.onTouchUp(scaledX, scaledY)){
                setCurrentState(new AppearanceState());
            } else if (musicBut.onTouchUp(scaledX,scaledY)) {
                music = !music;
                GameMainActivity.music(music);
            }
        }

        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    public String name() {
        return "MenuState";
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
