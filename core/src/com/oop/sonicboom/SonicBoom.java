package com.oop.sonicboom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SonicBoom extends Game {

	// Virtual size for game
	public static final int V_WIDTH = 288;
	public static final int V_HEIGHT = 216;

	// Pixel per Meter
	public static final float PPM = 100;

	// Fixture Mask BIT
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short RING_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short PLATFORM_BIT = 32;
	public static final short LOOP_SWITCH_BIT = 64;
	public static final short LOOP_R_BIT = 128;
	public static final short LOOP_R_SENSOR_BIT = 256;
	public static final short LOOP_L_BIT = 512;
	public static final short LOOP_L_SENSOR_BIT = 1024;

	// used by all screens
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
