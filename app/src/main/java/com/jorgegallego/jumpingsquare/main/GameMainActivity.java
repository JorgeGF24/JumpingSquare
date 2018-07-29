package com.jorgegallego.jumpingsquare.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.jorgegallego.jumpingsquare.R;
import com.jorgegallego.jumpingsquare.state.State;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class GameMainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    public Sensor mAccelerometer;
    public static State currentState;

    private static MediaPlayer mp;
    private static int songPauseTime;

    public static final int GAME_HEIGHT = 800, GAME_WIDTH = 450;
    public static int bestScore;
    public static GameView sGame;
    public static AssetManager assets;

    private static SharedPreferences prefs;
    private static final String bestScoreKey = "bestScoreKey", currencyKey = "money Key", musicKey = "On/Off Key",  outColorKey = "OCK", inColorKey = "ICK"; //redBallUpLvlKey = "RBLVL", moreBallsUpLvlKey = "MBLVL" lessLifeMageUpKey = "LLMLVL", modeAndNum --> outside color unlocked = "o" + ColorNum, inside color unlocked = "i" + ColorNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("data", Activity.MODE_PRIVATE);
        //prefsScore = getSharedPreferences(bestScoreKey, Activity.MODE_PRIVATE);
        //refsCurrency = getSharedPreferences(currencyKey, Activity.MODE_PRIVATE);
        bestScore = DataManager.getBestScore();
        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    static void setBestScore(int bestScore) {
        GameMainActivity.bestScore = bestScore;

        prefs.edit().putInt(bestScoreKey, bestScore).apply();
    }

    static void saveCurrencyProg(int currency) {

        prefs.edit().putInt(currencyKey, currency).apply();
    }

    public static void music(boolean newMode) {
        prefs.edit().putBoolean(musicKey, newMode).apply();
    }

    public static void unlockColor(String modeAndNum) {
        prefs.edit().putBoolean(modeAndNum, false).apply();
    }

    public static void oColor(int o) {
        prefs.edit().putInt(outColorKey, o).apply();
    }

    public static void iColor(int i) {
        prefs.edit().putInt(inColorKey, i).apply();
    }

    //Upgrades

    public static void upgradeLvlUp(String upKey) {
        prefs.edit().putInt(upKey, prefs.getInt(upKey, 1) + 1).apply();
    }

    public static boolean retriveIsColorLocked(String modeAndNum) {
        return !(modeAndNum.equals("o0") || modeAndNum.equals("i0")) && prefs.getBoolean(modeAndNum, true);
    }

    public static  int retrieveInColor() { return prefs.getInt(inColorKey, 0); }

    public static  int retrieveOutColor() { return prefs.getInt(outColorKey, 0); }

    public static boolean retreiveMusicMode() {
        return prefs.getBoolean(musicKey, true);
    }

    public static int retreiveBestScore() {
        return prefs.getInt(bestScoreKey, 0);
    }

    public static int retrieveCurrency() {
        return prefs.getInt(currencyKey, 0);
    }

    public static int retrieveUpLvl(String upKey) {
        return prefs.getInt(upKey, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sGame.onResume();
        if (sGame.getPlay() && retreiveMusicMode()){
            mp = MediaPlayer.create(sGame.getActivity(), R.raw.song2);
            mp.seekTo(songPauseTime);
            mp.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sGame.onPause();
        mSensorManager.unregisterListener(this);
        if (mp != null) {
            mp.pause();
            songPauseTime = mp.getCurrentPosition();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(currentState != null) {
            currentState.onSensorChanged(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static void setCurrentState(State newState) {
        currentState = newState;
    }

    public static void playMusic(boolean loop) {
        if (retreiveMusicMode()) {
            mp = MediaPlayer.create(sGame.getActivity(), R.raw.song2);
            mp.setLooping(loop);
            mp.start();
        }
    }

    public static void stopMusic() {
        if (mp != null) {
            mp.stop();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            sGame.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}
