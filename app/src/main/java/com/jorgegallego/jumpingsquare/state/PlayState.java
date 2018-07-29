package com.jorgegallego.jumpingsquare.state;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import java.util.ArrayList;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.DataManager;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameView;
import com.jorgegallego.jumpingsquare.model.Bolitas;
import com.jorgegallego.jumpingsquare.model.Mages.Mage;
import com.jorgegallego.jumpingsquare.model.Mages.PlatDestroyer;
import com.jorgegallego.jumpingsquare.model.Mages.SpikeLeaver;
import com.jorgegallego.jumpingsquare.model.Plataform;
import com.jorgegallego.jumpingsquare.model.Squaredy;

public class PlayState extends State {
	private final int GAME_WIDTH = GameMainActivity.GAME_WIDTH;
	private final int GAME_HEIGHT = GameMainActivity.GAME_HEIGHT;

	private Squaredy ball;
	private final int BALL_DIAMETER = Math.round(55 * Y_CONSTANT);
	private final int BORDER_SIZE = Math.round(11 *Y_CONSTANT);

	private final int[] PLATAFORM_WIDTH_RANGE = new int[]{Math.round(106*X_CONSTANT), Math.round(185*X_CONSTANT)};// inclusive both //lower and upper bounds of plataform width
	private final int PLATAFORM_HEIGHT = Math.round(25*Y_CONSTANT), PLAT_SEPARATION_DISTANCE = Math.round(119*Y_CONSTANT);
	private ArrayList<ArrayList<Plataform>> plataforms;
	private ArrayList<Integer> levelsDoublePlat = new ArrayList<>();
	private int platNumOfLevels;

	private boolean hasStarted = false;

	private int objectsDownVel;

	private int greyIncrement = 4, greyNumber = 80;

	private int score, currency;

	private Bolitas[] bolitas;
	private final int NUMBER_OF_BOLITAS = 2 + GameMainActivity.retrieveUpLvl("MBLVL");
	private int waitingBalls, waitingBallNum, blackBallScore = 1, redBallScore = 3;

    private boolean readyToJump;
    private Plataform jumpPlat;

	//variables needed for removing platforms
	private double startRemovingTime = 0;
	private boolean removeProcessOn = false, crossesVisible = false;
	private int firstRemPlatNum, secondRemPlatNum, thirdRemPlatNum;

	//private final int NUM_OF_CLOUDS = 2;
	//private Cloud[] clouds = new Cloud[NUM_OF_CLOUDS];

	private Mage mage;
	private int mageHP = 55 - GameMainActivity.retrieveUpLvl("LLMLVL") * 5;
	private String spikerID = "Spiker", platDestroyerID = "Platform destroyer";

	private int song, songID;

	@Override
	public void init() {
		currency = DataManager.getCurrency();

		ball = new Squaredy((GAME_WIDTH - BALL_DIAMETER) / 2, GAME_HEIGHT * 0.85f - BALL_DIAMETER, BALL_DIAMETER, BALL_DIAMETER, BORDER_SIZE);
		bolitas = new Bolitas[NUMBER_OF_BOLITAS];

        DataManager.resetScore();
		score = 0;

		initPlataforms();

		int o = RandomNumberGenerator.getRandInt(5);

		for (int i = 0; i < NUMBER_OF_BOLITAS; i++) {
			bolitas[i] = new Bolitas(plataforms.get(o + i).get(0).getX(), plataforms.get(o + i).get(0).getWidth(), plataforms.get(o + i).get(0));
		}

		initClouds();

		GameMainActivity.playMusic(true);

		if (RandomNumberGenerator.getRandInt(2) == 1) {
			mage = new PlatDestroyer(Assets.mage1, Assets.mage1hurt, (int) (280 * X_CONSTANT), (int) (400 * X_CONSTANT),
					X_CONSTANT, mageHP, this, platDestroyerID, plataforms, platNumOfLevels, levelsDoublePlat);
		} else {
			mage = new SpikeLeaver(Assets.mage1, Assets.mage1hurt, (int) (280 * X_CONSTANT), (int) (400 * X_CONSTANT), X_CONSTANT,
					mageHP, ball, this, spikerID);
		}

		mage.enter();

		GameMainActivity.sGame.playOn(true);
	}

	private void initPlataforms() {
		plataforms = new ArrayList<>();
		for (int i = 0; i < GAME_HEIGHT / PLAT_SEPARATION_DISTANCE + 1; i++) {
			ArrayList<Plataform> currentArray = new ArrayList<>();
			Plataform currentPlataform = new Plataform(i * PLAT_SEPARATION_DISTANCE, PLATAFORM_WIDTH_RANGE, PLATAFORM_HEIGHT, X_CONSTANT);
			currentArray.add(currentPlataform);

			if (RandomNumberGenerator.getRandInt(4) == 1) {
				Plataform currentPlataform2 = new Plataform(i * PLAT_SEPARATION_DISTANCE, PLATAFORM_WIDTH_RANGE, PLATAFORM_HEIGHT, X_CONSTANT);
				currentArray.add(currentPlataform2);
				levelsDoublePlat.add(i);
			}
			plataforms.add(currentArray);
			platNumOfLevels++;
		}
	}

	private void initClouds() {
		int[] bounds = new int[]{Math.round(140*X_CONSTANT), Math.round(260*X_CONSTANT)};
		//for(int i = 0; i < NUM_OF_CLOUDS; i++) {
		//	Cloud currentCloud = new Cloud(bounds);
		//	clouds[i] = currentCloud;
		//}
	}

	@Override
	public void update(double delta) {

		ball.update(delta);

		checkIfDead();

		//sets plataform/bolitas vel to the balls vel (comprobar si bola ha llegado al centro) 
		if (hasStarted && ball.getY() <= GAME_HEIGHT / 2 && ball.getVelY() < 0) {
			objectsDownVel = ball.getVelY();
		}

		mage.update(delta);

		if (hasStarted) {
			for (int i = 0; i < NUMBER_OF_BOLITAS; i++) {
				if (bolitas[i].isTouching(ball.getRect()) && !bolitas[i].isFlyingToMage()) {
					bolitas[i].die(mage);
					score += bolitas[i].isSpecial()?redBallScore:blackBallScore;
					currency += bolitas[i].isSpecial()?redBallScore:blackBallScore;
					DataManager.changeCurrentScore(bolitas[i].isSpecial()?redBallScore:blackBallScore);
					DataManager.changeCurrency(bolitas[i].isSpecial()?redBallScore:blackBallScore);
				}
			}
		}

		//actualizar plataformas + comprobar si la bola esta tocando una y saltar  + o y bajar plataformas

		for (int i = 0; i < plataforms.size(); i++) {
			for (int a = 0; a < plataforms.get(i).size(); a++) {
				if (plataforms.get(i).get(a).getY() >= GAME_HEIGHT) {
					plataforms.get(i).get(a).reset();
					if ((waitingBalls > 0) && (RandomNumberGenerator.getRandInt(3) == 1)) {
						bolitas[waitingBallNum].reset(plataforms.get(i).get(a).getX(), plataforms.get(i).get(a).getWidth(), plataforms.get(i).get(a));
					}
				}
				plataforms.get(i).get(a).update(delta, objectsDownVel);

				if (Rect.intersects(plataforms.get(i).get(a).getRect(), ball.getRect()) && ball.getY() + BALL_DIAMETER <= plataforms.get(i).get(a).getY() + 12 * X_CONSTANT && ball.getVelY() > 120 * X_CONSTANT) {
					jumpPlat = plataforms.get(i).get(a); //.getY() - PLATAFORM_HEIGHT
                    readyToJump = true;
				}
			}
		}

		waitingBalls = 0;
		//actualizar bolitas
		for (int i = 0; i < NUMBER_OF_BOLITAS; i++) {
			bolitas[i].update(delta);
			if (bolitas[i].getWaiting()) {
				waitingBalls++;
				waitingBallNum = i;
			}
		}

		//actualizar nubes
		//for (int i = 0; i < NUM_OF_CLOUDS; i++) {
		//	clouds[i].update(delta, objectsDownVel);
		//}



        if (ball.getVelY() > 0 && readyToJump && jumpPlat.getY() >= ball.getY() + BALL_DIAMETER ) {
            readyToJump = false;
            ball.setJumpHeight(jumpPlat.getY() - 30, jumpPlat.getX(), jumpPlat.getX() + jumpPlat.getWidth());
			jumpPlat = null;
        }

		//if (removeProcessOn) {
		//	mage1.removePlats();
		//}
	}

	private void checkIfDead(){
		if (ball.getY() > GAME_HEIGHT + 20 * X_CONSTANT) {
			die();
		}
	}

	public void die() {
		DataManager.save();
		GameView.vibrate(750);
		GameMainActivity.stopMusic();
		GameMainActivity.sGame.playOn(false);
		setCurrentState(new GameOverState());
	}

	public void mageKilled(String mageID) {
		if (mageID == spikerID) {
			mage = new PlatDestroyer(Assets.mage1, Assets.mage1hurt, (int) (280 * X_CONSTANT), (int) (400 * X_CONSTANT),
					X_CONSTANT, mageHP, this, platDestroyerID, plataforms, platNumOfLevels, levelsDoublePlat);
		} else {
			mage = new SpikeLeaver(Assets.mage1, Assets.mage1hurt, (int) (280 * X_CONSTANT), (int) (400 * X_CONSTANT), X_CONSTANT,
					mageHP, ball, this, spikerID);
		}
		mage.enter();
	}

	@Override
	public void render(Painter g) {
		//fondo:
		g.setColor(Assets.backgroundColor);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		/*Score (big)
		Typeface font = Typeface.create("Arial", Typeface.BOLD);
		textSize = (int) ((370 - score * 2) * X_RATIO_CONSTANT);
		g.setFont(font, textSize);
		g.setColor(Assets.backgroundScoreColor);
		int textWidth = g.measureText("" + score, Math.round((370 - score * 2) * X_RATIO_CONSTANT));
		g.drawString("" + score, GAME_WIDTH / 2 - (textWidth / 2), Math.round(GAME_HEIGHT - 140/194));
		*/

		//score

		/*bestScore
		font = Typeface.create("Arial", Typeface.BOLD);
		g.setFont(font, 50 * X_RATIO_CONSTANT);
		g.setColor(Assets.bestScoreColor);
		textWidth = g.measureText("Best score: " + DataManager.getBestScore(),  Math.round(50 * X_RATIO_CONSTANT));
		g.drawString("Best score: " + DataManager.getBestScore(),(GAME_WIDTH / 2 - (textWidth / 2)), (int) Math.round(0.08 * GAME_HEIGHT));
		*/

		//for (int i = 0; i < NUM_OF_CLOUDS; i++) {
		//	clouds[i].render(g);
		//}

		mage.render(g);

		//Plataforms
		for (int i = 0; i < plataforms.size(); i++) {
			for (int a = 0; a < plataforms.get(i).size(); a++) {
				//g.fillRect(plataforms.get(i).get(a).getX(), plataforms.get(i).get(a).getY(), plataforms.get(i).get(a).getWidth(), plataforms.get(i).get(a).getHeight());
				plataforms.get(i).get(a).render(g);
				}
			}

		mage.renderExtras(g, X_CONSTANT);

		//squaredy
		ball.render(g);

		//bolitas
		for (int i = 0; i < NUMBER_OF_BOLITAS; i++) {
			bolitas[i].render(g);
		}

		//press-key-to-start text
		if (!hasStarted) {
			if (greyNumber <= 6 || greyNumber >= 180) {
				greyIncrement *= -1;
			}
			Typeface font = Typeface.create("Arial", Typeface.BOLD);
			greyNumber += greyIncrement;
            g.setFont(font, 30 * X_CONSTANT);
			g.setColor(Color.rgb(greyNumber, greyNumber, greyNumber));
			String startTextString = "Touch screen to start!";
			int textWidth = g.measureText(startTextString, Math.round(30 * X_CONSTANT));
			g.drawString(startTextString, (GAME_WIDTH / 2 - (textWidth / 2)), GAME_HEIGHT / 2 + 25);
		}

		Typeface font = Typeface.create("Arial", Typeface.BOLD);
		g.setFont(font, (int) (50 * X_CONSTANT));
		g.setColor(Assets.backgroundScoreColor);
		int textWidth = g.measureText("" + score, (int) (50 * X_CONSTANT));
		g.drawString("" + score, GAME_WIDTH / 2 - (textWidth / 2), (int) (100 * X_CONSTANT));
		g.setFont(font, (int) (30 * X_CONSTANT));
		textWidth = g.measureText("" + currency +"$", (int) (30 * X_CONSTANT));
		g.drawRect((int) (GAME_WIDTH - textWidth - 42 * X_CONSTANT), (int) (20 * X_CONSTANT),  (int) (textWidth + 22 * X_CONSTANT), (int) (80 * X_CONSTANT), (int) (10  * X_CONSTANT));
		g.drawString("" + currency + "$", (int) (GAME_WIDTH - textWidth - 32 * X_CONSTANT), (int) (80 * X_CONSTANT));
		//g.drawImage(Assets.blackBall, (int) (GAME_WIDTH - 65 * X_RATIO_CONSTANT), (int) (40 * X_RATIO_CONSTANT), (int) (35 * X_RATIO_CONSTANT), (int) (35 * X_RATIO_CONSTANT));
	}





	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
		switch(e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (scaledX < GAME_WIDTH / 2) {
					ball.onTouch(true);
				} else {
					ball.onTouch(false);
				}
                if (!hasStarted) {
                    ball.jump();
                    hasStarted = true;
                }
                return true;
			case MotionEvent.ACTION_UP:
				if (e.getActionMasked() == MotionEvent.ACTION_UP) {
					ball.onTouchRelease();
				}
                return true;
		}
        return false;
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		//if(hasStarted) {
		//	ball.onSensorChanged(sensorEvent);
		//}
	}

	public String name() {
		return "PlayState";
	}

	@Override
	public void onPause() {
		if (hasStarted) {
			ball.onPause();
		}

	}

	@Override
	public void onResume() {
		if (hasStarted) {
			ball.onResume();
		}
	}

	public void setRemoveProcessOn(boolean removeProcessOn) {
		this.removeProcessOn = removeProcessOn;
	}

	public void setCrossesVisible(boolean crossesVisible) {
		this.crossesVisible = crossesVisible;
	}
}
