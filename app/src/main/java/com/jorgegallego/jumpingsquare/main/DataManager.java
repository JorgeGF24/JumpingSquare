package com.jorgegallego.jumpingsquare.main;

/**
 * Created by Jorge y Juan on 12/07/2016.
 */
public class DataManager {

    private static int bestScore = GameMainActivity.retreiveBestScore(), currentScore;
    private static int currency = GameMainActivity.retrieveCurrency();

    public static void changeCurrentScore(int change) {
        currentScore += change;
        if (currentScore > bestScore) {
            bestScore = currentScore;
            GameMainActivity.setBestScore(bestScore);
        }
    }

    public static void save() {
        if (currentScore > bestScore) {
            bestScore = currentScore;
            GameMainActivity.setBestScore(bestScore);
        }
        GameMainActivity.saveCurrencyProg(currency);
    }

    public static void changeCurrency(int change) {
        currency += change;
    }

    public static void resetScore() {
        currentScore = 0;
    }

    public static int getCurrentScore() {
        return currentScore;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static int getCurrency() {
        return currency;
    }

}
