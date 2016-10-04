package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Ring extends Item {

	private Animation animation;
	private float stateTimer;

	public Ring(GameScreen game, Body body, Fixture fixture, Animation animation) {
		super(game, body, fixture);
		this.animation = animation;
	}

	@Override
	public void defineItem() {
		fixture.setUserData(this);

		setBounds(0, 0, 16 / SonicBoom.PPM, 16 / SonicBoom.PPM);
		setPosition(body.getPosition().x, body.getPosition().y);
	}

	@Override
	public void hit() {
		// add Ring /score
		destroy();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		setRegion(animation.getKeyFrame(stateTimer, true));
		stateTimer += delta;
	}

}
