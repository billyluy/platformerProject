package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.game.Screens.GameOverScreen;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by Student 6 on 6/9/2017.
 */

public class Save extends InteractiveTiles {

    private PlayScreen ps;


    public Save(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        ps = screen;
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(platformerGame.SAVE_BIT);
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Save Tile");
        ps.setPlayerX(ps.getPlayer().getX());
        ps.setPlayerY(ps.getPlayer().getY());
        getCell().setTile(null);
    }
}
