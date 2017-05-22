package com.platformer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.Scenes.Hud;
import com.platformer.game.platformerGame;

/**
 * Created by User on 5/20/2017.
 */

public class PlayScreen implements Screen {

    private platformerGame game;
    private OrthographicCamera gamecam;
    private Viewport port;
    private Hud hud;
    //map loader
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //box2D(collision and physics)
    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(platformerGame game){
        this.game = game;
        //cam to follow user through map
        gamecam = new OrthographicCamera();
        //maintain aspect ratio for screens
        port = new FitViewport(platformerGame.gameWidth,platformerGame.gameHeight,gamecam);
        //adds a hud
        hud = new Hud(game.batch);

        //render map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("gamemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(port.getWorldWidth()/2,32000-(port.getWorldHeight()/2),0);

        //-----------------------MARIO TEST-----------------------------------//
//        gamecam.position.set(port.getWorldWidth()/2,port.getWorldHeight()/2,0);
        //-----------------------MARIO TEST-----------------------------------//

        //box2d
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //gets certain objects by layer used for collision


        /*-------------------------------------MARIO TEST GAME--------------------------------------------*/
//        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject)object).getRectangle();
//            //setting the player
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
//            body = world.createBody(bdef);
//            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
//        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject)object).getRectangle();
//            //setting the player
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
//            body = world.createBody(bdef);
//            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
//        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject)object).getRectangle();
//            //setting the player
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
//            body = world.createBody(bdef);
//            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
//        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            //setting the player
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getY() / 2, rect.getY() + rect.getHeight() / 2);
//            body = world.createBody(bdef);
//            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
        /*-------------------------------------MARIO TEST GAME--------------------------------------------*/

        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            System.out.println("ran");
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getY()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecam.position.x += 1000*dt;
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
