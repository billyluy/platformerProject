package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by Student 6 on 6/9/2017.
 */

public class Save extends InteractiveTiles {

    private PlayScreen ps;
    private static TiledMapTileSet tileSet;
    private final int[] saveFrames;
    private boolean touched;

    public Save(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        ps = screen;
        fixture.setUserData(this);
        fixture.setSensor(true);
        tileSet = map.getTileSets().getTileSet("tileset");
        saveFrames = new int[6];
        for(int i = 0; i < 6; i ++) {
            saveFrames[i] = 33 + (i * 2);
            if(i == 3)
                saveFrames[i] = saveFrames[i - 1] - 2;

        }
        touched = false;
        setCategoryFilter(platformerGame.SAVE_BIT);
    }

    public void changeTile() {
        getCell().setTile(tileSet.getTile(saveFrames[(int) (Math.random() * 6)]));
    }

    public boolean getTouched() {
        return touched;
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Save Tile");
        System.out.println(ps.getPlayer().getX() * 100);
        System.out.println(ps.getPlayer().getY() * 100);
        setCategoryFilter(platformerGame.DESTROY_BIT);
        ps.setPlayerX(ps.getPlayer().getX());
        ps.setPlayerY(ps.getPlayer().getY());
        getCell().setTile(null);
        touched = true;
    }
}
