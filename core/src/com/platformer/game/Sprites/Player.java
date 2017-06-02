package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/24/2017.
 */

public class Player extends Sprite {
    public World world;
    public Body body;
    public static int jump;
    protected Fixture fixture;
    private TextureRegion playerLeftStand1;
    private TextureRegion playerLeftStand2;
    private TextureRegion playerRightStand1;
    private TextureRegion playerRightStand2;

    public Player(World world, PlayScreen ps){
        super(ps.getAtlas().findRegion("leftstand1"));
        playerLeftStand1 = new TextureRegion(getTexture(), 0, 0, 64, 64);
        playerLeftStand2 = new TextureRegion(getTexture(), 64, 0, 64, 64);
        playerRightStand1 = new TextureRegion(getTexture(), 128, 0, 64, 64);
        playerRightStand2 = new TextureRegion(getTexture(), 192, 0, 64, 64);

        setBounds(0, 0, 80 / platformerGame.PPM, 64 / platformerGame.PPM);
        setRegion(playerLeftStand1);
        this.world = world;
        definePlayer();
        jump = 2;
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
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
        fdef.filter.maskBits = platformerGame.DEFAULT_BIT | platformerGame.GROUND_BIT | platformerGame.SPIKE_BIT | platformerGame.DISAPPERING_BIT | platformerGame.COIN_BIT;

        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData("player");
    }


}
