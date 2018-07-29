package com.jorgegallego.jumpingsquare.main;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jorge y Juan on 11/07/2016.
 */
public class Assets {

    private static SoundPool soundPool;

    private static AssetManager am = GameMainActivity.sGame.getActivity().getApplicationContext().getAssets();
    public static Typeface titlesFont,  subTitlesFont, textFont, currencyFont, testFont;
    public static int song, bounce, bounce2, evilLaugh, hit, click, greySelectT;
    public static Bitmap background, background2, background3, background4, background5, background6,
            playButton, playButtonDown,homeButton, homeButtonDown, shopButton, shopButtonDown, skinButton,
            skinButtonDown, musicButton, musicButtonDown, cross, larrow, rarrow, larrowDown, rarrowDown,
            lessLifeMageUp,
            moreRedBallsImage, moreBallsImage, boxButton, boxButtonDown, transpBG,
            platRed, heart, mage1, mage1hurt, lock, spikeImage,
            plataform, blackBall, redBall,
            cloud1, cloud2, cloud3, cloud4,
            squaredy, squaredyJ;
    public static int[] oColor, iColor, fColor;
    public static ArrayList<ArrayList<Bitmap>> faces;
    public static int iBoxColor, mageLifeFontColor, specialBolitaOutColor, backgroundColor, bolitaInColor, normalBolitaOutColor, backgroundScoreColor, bestScoreColor, ballBorderColor, ballInsideColor, plataformColor, menuTextColor;

    public static void load() {
        background = loadBitmap("backgrounds/BG1.gif", false);
        background2 = loadBitmap("backgrounds/BG2.png", false);
        background3 = loadBitmap("backgrounds/BG3.png", false);
        background4 = loadBitmap("backgrounds/BG4.png", false);
        background5 = loadBitmap("backgrounds/BG5.png", false);
        background6 = loadBitmap("backgrounds/BG6.png", false);

        titlesFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "pricedown.ttf"));
        subTitlesFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "RobotoCondensed-Regular.ttf"));
        textFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "RobotoCondensed-Bold.ttf"));
        currencyFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Roboto-Black.ttf"));
        testFont = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "MMRock9.ttf"));

        bounce = loadSound("sound/jump3.wav");
        bounce2 = loadSound("sound/jump3.wav");
        evilLaugh = loadSound("sound/Evil_Laugh.mp3");
        hit = loadSound("sound/hit.mp3");
        song = loadSound("sound/Song.mp3");
        click = loadSound("sound/click.wav");

        playButton = loadBitmap("buttons/PlayButton.png", true);
        playButtonDown = loadBitmap("buttons/PlayButton2.png", true);
        shopButton = loadBitmap("buttons/ShopButton.png", true);
        shopButtonDown = loadBitmap("buttons/ShopButton2.png", true);
        skinButton = loadBitmap("buttons/SkinButton.png", true);
        skinButtonDown = loadBitmap("buttons/SkinButton2.png", true);
        homeButton = loadBitmap("buttons/HomeButton.png", true);
        homeButtonDown = loadBitmap("buttons/HomeButton2.png", true);
        musicButton = loadBitmap("buttons/MusicButton.png", true);
        musicButtonDown = loadBitmap("buttons/MusicButton2.png", true);
        cross = loadBitmap("buttons/CrossOutLine.png", true);
        larrow = loadBitmap("buttons/larrow.gif", true);
        rarrow = loadBitmap("buttons/rarrow.gif", true);
        larrowDown = loadBitmap("buttons/larrowDown.gif", true);
        rarrowDown = loadBitmap("buttons/rarrowDown.gif", true);
        lessLifeMageUp = loadBitmap("buttons/lessLifeMage3.gif", true);

        transpBG = loadBitmap("buttons/Skins/transparentBackground.png", false);
        oColor = new int[] {Color.BLACK, new Color().rgb(248, 63, 63), new Color().rgb(248, 121, 63), new Color().rgb(238, 255, 50), new Color().rgb(71, 255, 50), new Color().rgb(50, 255, 179), new Color().rgb(50, 98, 255), new Color().rgb(153, 50, 255), new Color().rgb(255, 50, 159),
                Color.RED, Color.CYAN, Color.GREEN, Color.CYAN, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.GREEN,
                Color.RED, Color.CYAN, Color.GREEN, Color.CYAN, Color.GREEN};
        iColor = new int[] {Color. WHITE, new Color().rgb(248, 63, 63), new Color().rgb(248, 121, 63), new Color().rgb(238, 255, 50), new Color().rgb(71, 255, 50), new Color().rgb(50, 255, 179), new Color().rgb(50, 98, 255), new Color().rgb(153, 50, 255), new Color().rgb(255, 50, 159),
                Color.RED, Color.CYAN, Color.GREEN, Color.CYAN, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.GREEN,
                Color.RED, Color.CYAN, Color.GREEN, Color.CYAN, Color.GREEN};

        moreRedBallsImage = loadBitmap("buttons/MoreRedBallsUpgrade.png", true);
        moreBallsImage = loadBitmap("buttons/MoreBallsButton.png", true);
        boxButton = loadBitmap("buttons/boxButton.png", true);
        boxButtonDown = loadBitmap("buttons/BoxButton2.png", true);

        platRed = loadBitmap("mysc/PlatRed.png", true);
        heart = loadBitmap("mysc/HeartB.png", true);
        mage1 = loadBitmap("mages/DefaultMage7.png", true);
        mage1hurt = loadBitmap("mages/DefaultMage5_hurt.png", true);
        lock = loadBitmap("mysc/lock.png", true);
        spikeImage = loadBitmap("mysc/Spike.gif", true);

        plataform = loadBitmap("platforms/EPlat.gif", true);
        blackBall = loadBitmap("balls/blackBall.png", true);
        redBall = loadBitmap("balls/RedBall.png", true);

        cloud1 = loadBitmap("clouds/cloudBorder.png", true);
        cloud2 = loadBitmap("clouds/cloud2.png", true);
        cloud3 = loadBitmap("clouds/cloud3.png", true);
        cloud4 = loadBitmap("clouds/cloud4.png", true);

        squaredy = loadBitmap("square/squaredy1.gif", true);
        squaredyJ = loadBitmap("square/squaredy2.gif", true);

        greySelectT = new Color().argb(200, 120, 120, 120);
        iBoxColor = new Color().rgb(104, 226, 216);
        mageLifeFontColor = new Color().rgb(0, 0, 0);
        specialBolitaOutColor = new Color().rgb(255, 0, 0);
        backgroundColor = new Color().rgb(120, 190, 240);//(44, 199, 199); //WHITE
        bolitaInColor = new Color().rgb(255, 255, 255);
        normalBolitaOutColor = new Color().rgb(0, 0, 0);
        backgroundScoreColor = new Color().rgb(0, 0, 0);
        bestScoreColor = new Color().rgb(255, 255, 255); //playState
        ballBorderColor = new Color().rgb(0, 0, 0);   //BLACK
        ballInsideColor = new Color().rgb(255, 255, 255); //WHITE
        plataformColor = new Color().rgb(0, 0, 0);        //BLACK
        menuTextColor = new Color().rgb(255, 150, 150);
    }

    private static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Options options = new Options();
        if(transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        if (bitmap != null){
            System.out.println(filename + " loaded");
        }
        return bitmap;
    }

    private static int loadSound(String filename) {
        int soundID = 0;
        if (soundPool == null) {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);

        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
        } catch (IOException e){
            e.printStackTrace();
        }
        return soundID;
    }

    public static int playSound(int soundID, int loop) {
        if (GameMainActivity.retreiveMusicMode()) {
            return soundPool.play(soundID, 1, 1, 1, loop, 1);
        } else {
            return 0;
        }
    }

    public static void stopSound(int soundID) {
        soundPool.stop(soundID);
    }
}
