package com.oop.sonicboom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class GameObjects implements Disposable {

	private GameScreen game;

	private Array<Ring> rings;
	private Animation ringAnimation;
	private Texture ringTexture;

	private Array<Spike> spikes;
	private Array<DashPanel> dashPanels;
	private Array<Spring> springs;
	private Array<Platform> platforms;

	public GameObjects(GameScreen game) {
		this.game = game;

		createRings();
		createSpikes();
		createDashPanels();
		createSprings();
		createPlatform();
	}

	public void createRings() {

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

	public void createSpikes() {
		spikes = new Array<Spike>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			try {
				if (object instanceof TextureMapObject && object.getName().equals("spike")) {
					spikes.add(new Spike(game, object));
				}
			} catch (NullPointerException e) {
				// object has no name
			}
		}
	}

	public void createDashPanels() {
		dashPanels = new Array<DashPanel>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			try {
				if (object instanceof TextureMapObject && object.getName().equals("dp")) {
					dashPanels.add(new DashPanel(game, object));
				}
			} catch (NullPointerException e) {
				// object has no name
			}
		}
	}

	public void createSprings() {
		springs = new Array<Spring>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			try {
				if (object instanceof TextureMapObject && object.getName().equals("spring")) {
					springs.add(new Spring(game, object));
				}
			} catch (NullPointerException e) {
				// object has no name
			}
		}
	}

	public void createPlatform() {
		platforms = new Array<Platform>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			try {
				if (object instanceof TextureMapObject && object.getName().equals("platform")) {
					platforms.add(new Platform(game, object));
				}
			} catch (NullPointerException e) {
				// object has no name
			}
		}
	}

	public void update(float delta) {
		for (Ring ring : rings) {
			ring.update(delta);
		}

		for (Spring spring : springs) {
			spring.update(delta);
		}

		for (Platform platform : platforms) {
			platform.update(delta);
		}
	}

	public void draw(Batch batch) {
		// draw rings
		for (Ring ring : rings) {
			ring.draw(batch);
		}

		// draw spikes
		for (Spike spike : spikes) {
			spike.draw(batch);
		}

		// draw dash panel
		for (DashPanel dashPanel : dashPanels) {
			dashPanel.draw(batch);
		}

		// draw springs
		for (Spring spring : springs) {
			spring.draw(batch);
		}

		// draw platforms
		for (Platform platform : platforms) {
			platform.draw(batch);
		}
	}

	@Override
	public void dispose() {
		ringTexture.dispose();
	}
}
