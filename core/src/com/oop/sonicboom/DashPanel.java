package com.oop.sonicboom;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;

public class DashPanel extends GameObject {

	private Vector2 force;

	public DashPanel(GameScreen game, MapObject object) {
		super(game, object);

		force = new Vector2(0.25f, 0).rotate(-rotation);
	}

	@Override
	public void customizeObject() {
		Filter filter = new Filter();
		filter.categoryBits = SonicBoom.OBJECT_BIT;
		body.getFixtureList().get(0).setFilterData(filter);
		body.getFixtureList().get(0).setSensor(true);
		body.getFixtureList().get(0).setUserData(this);
	}

	@Override
	public void hit() {
		// speed up
		game.player.body.applyLinearImpulse(force, game.player.body.getWorldCenter(), true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
