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
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/23/2017.
 */

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //body/fixtures for rectangular object layers
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Ground(world,map,rect);

        }

        //body/fixtures for polyline spikes CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF
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
            body.createFixture(fdef);
        }


    }
}
