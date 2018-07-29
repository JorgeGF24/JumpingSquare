package com.jorgegallego.framework.util;

import java.util.Random;

public class RandomNumberGenerator {
	
	private static Random rand = new Random();
	
	public static int getRandIntBetween(int lowerBound, int upperBound) { // both inclusive
		return rand.nextInt(upperBound - lowerBound) + lowerBound;
	}
	
	public static int getRandIntBetweenNot(int lowerBound, int upperBound, int not, int not2) {//both inclusive
		int i = not;
		while (i == not || i == not2) {
			i = rand.nextInt(upperBound - lowerBound) + lowerBound;	
		}
		return i;
	}
	
	public static int getRandIntBetweenNot(int lowerBound, int upperBound, int not) {//both inclusive
		int i = not;
		while (i == not) {
			i = rand.nextInt(upperBound - lowerBound) + lowerBound;	
		}
		return i;
	}
	
	public static int getRandInt(int upperBound) {
		return rand.nextInt(upperBound) + 1; //between 1 and upperBound both inclusive
	}

	public static int getRandSign() {
		return getRandInt(2) == 2?1:-1;
	}
	
}
