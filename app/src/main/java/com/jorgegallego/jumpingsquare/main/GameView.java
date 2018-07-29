package com.jorgegallego.jumpingsquare.main;
;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.jorgegallego.jumpingsquare.state.LoadState;
import com.jorgegallego.jumpingsquare.state.State;
import com.jorgegallego.framework.util.InputHandler;
import com.jorgegallego.framework.util.Painter;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class GameView extends SurfaceView implements Runnable {

    private final Object M_PAUSE_LOCK;
    private boolean mPaused;
    boolean play = false;

    private Bitmap gameImage;
    private Rect gameImageSrc;
    private Rect gameImageDst;
    private Canvas gameCanvas;
    private Painter graphics;

    private Context activity;

    private Thread gameThread;
    private volatile boolean running = true;
    private volatile State currentState, loadState;

    private InputHandler inputHandler;

    private static Vibrator v;

    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        activity = context;
        gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
        gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
        gameImageDst = new Rect();
        gameCanvas = new Canvas(gameImage);
        graphics = new Painter(gameCanvas);

        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        SurfaceHolder holder = getHolder();
        holder.addCallback(new Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initInput();
                if (currentState == null) {
                    loadState = new LoadState();
                    loadState.init();
                    setCurrentState(loadState);

                }
                initGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //TODO Auto-Generated method stub
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
               onPause();
            }
        });

        M_PAUSE_LOCK = new Object();
        mPaused = false;

    }

    public void setCurrentState(State newState) {
        newState.init();
        currentState = newState;
        inputHandler.setCurrentState(currentState);
        GameMainActivity.setCurrentState(newState);

        System.gc(); //TODO this goes first
    }

    private void updateAndRender(double delta) {
        currentState.update(delta);
        currentState.render(graphics);
        renderGameImage();
    }

    private void renderGameImage() {
        Canvas screen = getHolder().lockCanvas();
        if (screen != null) {
            screen.getClipBounds(gameImageDst);
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
            getHolder().unlockCanvasAndPost(screen);
        }
    }

    public void playOn(boolean bool){play = bool;}
    public boolean getPlay(){return play;}

    private void initInput() {
        if (inputHandler == null) {
            inputHandler = new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }

    private void initGame() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    public Context getActivity(){
        return activity;
    }

    public void onPause() {
        synchronized (M_PAUSE_LOCK) {
            mPaused = true;
        }
    }

    public void onResume() {
        synchronized (M_PAUSE_LOCK) {
            mPaused = false;
            M_PAUSE_LOCK.notifyAll();
        }
    }

    public static void vibrate(int time) {
        v.vibrate(time);
    }

    @Override
    public void run() {

        double updateDurationMillis = 0;
        double sleepDurationMillis = 0, trueSleepDurationMillis = 0;

        while (running) {
            double beforeUpdateRender = System.nanoTime();
            double deltaMillis = (updateDurationMillis + trueSleepDurationMillis)/1000L;

            updateAndRender(deltaMillis);

            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);
            trueSleepDurationMillis = 17 - updateDurationMillis;

            try {
                Thread.sleep((long)sleepDurationMillis); //Thread.sleep o gameThread.sleep
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (M_PAUSE_LOCK) {
                while (mPaused) {
                    try {
                        M_PAUSE_LOCK.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

        }
    }
}
