package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

import java.util.ArrayList;

import sun.awt.PlatformFont;

/**
 * Created by Student6 on 5/30/2017.
 */

public class Coin extends InteractiveTiles {

    TiledMapTile coin;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(platformerGame.COIN_BIT);
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Coin");
//        setCategoryFilter(platformerGame.DESTROY_BIT);
//        getCell().setTile(null);
        Player.jump ++;
    }

    public void restart(){

    }
}

