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

	public GameObjects(GameScreen game) {
		this.game = game;

		createRings();
		createSpikes();
		createDashPanels();
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
			if (object instanceof TextureMapObject && object.getName().equals("spike")) {
				spikes.add(new Spike(game, object));
			}
		}
	}

	public void createDashPanels() {
		dashPanels = new Array<DashPanel>();

		for (MapObject object : game.getMap().getLayers().get("GameObject").getObjects()) {
			if (object instanceof TextureMapObject && object.getName().equals("dp")) {
				dashPanels.add(new DashPanel(game, object));
			}
		}
	}

	public void update(float delta) {
		for (Ring ring : rings) {
			ring.update(delta);
		}
	}

	public void draw(Batch batch) {
		for (Ring ring : rings) {
			ring.draw(batch);
		}

		for (Spike spike : spikes) {
			spike.draw(batch);
		}

		for (DashPanel dashPanel : dashPanels) {
			dashPanel.draw(batch);
		}
	}

	@Override
	public void dispose() {
		ringTexture.dispose();
	}
}
