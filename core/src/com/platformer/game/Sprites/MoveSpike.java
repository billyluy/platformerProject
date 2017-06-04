package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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

    public MoveSpike(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        setPosition(x,y);
        defineMoveSpike();
    }

    protected abstract void defineMoveSpike();
}
