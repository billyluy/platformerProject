package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/26/2017.
 */

public class DisappearingTile extends InteractiveTiles {
    private float playerYVelocity;
    private float playerXVelocity;
    private PlayScreen ps;

    public DisappearingTile(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        ps = screen;
        fixture.setUserData(this);
        setCategoryFilter(platformerGame.DISAPPERING_BIT);
        playerYVelocity = 0;
        playerXVelocity = 0;
    }

    public void upodateVelocity() {
        playerYVelocity = ps.getPlayer().body.getLinearVelocity().y;
        playerXVelocity = ps.getPlayer().body.getLinearVelocity().x;
    }
    @Override
    public void onTouch() {
        Gdx.app.log("Touch", "Disappear");
        setCategoryFilter(platformerGame.DESTROY_BIT);
        getCell().setTile(null);
        ps.getPlayer().body.setLinearVelocity(playerXVelocity, playerYVelocity);
    }
}
