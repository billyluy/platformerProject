package com.platformer.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platformer.game.platformerGame;

/**
 * Created by Student6 on 5/26/2017.
 */

public class DisappearingTile  {
    public DisappearingTile(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef  = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Body body;
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(((rect.getX() + rect.getWidth() / 2) / platformerGame.PPM), ((rect.getY() + rect.getHeight() / 2) / platformerGame.PPM));
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / platformerGame.PPM, rect.getHeight() / 2 / platformerGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("disappear");
        }
    }
}
