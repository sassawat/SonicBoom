package com.oop.sonicboom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Disposable;

public abstract class Player extends Sprite implements Disposable {

	protected GameScreen game;

	protected Body body;
	protected Fixture fixture;

	protected enum State {
		IDLE, WALKING, RUNNING, SPINNING, SPINCHARGE, JUMPPING, CROUCHING, HURTING, DYING
	};

	// player game state
	protected boolean faceRight;
	protected boolean onGround;
	protected boolean loop;
	protected boolean loseRing;
	protected boolean hurt;
	protected boolean dead;

	protected int tempRing;

	// player motion state
	protected boolean moveRight;
	protected boolean moveLeft;
	protected boolean preSpin;
	protected boolean spinCharged;
	protected boolean spinning;
	protected boolean spinJump;
	protected boolean crouch;

	private PlayerInputProcessor playerInputProcessor;

	public Player(GameScreen game) {
		this.game = game;

		// find body for player
		body = game.parser.getBodies().get("player");
		fixture = game.parser.getFixtures().get("player");
		fixture.setUserData(this);

		// create PlayerInputProcessor
		playerInputProcessor = new PlayerInputProcessor(this);
		Gdx.input.setInputProcessor(playerInputProcessor);
	}

	public void switchLoop() {
		loop = loop ? false : true;
	}

	abstract public State getState();

	abstract public void update(float delta);

	abstract public void jump();

	abstract public void dash();

	abstract public void loseRing(int n);

	abstract public void hurt(float time);

	abstract public void kill();

}
