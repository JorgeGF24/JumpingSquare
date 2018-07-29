package com.jorgegallego.jumpingsquare.model;

import android.graphics.Rect;
import com.jorgegallego.framework.util.Painter;

import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;
import com.jorgegallego.jumpingsquare.model.Mages.Mage;

public class Bolitas {

	private Plataform chosenPlat;
	
	private int x, y, length;
	private Rect ballRect;
	private boolean isSpecial, isWaiting, isFlyingToMage;
	private int dmgPoints, specialScore = 3, normalScore = 1;

	private int objX, objY;
	private double killVelX, killVelY;

	private int redBallInvProbability = 12 - GameMainActivity.retrieveUpLvl("RBLVL");

	private Mage targetMage;
	
	public Bolitas(int x, int platLength, Plataform plat) {
		chosenPlat = plat;

		length = (int) (RandomNumberGenerator.getRandIntBetween(30, 35) * GameMainActivity.GAME_WIDTH/720f);
		this.x = x + platLength/2 - length/2;
		ballRect = new Rect(x + 2, y + 2, length - 2 + x, length - 2 + y);

		if(RandomNumberGenerator.getRandInt(redBallInvProbability) == 1){
			isSpecial = true;
			dmgPoints = specialScore;
		} else {
			isSpecial = false;
			dmgPoints = normalScore;
		}

		isWaiting = false;
	}

	public void reset(int x, int platLength, Plataform plat) {
		chosenPlat = plat;
		isWaiting = false;

		length = (int) (RandomNumberGenerator.getRandIntBetween(30, 35) * GameMainActivity.GAME_WIDTH/720f);
		this.x = x + platLength/2 - length/2;
		y = -length * 2;
		ballRect = new Rect(x + 2, y + 2, length - 2 + x, length - 2 + y);

		if(RandomNumberGenerator.getRandInt(redBallInvProbability) == 1){
			isSpecial = true;
			dmgPoints = specialScore;
		} else {
			isSpecial = false;
			dmgPoints = normalScore;
		}

		killVelX = 0;
		killVelY = 0;

		isFlyingToMage = false;
	}
	
	public void render(Painter g) {
		if (isSpecial) {
			g.drawImage(Assets.redBall, x, y, length, length);
		} else {
			g.drawImage(Assets.blackBall, x, y, length, length);
		}
	}
	
	public boolean isTouching(Rect squaredy) {
        return Rect.intersects(squaredy, ballRect);
    }
	
	public boolean isSpecial() {
		return isSpecial;
	}

	public void update(double delta) {
		if (isFlyingToMage) { //if isFlyingToMage

			objX = targetMage.getX() + targetMage.getWidth() / 4;
			objY = targetMage.getY() + targetMage.getHeight() / 4;

			if (((objX - x) * ((objX - x > 0) ? +1 : -1) > 20 * GameMainActivity.GAME_HEIGHT / 1000) && ((objY - y) * ((objY - y > 0) ? +1 : -1) > 20 * GameMainActivity.GAME_HEIGHT / 1000)) {
				killVelX = (objX - x) * 8 * GameMainActivity.GAME_HEIGHT / 1000;
				killVelY = (objY - y) * 4 * GameMainActivity.GAME_HEIGHT / 1000;
			} else {
				killVelX = 500 * GameMainActivity.GAME_HEIGHT / 1000 * ((objX - x > 0) ? +1 : -1);
				killVelY = 300 * GameMainActivity.GAME_HEIGHT / 1000 * ((objY - y > 0) ? +1 : -1);
			}

			x += killVelX * delta;
			y += killVelY * delta;

			if (((objX - x) * ((objX - x > 0) ? +1 : -1) < 50) && ((objY - y) * ((objY - y > 0) ? +1 : -1) < 20)) {
				setToWaiting();
				isFlyingToMage = false;
				targetMage.whenHit(dmgPoints);
			}
		} else if (!isWaiting) {
			if (chosenPlat.getY() < y) {
				//reset();
				setToWaiting();
			}

			y = chosenPlat.getY() - length * 2;
			ballRect.set(x + 2, y + 2, length + x - 2, length + y - 2);

		}
	}

	public boolean getWaiting() { return isWaiting;}

	private void setToWaiting() {
		isWaiting = true;
		y = -50;
		ballRect = new Rect(x + 2, y + 2, length - 2 + x, length - 2 + y);
	}

	public void die(Mage mage){
		isFlyingToMage = true;
		killVelX = (objX - x) * 4 * GameMainActivity.GAME_HEIGHT/1000;
		killVelY = (objY - y) * 2 * GameMainActivity.GAME_HEIGHT/1000;

		targetMage = mage;
	}

	public boolean isFlyingToMage() {
		return isFlyingToMage;
	}
}
