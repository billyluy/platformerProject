package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by Student 6 on 6/9/2017.
 */

public class Save extends InteractiveTiles {

    public Save(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(platformerGame.SAVE_BIT);
    }

    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Save Tile");
        getCell().setTile(null);
    }
}
