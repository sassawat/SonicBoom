package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Player extends Sprite{
	
	protected World world;
	protected GameScreen game;
	protected Body body;
	protected Fixture fixture;
	protected boolean onGround;
	
	public Player(World world, GameScreen game){
		this.world = world;
		this.game = game;
		
		body = game.parser.getBodies().get("player");
		fixture = game.parser.getFixtures().get("player");
		fixture.setUserData(this);
	}
	
	abstract public void update(float delta);
	abstract public void handleInput(float delta);

}
