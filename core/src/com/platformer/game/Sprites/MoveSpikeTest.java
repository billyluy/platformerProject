package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by User on 6/4/2017.
 */

public class MoveSpikeTest extends Sprite {
    public World world;
    public Body b2body;

    public MoveSpikeTest(World world){
        this.world = world;
        defineMoveSpikeTest();
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void defineMoveSpikeTest(){
        BodyDef bdef = new BodyDef();
        //position is based on the entire map
        bdef.position.set(128/ platformerGame.PPM,31164/platformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30/platformerGame.PPM);

        fdef.filter.categoryBits = platformerGame.PLAYER_BIT;
        fdef.filter.maskBits = platformerGame.GROUND_BIT | platformerGame.SPIKE_BIT | platformerGame.DISAPPERING_BIT | platformerGame.COIN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
