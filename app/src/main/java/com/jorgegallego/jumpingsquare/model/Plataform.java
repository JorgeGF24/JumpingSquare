package com.jorgegallego.jumpingsquare.model;

import android.graphics.Rect;

import com.jorgegallego.framework.util.Painter;
import com.jorgegallego.framework.util.RandomNumberGenerator;
import com.jorgegallego.jumpingsquare.main.Assets;
import com.jorgegallego.jumpingsquare.main.DataManager;
import com.jorgegallego.jumpingsquare.main.GameMainActivity;

public class Plataform {
	
	private int x, y, height, width, LWB, UPB;
    private final int PLAT_RECT_SEPARATION = 15; //margin
	private Rect rect;
	private boolean visible = true;

	private float X_CONSTANT;
	
	public Plataform(int y,int[] plataformWidthRange, int height, float X_CONSTANT) {
		LWB = plataformWidthRange[0];
		UPB = plataformWidthRange[1];
		width = RandomNumberGenerator.getRandIntBetween(LWB, UPB);
		x = RandomNumberGenerator.getRandIntBetween(0, GameMainActivity.GAME_WIDTH - width);
		this.y = y;
		this.height = height;
		
		rect = new Rect(x + (int) (5 * X_CONSTANT), y - PLAT_RECT_SEPARATION, width + x  - (int) (5 * X_CONSTANT) , height + y - PLAT_RECT_SEPARATION);

		this.X_CONSTANT = X_CONSTANT;
	}
	
	public void update(double delta, int newVelY) {
		int velY = (int) (newVelY * -1.5);
		y += (int) (velY * delta);
		if (visible) {
			rect.set(x + (int) (5 * X_CONSTANT), y - PLAT_RECT_SEPARATION, width + x - (int) (5 * X_CONSTANT), height + y - PLAT_RECT_SEPARATION);
		}
		
		//if (y > GameMainActivity.GAME_HEIGHT) {
		//	reset();
		//}
	}
	
	public void render(Painter g) {
		if (visible) {
			g.drawImage(Assets.plataform, x, y, width, height);
		}
	}
	
	public void reset() {
		y = 0 - height;
		int score = DataManager.getCurrentScore();
		if (score < 58) {
			width = RandomNumberGenerator.getRandIntBetween(LWB - score, UPB - score);
		} else {
			width = 20;
		}
		visible = true;

		x = RandomNumberGenerator.getRandIntBetween(0, GameMainActivity.GAME_WIDTH - width);
	}
	public void removed(boolean visible) {
		if (!visible) {
			rect.set(0, -50, width, height - 50);
		}
		this.visible = visible;
	}

	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Rect getRect() {
		return rect;
	}
}
