package com.platformer.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platformer.game.Sprites.InteractiveTiles;

/**
 * Created by Student 6 on 5/30/2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() != null && fixA.getUserData().equals("spike")){
            System.out.println("HIT SPIKE");
        }
        if(fixA.getUserData().equals("player") || fixB.getUserData().equals("player")){
            Fixture player = fixA.getUserData().equals("player") ? fixA:fixB;
            Fixture object = player.equals(fixA) ? fixB:fixA;
            if(object.getUserData() instanceof InteractiveTiles){
                ((InteractiveTiles)object.getUserData()).onTouch();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}