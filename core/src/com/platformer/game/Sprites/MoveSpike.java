package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by User on 6/4/2017.
 */

public abstract class MoveSpike extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    protected Fixture fixture;
    public Vector2 velocity;

    public MoveSpike(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineMoveSpike();
        velocity = new Vector2(0,-4);
    }

    protected abstract void defineMoveSpike();
    protected abstract void destroySpike();
}
