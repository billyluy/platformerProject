package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
    private enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    private Animation<TextureRegion> playerRUN;
    private Animation<TextureRegion> playerJUMP;
    private Animation<TextureRegion> playerSTAND;
    private float stateTimer;
    private boolean runningRight;
    private boolean isDead;

    public Player(PlayScreen ps){
        super(ps.getAtlas().findRegion("run1"));
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i ++)
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        playerRUN = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        for(int i = 3; i < 5; i ++)
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        playerSTAND = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();


        setBounds(0, 0, 64 / platformerGame.PPM, 64 / platformerGame.PPM);
        this.world = ps.getWorld();
        definePlayer();
        jump = 0;
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            /*
            add a dead region
             */
            case JUMPING:
                region = new TextureRegion(getTexture(), 64, 0, 64, 64);
                break;
            case RUNNING:
                region = playerRUN.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = new TextureRegion(getTexture(), 0, 0, 64, 64);
                break;
            case STANDING:
            default:
                region = playerSTAND.getKeyFrame(stateTimer, true);
                break;
        }
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        } else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if(isDead)
            return State.DEAD;
        else if(body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;
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
        fdef.filter.maskBits = platformerGame.GROUND_BIT | platformerGame.SPIKE_BIT | platformerGame.DISAPPERING_BIT | platformerGame.COIN_BIT;

        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData("player");
    }

    public void hit(){
        isDead = true;

    }
}
