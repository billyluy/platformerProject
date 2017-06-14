package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
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

    private PlayScreen ps;
    private static TiledMapTileSet tileSet;
    private final int[] saveFrames;
    private int frameNum;
    private boolean touched;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        ps = screen;
        tileSet = map.getTileSets().getTileSet("tileset");
        saveFrames = new int[5];
        frameNum = 1;
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(platformerGame.COIN_BIT);
        touched = false;
        for(int i = 0; i < 5; i ++) {
            saveFrames[i] = 45 + (i * 2);
        }
    }

    public void changeTile() {
        getCell().setTile(tileSet.getTile(saveFrames[frameNum]));
        frameNum ++;
        if(frameNum > 4)
            frameNum = 0;
    }

    public boolean getTouched() {
        return touched;
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Coin");
        setCategoryFilter(platformerGame.DESTROY_BIT);
        getCell().setTile(null);
        Player.jump ++;
        touched = true;
    }
}

