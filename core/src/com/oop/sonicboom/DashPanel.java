package com.oop.sonicboom;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;

public class DashPanel extends GameObject {

	public DashPanel(GameScreen game, MapObject object) {
		super(game, object);
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
		game.player.body.applyLinearImpulse(new Vector2(0.25f, 0), game.player.body.getWorldCenter(), true);
	}

}
