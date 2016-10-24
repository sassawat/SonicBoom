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
	protected float lifeTime;

	protected float splashTime;

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
		// destroy item
		if (toDestroy && !destroyed) {
			world.destroyBody(body);
			destroyed = true;
		}

		// spawn item
		if (toSpawn) {
			this.body = world.createBody(bdef);
			this.fixture = body.createFixture(fdef);

			defineItem();

			toSpawn = false;
		}

		// dead time
		if (destroyed) {
			destroyedTime += delta;
		}

		// life time
		if (lifeTime > 0) {
			splash(1, 0.05f, delta);
			lifeTime -= delta;
		}
		if (lifeTime < 0) {
			destroy();
		}

		// update postion of sprite
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

	public void setLifeTime(float time) {
		this.lifeTime = time;
	}

	public void splash(float time, float freq, float delta) {
		if (lifeTime <= time) {
			if (splashTime > freq) {
				setAlpha(0);
				splashTime = 0;
			} else {
				setAlpha(1);
			}
			
			splashTime += delta;
		}
	}
}
