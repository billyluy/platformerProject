package com.platformer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/30/2017.
 */

public class Spikes{

    protected Fixture fixture;

    public Spikes(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Body body;

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(PolylineMapObject.class)) {
            Polyline poly = ((PolylineMapObject) object).getPolyline();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(0, 0);
            body = world.createBody(bdef);

            float vertices[] = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
            for(int i = 0; i < vertices.length; i ++){
                vertices[i] /= platformerGame.PPM;
            }
            ChainShape shape2 = new ChainShape();
            shape2.createChain(vertices);
            fdef.shape = shape2;
            fixture = body.createFixture(fdef);
            fixture.setUserData("spike");
        }
    }
}
