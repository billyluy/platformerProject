package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/30/2017.
 */

public class Coin extends InteractiveTiles {
    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(platformerGame.COIN_BIT);
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Coin");
        setCategoryFilter(platformerGame.DESTROY_BIT);
        getCell().setTile(null);
        Player.jump ++;
    }
}

