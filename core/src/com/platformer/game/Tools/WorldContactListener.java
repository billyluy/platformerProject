package com.platformer.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.platformer.game.Sprites.BallSpike;
import com.platformer.game.Sprites.InteractiveTiles;
import com.platformer.game.Sprites.MoveSpike;
import com.platformer.game.Sprites.Player;
import com.platformer.game.platformerGame;

/**
 * Created by Student 6 on 5/30/2017.
 */

public class WorldContactListener implements ContactListener {
    private Player player;
    private AssetManager manager;
    private Sound sound;

    public WorldContactListener(Player player) {
        this.player = player;
        manager = new AssetManager();
        manager.load("audio/death.mp3", Sound.class);
        manager.finishLoading();
        sound = manager.get("audio/death.mp3", Sound.class);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();//moving
        Fixture fixB = contact.getFixtureB();//getting collided
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if (fixA.getUserData() != null && fixA.getUserData().equals("spike") && fixB.getUserData().equals("player")) {
            sound.play();
            player.setDead(true);
            Gdx.app.log("Touch", "Spike");
        }
        if (fixA.getUserData().equals("player") || fixB.getUserData().equals("player")) {
            Fixture player = fixA.getUserData().equals("player") ? fixA : fixB;
            Fixture object = player.equals(fixA) ? fixB : fixA;
            if (object.getUserData() instanceof InteractiveTiles) {
                ((InteractiveTiles) object.getUserData()).onTouch();
            }
        }

        switch (cDef) {
            case platformerGame.GROUND_BIT | platformerGame.MOVESPIKE_BIT:
                if (fixA.getFilterData().categoryBits == platformerGame.MOVESPIKE_BIT)
                    ((MoveSpike) fixA.getUserData()).destroySpike();
                else
                    ((MoveSpike) fixB.getUserData()).destroySpike();
                break;
            case platformerGame.PLAYER_BIT | platformerGame.MOVESPIKE_BIT:
                if (fixA.getFilterData().categoryBits == platformerGame.PLAYER_BIT) {
                    sound.play();
                    this.player.setDead(true);
                }
                else {
                    sound.play();
                    this.player.setDead(true);
                }
                if (fixA.getFilterData().categoryBits == platformerGame.MOVESPIKE_BIT)
                    ((MoveSpike) fixA.getUserData()).destroySpike();
                else
                    ((MoveSpike) fixB.getUserData()).destroySpike();
                break;
            case platformerGame.BALL_BIT | platformerGame.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == platformerGame.BALL_BIT)
                    ((BallSpike) fixA.getUserData()).changeVelocity(true, true);
                else
                    ((BallSpike) fixB.getUserData()).changeVelocity(true, true);
                break;
            case platformerGame.BALL_BIT | platformerGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == platformerGame.PLAYER_BIT) {
                    sound.play();
                    this.player.setDead(true);
                }
                else {
                    sound.play();
                    this.player.setDead(true);
                }
                if (fixA.getFilterData().categoryBits == platformerGame.MOVESPIKE_BIT)
                    ((MoveSpike) fixA.getUserData()).destroySpike();
                else
                    ((MoveSpike) fixB.getUserData()).destroySpike();
                break;
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
