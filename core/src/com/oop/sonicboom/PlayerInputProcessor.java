package com.oop.sonicboom;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputProcessor implements InputProcessor {

	Player player;

	public PlayerInputProcessor(Player player) {
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.UP:
			break;
		case Keys.DOWN:
			if ((player.moveRight || player.moveLeft) && player.onGround) {
				player.spinning = true;
			}
			if (!player.spinning) {
				player.preSpin = true;
			}
			break;
		case Keys.RIGHT:
			if (player.preSpin && player.onGround) {
				player.spinning = true;
			}
			player.moveRight = true;
			break;
		case Keys.LEFT:
			if (player.preSpin && player.onGround || player.spinJump) {
				player.spinning = true;
			}
			player.moveLeft = true;
			break;
		case Keys.SPACE:
			if (player.preSpin && player.onGround) {
				player.spinCharged = true;
			} else {
				player.spinJump = true;
				player.jump();
			}
			break;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		switch (keycode) {
		case Keys.UP:
			break;
		case Keys.DOWN:
			if (player.spinCharged) {
				player.spinCharged = false;
				player.spinning = true;
				player.dash();
			}
			player.preSpin = false;
			break;
		case Keys.RIGHT:
			player.moveRight = false;
			break;
		case Keys.LEFT:
			player.moveLeft = false;
			break;
		case Keys.SPACE:
			break;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
