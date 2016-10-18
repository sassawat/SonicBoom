package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Platform extends GameObject {

	private boolean toDestroy;
	private boolean destroyed;

	public Platform(GameScreen game, MapObject object) {
		super(game, object);
	}

	@Override
	public void customizeObject() {
		// whole platform box
		body.setType(BodyType.KinematicBody);
		body.getFixtureList().get(0).setSensor(true);

		// suface of platform
		FixtureDef fdef = new FixtureDef();
		EdgeShape shape = new EdgeShape();

		Vector2 v1 = new Vector2(-width / 2 + 2 / SonicBoom.PPM, height / 2);
		Vector2 v2 = new Vector2(width / 2 - 2 / SonicBoom.PPM, height / 2);

		shape.set(v1, v2);
		fdef.shape = shape;
		fdef.filter.categoryBits = SonicBoom.PLATFORM_BIT;

		body.createFixture(fdef).setUserData(this);
	}

	@Override
	public void update(float delta) {

		// if not destroyed
		if (!destroyed) {
			// update position
			x = body.getPosition().x - width / 2;
			y = body.getPosition().y - height / 2;
		}

		// set to destroy if out of map
		if (y < 0) {
			toDestroy = true;
		}

		// destroy if set to destroy
		if (toDestroy && !destroyed) {
			world.destroyBody(body);
			destroyed = true;
		}
	}

	@Override
	public void hit() {
		// make it fall
		if (game.player.body.getLinearVelocity().y < 0) {
			body.setLinearVelocity(0, -0.5f);
		}
	}

	@Override
	public void draw(Batch batch) {
		if (!destroyed)
			super.draw(batch);
	}

}
