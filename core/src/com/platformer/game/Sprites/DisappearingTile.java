package com.platformer.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Student6 on 5/26/2017.
 */

public class DisappearingTile extends InteractiveTiles {
    public DisappearingTile(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
    }
}
