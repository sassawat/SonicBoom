package com.oop.sonicboom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class GameObject implements Disposable {

	private GameScreen game;
	
	private Array<Ring> rings;
	private Animation ringAnimation;
	private Texture ringTexture;

	public GameObject(GameScreen game) {
		this.game = game;
		game.getWorld();

		createRing();
	}

	public void createRing() {

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

	public void update(float delta) {
		for (Ring ring : rings) {
			ring.update(delta);
		}
	}

	public void draw(Batch batch) {
		for (Ring ring : rings) {
			ring.draw(batch);
		}
	}

	@Override
	public void dispose() {
		ringTexture.dispose();
	}
}
