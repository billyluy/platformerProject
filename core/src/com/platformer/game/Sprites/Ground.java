package com.platformer.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/30/2017.
 */

public class Ground extends InteractiveTiles{
    public Ground(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef  = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(((bounds.getX() + bounds.getWidth() / 2)/ platformerGame.PPM), ((bounds.getY() + bounds.getHeight() / 2)/platformerGame.PPM));
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2/platformerGame.PPM, bounds.getHeight() / 2/platformerGame.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
