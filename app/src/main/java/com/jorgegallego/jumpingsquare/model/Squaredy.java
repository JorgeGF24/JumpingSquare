package com.jorgegallego.jumpingsquare.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;


public class Squaredy {
	final float Y_CONSTANT = GameMainActivity.GAME_HEIGHT/1280f;
	
	private float x, y, velY, velX, apparentVelY, pausedVelY, pausedVelX, pausedApparentVelY, jumpHeight = GameMainActivity.GAME_HEIGHT, platX, platXWidth; /*bouncePeriodCountdown*/;
	private int width, height, borderSize;
	private Rect rect;
	private int gravity = 1700 * GameMainActivity.GAME_HEIGHT /1000;
	private boolean steady = true;
	private int oColor = Assets.oColor[GameMainActivity.retrieveOutColor()], inColor = Assets.iColor[GameMainActivity.retrieveInColor()];
	private Bitmap squaredy, squaredyJ;
	
	public Squaredy(float x, float y, int height, int width, int borderSize) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.borderSize = borderSize;
		
		velY = 0;
		velX = 0;
		
		rect = new Rect((int)x, (int) y, (int)x + width, (int)y + height);

		squaredy = Assets.squaredy;
		squaredyJ = Assets.squaredyJ;
	}
	
	public void update(double delta) {
		if (!steady && y > GameMainActivity.GAME_HEIGHT/2) {
			velY += gravity * delta;
			apparentVelY = velY;
		} else if (!steady){
			apparentVelY += gravity * delta;
			velY = 0;
			if (apparentVelY > 0) {
				velY = apparentVelY;
			}
		}
        /*
        if (bouncePeriod) {
            bouncePeriodCountdown -= delta;
            if (bouncePeriodCountdown < 0) {
                bouncePeriod = false;
            }
        }
        */

		y += velY * delta;
		x += velX * delta;

		if (y > jumpHeight && x + width - 5 > platX && x + 5 < platXWidth){
			jumpHeight = GameMainActivity.GAME_HEIGHT + 50;
			platX = 0;
			platXWidth = 0;
			jump();
		}
		
		if (x + width < 0){
			x = GameMainActivity.GAME_WIDTH;
            //velX = 300;
            //bouncePeriod = true;
            //bouncePeriodCountdown = 0.3f;
		} else if (x /* + width */ > GameMainActivity.GAME_WIDTH) {
			/*velX = -300;
            bouncePeriod = true;
            bouncePeriodCountdown = 0.3f;
            */
            x = 0 - width;
		}
		
		updateRect();
	}

	public void render(Painter g) {
		/*if (velY > 0) {
			g.drawImage(squaredy, (int) x, (int) y, width, height);
		} else {

			g.drawImage(squaredyJ, (int) x, (int) y, width, height);
		}*/
		g.setColor(oColor);
		g.fillRoundedRect((int) x, (int) y , width, height, 10);
		g.setColor(inColor);
		g.fillRect((int) x + borderSize, (int) y + borderSize, width - borderSize*2, height - borderSize*2);
	}
	
	private void updateRect() {
		rect.set(Math.round(x), (int)y, width + (int) x, height + (int) y);
	}
	
	public void jump() {
		if (steady) {
			steady = false;
		}
		velY = -900 * GameMainActivity.GAME_HEIGHT /1000;

		if (RandomNumberGenerator.getRandInt(2) == 1) {
			Assets.playSound(Assets.bounce2, 0);
		} else {
			Assets.playSound(Assets.bounce2, 0);
		}

	}

	public void setJumpHeight(int height, int platX, int platXWidth){
		this.platX = platX;
		this.platXWidth = platXWidth;
		jumpHeight = height;
	}

	public void onTouch(boolean left) {
		if (left) {
			velX = -500 * GameMainActivity.GAME_WIDTH/780f;
		} else {
			velX = 500 * GameMainActivity.GAME_WIDTH/780f;
		}
	}

	public void onPause() {
		pausedApparentVelY = apparentVelY;
		pausedVelX = velX;
		pausedVelY = velY;
		velY = 0;
		velX = 0;
		apparentVelY = 0;
	}

	public void onResume() {
		apparentVelY = pausedApparentVelY;
		velX = pausedVelX;
		velY = pausedVelY;
	}

	public void onTouchRelease(){
		velX /= 4;
	}

	public int getY() {
		return Math.round(y);
	}
	
	public int getX() {
		return Math.round(x);
	}
	
	public int getVelY() {
		return Math.round(apparentVelY);
	}

	public Rect getRect() {
		return rect;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	/*public void onSensorChanged(SensorEvent sensorEvent) {
        //if (!bouncePeriod) {
            velX = Math.round(-200 * sensorEvent.values[0]);
        //}
	}*/
}
