package com.platformer.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Student6 on 5/26/2017.
 */

public class InteractiveTiles {
    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Rectangle bounds;
    private Body body;

    public InteractiveTiles(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;
    }
}
