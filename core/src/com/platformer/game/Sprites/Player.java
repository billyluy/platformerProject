package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
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

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD}

    ;
    public State currentState;
    public State previousState;
    private Animation<TextureRegion> playerRUN;
    private Animation<TextureRegion> playerSTAND;
    private Animation<TextureRegion> playerDEAD;
    private float stateTimer;
    private boolean runningRight;
    Sprite bloodSprite;
    private boolean isDead;
    private boolean godMode;
    public static float playerX;
    public static float playerY;

    public Player(PlayScreen ps, float x, float y) {
        super(ps.getAtlas().findRegion("run1"));
        bloodSprite = new Sprite(new TextureAtlas("blood.pack").findRegion("bloods"));
        playerX = x;
        playerY = y;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        playerRUN = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        playerSTAND = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                frames.add(new TextureRegion(bloodSprite.getTexture(), j * 333, i * 333, 333, 333));
            }
        }
        playerDEAD = new Animation<TextureRegion>(0.05f, frames);
        frames.clear();
        setBounds(0, 0, 64 / platformerGame.PPM, 64 / platformerGame.PPM);
        this.world = ps.getWorld();
        definePlayer();
        jump = 0;
        godMode = false;

    }

    public void update(float dt) {
        if(godMode){
            Filter filter = new Filter();
            filter.categoryBits = platformerGame.GOD_BIT;
            fixture.setFilterData(filter);
        }
        playerX = getX();
        playerY = getY();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = playerDEAD.getKeyFrame(stateTimer, false);
                setSize(333 / platformerGame.PPM, 333 / platformerGame.PPM);
                world.setGravity(new Vector2(0, 0));
                this.body.setLinearVelocity(0, 0);
                break;
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
        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (isDead)
            return State.DEAD;
        else if (body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else return State.STANDING;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        //position is based on the entire map
        bdef.position.set(playerX, playerY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / platformerGame.PPM);

        fdef.filter.categoryBits = platformerGame.PLAYER_BIT;
        fdef.filter.maskBits = platformerGame.GROUND_BIT |
                platformerGame.SPIKE_BIT |
                platformerGame.DISAPPERING_BIT |
                platformerGame.COIN_BIT |
                platformerGame.MOVESPIKE_BIT |
                platformerGame.SAVE_BIT;
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData("player");
    }

    public void hit() {
        isDead = true;
    }

    public void setDead(boolean b) {
        isDead = b;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public static float getPlayerX() {
        return playerX;
    }

    public static float getPlayerY() {
        return playerY;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void setGodMode(boolean b){
        godMode = b;
    }
}
