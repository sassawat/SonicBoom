package com.oop.sonicboom;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // collision define
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
        case SonicBoom.PLAYER_BIT | SonicBoom.GROUND_BIT:
        	if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT){
        		((Player) fixA.getUserData()).onGround = true;
        	}
        	else{
        		((Player) fixB.getUserData()).onGround = true;
        	}
        	break;
        case SonicBoom.PLAYER_BIT | SonicBoom.RING_BIT:
        	if (fixA.getFilterData().categoryBits == SonicBoom.PLAYER_BIT){
				((Ring) fixB.getUserData()).hit();
        	}
        	else{
        		((Ring) fixA.getUserData()).hit();
        	}
        	break;
        }
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
