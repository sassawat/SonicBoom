package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Spring extends GameObject {

	private TextureRegion idle;
	private TextureRegion expand;
	private TextureRegion expanded;

	private Animation animation;
	private float stateTime;
	private boolean play;

	private Vector2 force;

	public Spring(GameScreen game, MapObject object) {
		super(game, object);

		idle = game.getMap().getTileSets().getTile(183).getTextureRegion();
		expand = game.getMap().getTileSets().getTile(184).getTextureRegion();
		expanded = game.getMap().getTileSets().getTile(185).getTextureRegion();

		animation = new Animation(0.05f, idle, expand, expanded);

		force = new Vector2(0.25f, 0).rotate(-rotation);
	}

	@Override
	public void customizeObject() {
		// create bump surface
		FixtureDef fdef = new FixtureDef();
		EdgeShape eShape = new EdgeShape();

		Vector2 v1 = new Vector2(width / 2, height / 2 - 2 / SonicBoom.PPM);
		Vector2 v2 = new Vector2(width / 2, -height / 2 + 2 / SonicBoom.PPM);

		eShape.set(v1, v2);
		fdef.shape = eShape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = SonicBoom.OBJECT_BIT;

		body.createFixture(fdef).setUserData(this);
	}

	@Override
	public void hit() {
		// bump it!
		game.player.body.setLinearVelocity(0, 0);
		game.player.body.applyLinearImpulse(force, game.player.body.getWorldCenter(), true);

		// trigger animation
		play = true;
	}

	@Override
	public void update(float delta) {
		if (play) {
			stateTime += delta;
			if (stateTime >= 0.3f) {
				play = false;
				stateTime = 0;
			}
		}
		textureObject.setTextureRegion(animation.getKeyFrame(stateTime));
	}

}
