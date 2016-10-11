package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Disposable;

public abstract class Player extends Sprite implements Disposable {

	protected GameScreen game;

	protected Body body;
	protected Fixture fixture;

	protected boolean faceRight;
	protected boolean onGround;
	protected boolean moveRight;
	protected boolean moveLeft;
	protected boolean preSpin;
	protected boolean spinCharged;
	protected boolean spinning;
	protected boolean spinJump;
	protected boolean loop;

	public Player(GameScreen game) {
		this.game = game;

		body = game.parser.getBodies().get("player");
		fixture = game.parser.getFixtures().get("player");
		fixture.setUserData(this);
	}
	
	public void switchLoop(){
		loop = loop ? false : true;
	}

	abstract public void update(float delta);

	abstract public void jump();

	abstract public void dash();

}
