package com.oop.sonicboom;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Spike extends GameObject {

	public Spike(GameScreen game, MapObject object) {
		super(game, object);
	}

	@Override
	public void customizeObject() {
		FixtureDef fdef = new FixtureDef();
		EdgeShape shape = new EdgeShape();

		Vector2 v1 = new Vector2(-width / 2 + 2 / SonicBoom.PPM, height / 2);
		Vector2 v2 = new Vector2(width / 2 - 2 / SonicBoom.PPM, height / 2);

		shape.set(v1, v2);
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = SonicBoom.OBJECT_BIT;

		body.createFixture(fdef).setUserData(this);
	}

	@Override
	public void hit() {
		pushBack(game.player, 0.125f, 0.2f);
		game.player.hurt(1);
		game.player.loseRing(GameScorer.clearScore());
	}

	private void pushBack(Player player, float vx, float vy) {
		Vector2 velocity = player.body.getLinearVelocity();
		Vector2 forceBack = new Vector2(velocity.x <= 0 ? vx : -vx, vy);
		player.body.setLinearVelocity(0, 0);
		player.body.applyLinearImpulse(forceBack, player.body.getWorldCenter(), true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
