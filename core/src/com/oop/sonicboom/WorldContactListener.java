package com.oop.sonicboom;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		// collision define
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {
		case SonicBoom.PLAYER_BIT | SonicBoom.GROUND_BIT:
		case SonicBoom.PLAYER_BIT | SonicBoom.PLATFORM_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).onGround = true;

				if (((Object) fixB.getUserData()) instanceof GameObject) {
					((GameObject) fixB.getUserData()).hit();
				}

			} else {
				((Player) fixB.getUserData()).onGround = true;

				if (((Object) fixA.getUserData()) instanceof GameObject) {
					((GameObject) fixA.getUserData()).hit();
				}
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.RING_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Ring) fixB.getUserData()).hit();
			} else {
				((Ring) fixA.getUserData()).hit();
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_SWITCH_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).switchLoop();
			} else {
				((Player) fixB.getUserData()).switchLoop();
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_R_SENSOR_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).loop = true;
			} else {
				((Player) fixB.getUserData()).loop = true;
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_L_SENSOR_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).loop = false;
			} else {
				((Player) fixB.getUserData()).loop = false;
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((GameObject) fixB.getUserData()).hit();
			} else {
				((GameObject) fixA.getUserData()).hit();
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.ENEMY_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Enemy) fixB.getUserData()).hit();
			} else {
				((Enemy) fixA.getUserData()).hit();
			}
			break;
		}

	}

	@Override
	public void endContact(Contact contact) {
		// reset the default state of the contact in case it comes back for more
		contact.setEnabled(true);

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		// collision define
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_R_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).onLoop = false;
			} else {
				((Player) fixB.getUserData()).onLoop = false;
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_L_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				((Player) fixA.getUserData()).onLoop = false;
			} else {
				((Player) fixB.getUserData()).onLoop = false;
			}
			break;
		}

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		// collision define
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {
		case SonicBoom.PLAYER_BIT | SonicBoom.PLATFORM_BIT:
			Body playerBody = null;
			Body platform = null;

			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				playerBody = fixA.getBody();
				platform = fixB.getBody();

			} else {
				playerBody = fixB.getBody();
				platform = fixA.getBody();

			}

			int numPoints = contact.getWorldManifold().getNumberOfContactPoints();
			WorldManifold worldManifold = contact.getWorldManifold();

			// check if contact points are moving downward
			for (int i = 0; i < numPoints; i++) {
				Vector2 pointVel = playerBody.getLinearVelocityFromWorldPoint(worldManifold.getPoints()[i]);

				if (pointVel.y < 0)
					return;// point is moving down, leave contact solid and exit
			}

			// check if contact points are moving into platform
			for (int i = 0; i < numPoints; i++) {
				Vector2 pointVelPlatform = platform.getLinearVelocityFromWorldPoint(worldManifold.getPoints()[i]);
				Vector2 pointVelOther = playerBody.getLinearVelocityFromWorldPoint(worldManifold.getPoints()[i]);
				Vector2 relativeVel = platform.getLocalVector(pointVelOther.sub(pointVelPlatform));

				Vector2 relativePoint = platform.getLocalPoint(worldManifold.getPoints()[i]);

				if (relativeVel.y < -1) // if moving down faster than 1 m/s,
										// handle as before
					return;// point is moving into platform, leave contact solid
							// and exit
				else if (relativeVel.y < 1) { // if moving slower than 1 m/s
					// borderline case, moving only slightly out of platform
					float platformFaceY = 0.5f;// front of platform, from
												// fixture definition :(
					float platformFaceX = 0.5f;

					if (relativePoint.y > platformFaceY - 100 && relativePoint.x > platformFaceX - 100)
						return;// contact point is less than 5cm inside front
								// face of platfrom
				} else {
					;// moving up faster than 1 m/s
				}

			}

			// no points are moving downward, contact should not be solid
			contact.setEnabled(false);
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_R_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {

				if (((Player) fixA.getUserData()).loop == false) {
					contact.setEnabled(false);
				} else {
					((Player) fixA.getUserData()).contactPoint = contact.getWorldManifold().getPoints()[0];
					((Player) fixA.getUserData()).onLoop = true;
				}

			} else {
				if (((Player) fixB.getUserData()).loop == false) {
					contact.setEnabled(false);
				} else {
					((Player) fixB.getUserData()).contactPoint = contact.getWorldManifold().getPoints()[0];
					((Player) fixB.getUserData()).onLoop = true;
				}
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.LOOP_L_BIT:
			if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT) {
				if (((Player) fixA.getUserData()).loop == true) {
					contact.setEnabled(false);
				} else {
					((Player) fixA.getUserData()).contactPoint = contact.getWorldManifold().getPoints()[0];
					((Player) fixA.getUserData()).onLoop = true;
				}

			} else {
				if (((Player) fixB.getUserData()).loop == true) {
					contact.setEnabled(false);
				} else {
					((Player) fixB.getUserData()).contactPoint = contact.getWorldManifold().getPoints()[0];
					((Player) fixB.getUserData()).onLoop = true;
				}
			}
			break;
		case SonicBoom.PLAYER_BIT | SonicBoom.RING_BIT:
			contact.setEnabled(false);
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
