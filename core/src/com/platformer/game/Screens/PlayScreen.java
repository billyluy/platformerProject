package com.platformer.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.Scenes.Controller;
import com.platformer.game.Scenes.Hud;
import com.platformer.game.Sprites.DownSpike;
import com.platformer.game.Sprites.LeftSpike;
import com.platformer.game.Sprites.MoveSpike;
import com.platformer.game.Sprites.Player;
import com.platformer.game.Tools.B2WorldCreator;
import com.platformer.game.Tools.WorldContactListener;
import com.platformer.game.platformerGame;

import java.util.ArrayList;

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
    private Player player;
    private ArrayList<DownSpike> downSpikes;
    private ArrayList<LeftSpike> leftSpikes;
    private TextureAtlas atlas;
    private Controller controller;


    public PlayScreen(platformerGame game, float x, float y) {
        atlas = new TextureAtlas("player.pack");
        this.game = game;
        //cam to follow user through map
        gamecam = new OrthographicCamera();
        //maintain aspect ratio for screens
        port = new FitViewport(platformerGame.gameWidth / platformerGame.PPM, platformerGame.gameHeight / platformerGame.PPM, gamecam);
        //adds a hud
        hud = new Hud(game.batch);
        //render map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("gamemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / platformerGame.PPM);
        //map starts at bottom left corner
        gamecam.position.set(((port.getWorldWidth() / 2) / platformerGame.PPM) + port.getWorldWidth() / 2, (32000 - (port.getWorldHeight() / 2)) / platformerGame.PPM - port.getWorldHeight() / 2, 0);
        //box2d
        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10), true);
        new B2WorldCreator(this);
        player = new Player(this, x, y);
        downSpikes = new ArrayList<DownSpike>();
        //first level spikes
        downSpikes.add(new DownSpike(this, 3170 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));
        downSpikes.add(new DownSpike(this, 3234 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));
        downSpikes.add(new DownSpike(this, 3298 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));

        leftSpikes = new ArrayList<LeftSpike>();
        leftSpikes.add(new LeftSpike(this, 3170 / platformerGame.PPM, 31500 / platformerGame.PPM, 310));

        world.setContactListener(new WorldContactListener(player));
        controller = new Controller();
    }

    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 2) {
            return true;
        } else {
            return false;
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);
        player.update(dt);
        updateSpikes(dt);
        world.step(1 / 60f, 6, 2);
        gamecam.position.x = player.body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }

    public void handleInput(float dt) {
        //Key Press
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.body.getLinearVelocity().y == 0 || player.jump > 0)) {
            player.body.applyLinearImpulse(new Vector2(0, 5f), player.body.getWorldCenter(), true);
            player.jump = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 4)
            player.body.applyLinearImpulse(new Vector2(0.2f, 0), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -4)
            player.body.applyLinearImpulse(new Vector2(-0.2f, 0), player.body.getWorldCenter(), true);

        //Controller Press
        if (controller.isRightPressed() && player.body.getLinearVelocity().x <= 4)
            player.body.applyLinearImpulse(new Vector2(0.2f, 0), player.body.getWorldCenter(), true);
        if (controller.isLeftPressed() && player.body.getLinearVelocity().x >= -4)
            player.body.applyLinearImpulse(new Vector2(-0.2f, 0), player.body.getWorldCenter(), true);
        if (controller.isUpPress() && (player.body.getLinearVelocity().y == 0 || player.jump > 0)) {
            player.body.applyLinearImpulse(new Vector2(0, 5f), player.body.getWorldCenter(), true);
            player.jump = 0;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        drawSpikes();
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public void drawSpikes() {
        for (DownSpike d : downSpikes)
            if (!d.getDestroyed()) {
                d.draw(game.batch);
            }
        for (LeftSpike d : leftSpikes)
            if (!d.getDestroyed()) {
                d.draw(game.batch);
            }
    }

    public void updateSpikes(float dt) {
        for (DownSpike d : downSpikes)
            d.update(dt);
        for (LeftSpike d : leftSpikes)
            d.update(dt);
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
        controller.resize(width, height);
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

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
