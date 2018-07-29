package com.jorgegallego.framework.util;

import android.graphics.Bitmap;
import com.jorgegallego.framework.util.Frame;

public class Animation {
	
	private double[] frameEndTimes;
	private Frame[] frames;
	private int currentFrameIndex = 0;
	
	private double totalDuration;
	private double currentTime = 0;
	
	public Animation(Frame...frames) {
		this.frames = frames;
		frameEndTimes = new double[frames.length];
		
		for(int i = 0; i < frames.length; i++) {
			Frame f = frames[i];
			totalDuration += f.getDuration();
			frameEndTimes[i] = totalDuration;
		}
	}
	
	public synchronized void update(float increment) {
		
		currentTime += increment;
		
		if (currentTime > totalDuration) {
			wrapAnimation();
		}
		
		while (currentTime > frameEndTimes[currentFrameIndex]) {
			currentFrameIndex++;
		}
		
	}
	
	private synchronized void wrapAnimation() {
		currentFrameIndex = 0;
		currentTime %= totalDuration;
	}
	
	public synchronized void render(Painter g, int x, int y) {
		g.drawImage(frames[currentFrameIndex].getImage(), x, y);
	}
		
	public synchronized void render(Painter g, int x, int y, int width, int height) {
		g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
	}
	

}
