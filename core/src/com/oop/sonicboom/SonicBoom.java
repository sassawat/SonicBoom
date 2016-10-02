package com.oop.sonicboom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SonicBoom extends Game {

	// Virtual size for game
	public static final int V_WIDTH = 288;
	public static final int V_HEIGHT = 216;
	
	// Pixel per Meter
	public static final float PPM = 100; 

	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		// setScreen(new GameScreen(this));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
