package com.oop.sonicboom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class GameObjects implements Disposable {

	private GameScreen game;
	private Array<Ring> rings;
	private Animation ringAnimation;
	private Texture ringTexture;

	private Array<GameObject> objects;

	private Array<Ring> spawnedRings;
	private BodyDef bdefRing;
	private FixtureDef fdefRing;

	public GameObjects(GameScreen game) {
		this.game = game;

		createRings();

		createGameObject();

		defineSpawnedRing();
	}

	private void createRings() {

		rings = new Array<Ring>();

		// create ring Animation
		ringTexture = new Texture("Sprites/ring.gif");

		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(ringTexture, 0, 0, 16, 16));
		frames.add(new TextureRegion(ringTexture, 22, 0, 16, 16));
		frames.add(new TextureRegion(ringTexture, 0, 22, 16, 16));
		frames.add(new TextureRegion(ringTexture, 22, 22, 16, 16));
		ringAnimation = new Animation(0.2f, frames);
		frames.clear();

		// find body for item
		String key = "ring";
		int n = 0;

		while (game.parser.getBodies().get(n == 0 ? key : key + n) != null) {
			String tmpKey = n == 0 ? key : key + n;
			Body tmpBody = game.parser.getBodies().get(tmpKey);
			Fixture tmpFixture = game.parser.getFixtures().get(tmpKey);

			rings.add(new Ring(game, tmpBody, tmpFixture, ringAnimation));

			n++;
		}

	}

	private void createGameObject() {
		objects = new Array<GameObject>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			try {
				if (object instanceof TextureMapObject && object.getName().equals("spike")) {
					objects.add(new Spike(game, object));
				} else if (object instanceof TextureMapObject && object.getName().equals("dp")) {
					objects.add(new DashPanel(game, object));
				} else if (object instanceof TextureMapObject && object.getName().equals("spring")) {
					objects.add(new Spring(game, object));
				} else if (object instanceof TextureMapObject && object.getName().equals("platform")) {
					objects.add(new Platform(game, object));
				}
			} catch (NullPointerException e) {
				// object has no name
			}
		}
	}

	private void defineSpawnedRing() {
		spawnedRings = new Array<Ring>();

		fdefRing = new FixtureDef();

		CircleShape shape = new CircleShape();
		shape.setRadius(8 / SonicBoom.PPM);
		shape.setPosition(new Vector2(16 / 2 / SonicBoom.PPM, 16 / 2 / SonicBoom.PPM));
		fdefRing.shape = shape;

		fdefRing.filter.categoryBits = SonicBoom.RING_BIT;
		fdefRing.restitution = 1f;
	}

	public void spawnRing(Vector2 point, float vX, float vY) {
		bdefRing = new BodyDef();
		bdefRing.type = BodyType.DynamicBody;
		bdefRing.position.set(point);
		bdefRing.linearVelocity.x = vX;
		bdefRing.linearVelocity.y = vY;

		Ring ring = new Ring(game, bdefRing, fdefRing, true, ringAnimation);
		ring.setLifeTime(5);

		spawnedRings.add(ring);

	}

	public void spawnRing(float x, float y, float vX, float vY) {
		spawnRing(new Vector2(x, y), vX, vY);
	}

	public void update(float delta) {

		for (Ring ring : rings) {
			ring.update(delta);

			if (ring.destroyed) {
				rings.removeValue(ring, true);
			}
		}

		for (Ring ring : spawnedRings) {
			ring.update(delta);

			if (ring.destroyed && ring.getDestroyedTime() > 2) {
				spawnedRings.removeValue(ring, true);
			}
		}

		for (GameObject object : objects) {
			object.update(delta);

			if (object.destroyed) {
				objects.removeValue(object, true);
			}
		}
	}

	public void draw(Batch batch) {
		// draw rings
		for (Ring ring : rings) {
			ring.draw(batch);
		}

		// draw spawned rings
		for (Ring ring : spawnedRings) {
			ring.draw(batch);
		}

		// draw game objects
		for (GameObject object : objects) {
			object.draw(batch);
		}
	}

	@Override
	public void dispose() {
		ringTexture.dispose();
	}
}
