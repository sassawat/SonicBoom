package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Sonic extends Player {
	
	private enum State {
		IDLE, WALKING, RUNING, SPINNING, FALLING, 
	};

	private State currentState;
	private State previousState;

	private float stateTimer;
	private boolean faceRight;

	private Animation run;
	private Animation jump;
	
	private TextureRegion idle;
	
	public Sonic(World world, GameScreen game){
		super(world, game);
		
		currentState = State.IDLE;
		previousState = State.IDLE;
		stateTimer = 0;
		faceRight = true;
	}

}
