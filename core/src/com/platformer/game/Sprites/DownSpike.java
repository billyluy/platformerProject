package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by User on 6/4/2017.
 */

public class DownSpike extends MoveSpike {

    private boolean destroyMe;
    private boolean destroyed;
    private Sprite spikes;
    private float bounds;

    public DownSpike(PlayScreen screen,float x, float y, float bound) {
        super(screen, x, y, bound);
//        Image image = new Image(new Texture("sprites cancer\\spike down.png"));
        this.destroyed=false;
        this.destroyMe=false;
        setBounds(getX(),getY(),64/platformerGame.PPM,64/platformerGame.PPM);
        spikes = new Sprite(new TextureAtlas("spikes.pack").findRegion("spike down"));
        this.bounds = bound;
        velocity = new Vector2(0,-8);
    }

    public void update(float dt){
        if(destroyMe && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }else if(!destroyed){
            setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
            if(getX()+getWidth()/2 >= Player.getPlayerX() &&  getX()-getWidth()/2 <= Player.getPlayerX() && Player.getPlayerY()>=bounds){
                b2body.setLinearVelocity(velocity);
            }
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(new TextureRegion(spikes.getTexture(), 0, 0, 64, 64));
    }

    @Override
    protected void defineMoveSpike() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);
        b2body.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30/platformerGame.PPM);

        fdef.filter.categoryBits = platformerGame.MOVESPIKE_BIT;
        fdef.filter.maskBits = platformerGame.GROUND_BIT | platformerGame.PLAYER_BIT;

        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void destroySpike() {
        this.destroyMe = true;
    }
}
