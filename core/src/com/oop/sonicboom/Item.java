package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Item extends Sprite {

	protected GameScreen game;
	protected World world;

	protected Body body;
	protected Fixture fixture;

	protected boolean toDestroy;
	protected boolean destroyed;

	public Item(GameScreen game, Body body, Fixture fixture) {
		this.game = game;
		this.world = game.getWorld();

		this.body = body;
		this.fixture = fixture;

		toDestroy = false;
		destroyed = false;

		defineItem();
	}

	public abstract void defineItem();

	public abstract void hit();

	public void update(float delta) {
		if (toDestroy && !destroyed) {
			world.destroyBody(body);
			destroyed = true;
		}
	}

	public void draw(Batch batch) {
		if (!destroyed)
			super.draw(batch);
	}

	public void destroy() {
		toDestroy = true;
	}

}
