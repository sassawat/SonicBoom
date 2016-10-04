package com.oop.sonicboom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Sonic extends Player {

	private enum State {
		IDLE, WALKING, RUNNING, SPINNING
	};

	private State currentState;
	private State previousState;

	private float stateTimer;

	private boolean faceRight;

	private Animation idle;
	private Animation walk;
	private Animation run;
	private Animation spin;

	private Texture texture;

	public Sonic(World world, GameScreen game) {
		super(world, game);

		currentState = State.IDLE;
		previousState = State.IDLE;
		stateTimer = 0;
		faceRight = false;

		texture = new Texture("Sprites/sonic.png");

		Array<TextureRegion> frames = new Array<TextureRegion>();

		// get idle animation frames and add them to sonic Animation
		for (int i = 1; i < 8; i++)
			frames.add(new TextureRegion(texture, 5 + 36 * i, 10, 36, 34));
		idle = new Animation(0.2f, frames);
		frames.clear();

		// get walk animation frames and add them to sonic Animation
		for (int i = 1; i < 6; i++)
			frames.add(new TextureRegion(texture, 275 + 36 * i, 100, 37, 37));
		walk = new Animation(0.2f, frames);
		frames.clear();

		// get run animation frames and add them to sonic Animation
		for (int i = 1; i < 8; i++)
			frames.add(new TextureRegion(texture, 39 * i, 144, 37, 36));
		run = new Animation(0.12f, frames);
		frames.clear();

		// get spin animation frames and add them to sonic Animation
		for (int i = 1; i < 4; i++)
			frames.add(new TextureRegion(texture, 110 + (36 * i), 239, 36, 36));
		spin = new Animation(0.1f, frames);
		frames.clear();

		setBounds(0, 0, 34 / SonicBoom.PPM, 34 / SonicBoom.PPM);
	}

	@Override
	public void update(float delta) {
		setPosition(body.getWorldCenter().x - getWidth() / 2, body.getWorldCenter().y - getHeight() / 2);
		setRegion(getFrame(delta));

		stateTimer += delta;
	}

	public TextureRegion getFrame(float delta) {
		currentState = getState();

		TextureRegion region;

		switch (currentState) {
		case SPINNING:
			region = spin.getKeyFrame(stateTimer, true);
			break;
		case RUNNING:
			region = run.getKeyFrame(stateTimer, true);
			break;
		case WALKING:
			region = walk.getKeyFrame(stateTimer, true);
			break;
		case IDLE:
		default:
			region = idle.getKeyFrame(stateTimer, true);
			break;
		}

		if (faceRight && !region.isFlipX()) {
			region.flip(true, false);
		} else if (!faceRight && region.isFlipX()) {
			region.flip(true, false);
		}

		stateTimer = currentState == previousState ? stateTimer + delta : 0;
		previousState = currentState;

		return region;
	}

	public State getState() {
		if (!onGround) {
			return State.SPINNING;
		} else if (body.getLinearVelocity().x > 2f || body.getLinearVelocity().x < -2f) {
			return State.RUNNING;
		} else if (body.getLinearVelocity().x > 0.1f || body.getLinearVelocity().x < -0.1f) {
			return State.WALKING;
		} else {
			return State.IDLE;
		}
	}

	@Override
	public void handleInput(float delta) {
		// Control Player
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && onGround) {
			// body.applyLinearImpulse(new Vector2(0, 0.4f),
			// body.getWorldCenter(), true);
			body.applyForce(new Vector2(0, 20f), body.getWorldCenter(), true);
			onGround = false;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && body.getLinearVelocity().x <= 4.5) {
			body.applyLinearImpulse(new Vector2(0.002f, 0), body.getWorldCenter(), true);
			body.applyTorque(-5f, true);
			faceRight = true;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) && body.getLinearVelocity().x >= -4.5) {
			body.applyLinearImpulse(new Vector2(-0.002f, 0), body.getWorldCenter(), true);
			body.applyTorque(5f, true);
			faceRight = false;
		}
	}

	public void flipSprite() {

	}

}
