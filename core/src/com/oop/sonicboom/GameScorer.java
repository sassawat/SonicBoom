package com.oop.sonicboom;

import com.badlogic.gdx.utils.TimeUtils;

public class GameScorer {

	private static long startTime;
	private static long startPauseTime;
	private static long pauseTime;
	private static long timeCount;

	private static int score;

	public static void start() {
		startTime = TimeUtils.millis();

	}

	public static void pause() {
		startPauseTime = TimeUtils.millis();
	}

	public static void reseume() {
		pauseTime += TimeUtils.timeSinceMillis(startPauseTime);
	}

	public static void endGame() {
		GameScreen.forceGameOver();
	}

	public static void reset() {
		startTime = TimeUtils.millis();
		pauseTime = 0;
		timeCount = 0;
		score = 0;
	}

	public static void addScore(int value) {
		if (!GameScreen.isGameOver()) {
			score += value;
		}
	}

	public static int clearScore() {

		if (score == 0) {
			endGame();
			return 0;
		}

		int tempScore = score;
		score = 0;
		return tempScore;
	}

	public static int getScore() {
		return score;
	}

	public static long getTimeCount() {
		if (!GameScreen.isGameOver()) {
			timeCount = TimeUtils.timeSinceMillis(startTime) - pauseTime;
		}
		return timeCount;
	}

}
