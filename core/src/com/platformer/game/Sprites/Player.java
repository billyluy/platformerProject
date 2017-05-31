package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/24/2017.
 */

public class Player extends Sprite {
    public World world;
    public Body body;
    public int jump;
    protected Fixture fixture;

    public Player(World world){
        this.world = world;
        definePlayer();
        jump = 12;
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        //position is based on the entire map
        bdef.position.set(128/platformerGame.PPM,31164/platformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30/platformerGame.PPM);

        fdef.filter.categoryBits = platformerGame.PLAYER_BIT;
        fdef.filter.maskBits = platformerGame.DEFAULT_BIT | platformerGame.GROUND_BIT | platformerGame.SPIKE_BIT;

        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData("player");
    }


}
