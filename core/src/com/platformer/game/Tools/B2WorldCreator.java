package com.platformer.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.Sprites.Ground;
import com.platformer.game.Sprites.Spikes;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/23/2017.
 */

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){
        //body/fixtures for rectangular object layers
        new Ground(world,map);
        //body/fixtures for polyline spikes
        new Spikes(world,map);
    }
}
