package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Item extends Sprite {

	protected GameScreen game;
	protected World world;

	protected Body body;
	protected Fixture fixture;

	protected BodyDef bdef;
	protected FixtureDef fdef;

	protected boolean toDestroy;
	protected boolean destroyed;

	protected boolean toSpawn;

	protected float destroyedTime;

	public Item(GameScreen game, BodyDef bdef, FixtureDef fdef, boolean toSpawn) {
		this.game = game;
		this.world = game.getWorld();

		this.bdef = bdef;
		this.fdef = fdef;

		this.toSpawn = toSpawn;
	}

	public Item(GameScreen game, Body body, Fixture fixture) {
		this.game = game;
		this.world = game.getWorld();

		this.body = body;
		this.fixture = fixture;

		defineItem();
	}

	public abstract void defineItem();

	public abstract void hit();

	public void update(float delta) {
		if (toDestroy && !destroyed) {
			world.destroyBody(body);
			destroyed = true;
		}

		if (toSpawn) {
			this.body = world.createBody(bdef);
			this.fixture = body.createFixture(fdef);

			defineItem();

			toSpawn = false;
		}

		if (destroyed) {
			destroyedTime += delta;
		}

		setPosition(body.getPosition().x, body.getPosition().y);
	}

	public void draw(Batch batch) {
		if (!destroyed)
			super.draw(batch);
	}

	public void destroy() {
		toDestroy = true;
	}

	public float getDestroyedTime() {
		return destroyedTime;
	}

}
