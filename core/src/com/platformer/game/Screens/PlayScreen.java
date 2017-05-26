package com.platformer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.Scenes.Hud;
import com.platformer.game.Sprites.Player;
import com.platformer.game.platformerGame;

/**
 * Created by User on 5/20/2017.
 */

public class PlayScreen implements Screen{

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
    private Player player;


    public PlayScreen(platformerGame game) {
        this.game = game;
        //cam to follow user through map
        gamecam = new OrthographicCamera();
        //maintain aspect ratio for screens
        port = new FitViewport(platformerGame.gameWidth/platformerGame.PPM, platformerGame.gameHeight/platformerGame.PPM, gamecam);
        //adds a hud
        hud = new Hud(game.batch);

        //render map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("gamemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/platformerGame.PPM);
        //map starts at bottom left corner
        gamecam.position.set(((port.getWorldWidth()/2)/platformerGame.PPM)+port.getWorldWidth()/2, (32000 - (port.getWorldHeight() / 2))/platformerGame.PPM - port.getWorldHeight()/2, 0);
//        gamecam.position.set(port.getWorldWidth()/2,port.getWorldHeight()/2,0);

        //box2d
        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10), true);
        player = new Player(world);
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //body/fixtures for rectangular object layers
        for (int i = 0; i < 11; i++) {
            for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(((rect.getX() + rect.getWidth() / 2)/platformerGame.PPM), ((rect.getY() + rect.getHeight() / 2)/platformerGame.PPM));
                body = world.createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2/platformerGame.PPM, rect.getHeight() / 2/platformerGame.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);
            }
        }

        //body/fixtures for polyline spikes CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF CANCER AF
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(PolylineMapObject.class)) {
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

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        //overrides the gamecam position to follow the player's position
        gamecam.position.x = player.body.getPosition().x;
//        gamecam.position.y = player.body.getPosition().y;
        gamecam.update();
        renderer.setView(gamecam);
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 4)
            player.body.applyLinearImpulse(new Vector2(0.2f, 0), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -4)
            player.body.applyLinearImpulse(new Vector2(-0.2f, 0), player.body.getWorldCenter(), true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
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
