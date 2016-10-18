package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject {

	protected GameScreen game;
	protected World world;
	protected MapObject object;

	protected Body body;

	protected TextureMapObject textureObject;

	protected float rotation;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public GameObject(GameScreen game, MapObject object) {
		this.game = game;
		this.world = game.getWorld();
		this.object = object;

		this.textureObject = (TextureMapObject) object;

		defineObject();
		customizeObject();
	}

	private void defineObject() {
		TextureMapObject textureMapObject = (TextureMapObject) object;

		rotation = textureMapObject.getRotation();

		x = textureMapObject.getX() / SonicBoom.PPM;
		y = textureMapObject.getY() / SonicBoom.PPM;

		width = textureMapObject.getProperties().get("width", Float.class).floatValue() / SonicBoom.PPM;
		height = textureMapObject.getProperties().get("height", Float.class).floatValue() / SonicBoom.PPM;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		body = world.createBody(bdef);

		applyTiledLocationToBody(body, x, y, width, height, rotation);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		fdef.shape = shape;

		body.createFixture(fdef);
	}

	private void applyTiledLocationToBody(Body body, float x, float y, float width, float height, float rotation) {
		// set body position taking into consideration the center position
		body.setTransform(x + width / 2, y + height / 2, 0);

		// bottom left position in local coordinates
		Vector2 localPosition = new Vector2(-width / 2, -height / 2);

		// save world position before rotation
		Vector2 positionBefore = body.getWorldPoint(localPosition).cpy();

		// calculate angle in radians
		float angle = -rotation * MathUtils.degreesToRadians;

		// set new angle
		body.setTransform(body.getPosition(), angle);

		// save world position after rotation
		Vector2 positionAfter = body.getWorldPoint(localPosition).cpy();

		// adjust position with the difference (before - after)
		// so that the bottom left position remains unchanged
		Vector2 newPosition = body.getPosition().add(positionBefore).sub(positionAfter);
		body.setTransform(newPosition, angle);
	}

	public void draw(Batch batch) {
		batch.draw(textureObject.getTextureRegion(), x, y, textureObject.getOriginX() / SonicBoom.PPM,
				textureObject.getOriginY() / SonicBoom.PPM,
				textureObject.getTextureRegion().getRegionWidth() / SonicBoom.PPM,
				textureObject.getTextureRegion().getRegionHeight() / SonicBoom.PPM, textureObject.getScaleX(),
				textureObject.getScaleY(), -textureObject.getRotation());

	}

	public abstract void customizeObject();

	public abstract void update(float delta);

	public abstract void hit();

}
