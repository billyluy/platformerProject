package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Student6 on 5/30/2017.
 */

public abstract class Coin extends InteractiveTiles{
    public Coin(World world, TiledMap map, Rectangle rect){
        super(world,map,rect);
        fixture.setUserData(this);
    }

    @Override
    public void onTouch() {

    }
}
