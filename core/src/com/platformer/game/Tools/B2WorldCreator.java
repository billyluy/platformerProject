package com.platformer.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Student6 on 5/23/2017.
 */

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){
        //box2d

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //gets certain objects by layer used for collision

        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            //setting the player
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

/*
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            //setting the player
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            //setting the player
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            //setting the player
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            //setting the player
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY() / 2, rect.getY() + rect.getHeight() / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }                                   */
    }
}
