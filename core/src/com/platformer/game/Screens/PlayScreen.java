package com.platformer.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.Scenes.Controller;
import com.platformer.game.Scenes.Hud;
import com.platformer.game.Sprites.BallSpike;
import com.platformer.game.Sprites.Coin;
import com.platformer.game.Sprites.DisappearingTile;
import com.platformer.game.Sprites.DownSpike;
import com.platformer.game.Sprites.LeftSpike;
import com.platformer.game.Sprites.Player;
import com.platformer.game.Sprites.Save;
import com.platformer.game.Sprites.UpSpike;
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
    private ArrayList<UpSpike> upSpikes;
    private ArrayList<LeftSpike> leftSpikes;
    private ArrayList<BallSpike> ballSpikes;
    private TextureAtlas atlas;
    private Controller controller;
    private float playerX;
    private float playerY;
    private float saveFrameTime;
    private ArrayList<Save> saves;
    private float coinFrameTime;
    private ArrayList<Coin> coins;
    private ArrayList<DisappearingTile> disappearingTiles;
    private Music music;
    private Sound sound;
    private Sound sound2;
    public AssetManager manager;


    public PlayScreen(platformerGame game, float x, float y) {
        manager = new AssetManager();
        manager.load("audio/bg.mp3", Music.class);
        manager.load("audio/jump.mp3", Sound.class);
        manager.load("audio/death.mp3", Sound.class);
        manager.finishLoading();
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
//        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -10), true);
        player = new Player(this, x, y);
        playerX = x;
        playerY = y;
        disappearingTiles = new ArrayList<DisappearingTile>();
        coins = new ArrayList<Coin>();
        coinFrameTime = 0;
        saves = new ArrayList<Save>();
        saveFrameTime = 0;
        new B2WorldCreator(this);

        downSpikes = new ArrayList<DownSpike>();
        //first level spikes
        downSpikes.add(new DownSpike(this, 3618 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));
        downSpikes.add(new DownSpike(this, 3682 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));
        downSpikes.add(new DownSpike(this, 3746 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));
        downSpikes.add(new DownSpike(this, 3807 / platformerGame.PPM, 31903 / platformerGame.PPM, 310));

        upSpikes = new ArrayList<UpSpike>();
        upSpikes.add(new UpSpike(this, 2016 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));
        upSpikes.add(new UpSpike(this, 2080 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));
        upSpikes.add(new UpSpike(this, 2144 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));
        upSpikes.add(new UpSpike(this, 2208 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));
        upSpikes.add(new UpSpike(this, 2272 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));
        upSpikes.add(new UpSpike(this, 2336 / platformerGame.PPM, 29344 / platformerGame.PPM, 310));

        leftSpikes = new ArrayList<LeftSpike>();
        leftSpikes.add(new LeftSpike(this, 3805 / platformerGame.PPM, 30240 / platformerGame.PPM, 301));

        ballSpikes = new ArrayList<BallSpike>();
        ballSpikes.add(new BallSpike(this, 3000 / platformerGame.PPM, 31903 / platformerGame.PPM, 310,0,-8));
        ballSpikes.add(new BallSpike(this, 3780 / platformerGame.PPM, 28440 / platformerGame.PPM, 283,-8,0));
        ballSpikes.add(new BallSpike(this, 1380 / platformerGame.PPM, 28414 / platformerGame.PPM, 283,0,-7));
        ballSpikes.add(new BallSpike(this, 1107 / platformerGame.PPM, 28414 / platformerGame.PPM, 283,0,-7));
        ballSpikes.add(new BallSpike(this, 551 / platformerGame.PPM, 28830 / platformerGame.PPM, 283,-7,0));

        world.setContactListener(new WorldContactListener(player));
        controller = new Controller();

        music = manager.get("audio/bg.mp3", Music.class);
        sound = manager.get("audio/jump.mp3", Sound.class);
        sound2 = manager.get("audio/death.mp3", Sound.class);
        music.setLooping(true);
        music.setVolume(1);
        music.play();
    }

    public void addDissapear(DisappearingTile d) { disappearingTiles.add(d); }

    public void addCoins(Coin c) { coins.add(c); }

    public void addSaves(Save s) {
        saves.add(s);
    }

    public void setPlayerX(float x) {
        playerX = x;
    }

    public void setPlayerY(float y) {
        playerY = y;
    }

    public Player getPlayer() {
        return player;
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
//        if(player.getIsDead()){
//            sound2.play();
//        }
        if(player.getY()>310){
            gamecam.position.y = ((32010 - (port.getWorldHeight() / 2)) / platformerGame.PPM - port.getWorldHeight()/2);
        }
        if(player.getY()<310 && player.getY()>301 && !(player.getIsDead())){
            gamecam.position.y = (32010 - (port.getWorldHeight() / 2)) / platformerGame.PPM - port.getWorldHeight()/2 -(port.getWorldHeight()-(64/platformerGame.PPM));
        }
        if(player.getY()<301 && player.getY()>292 && !(player.getIsDead())){
            gamecam.position.y = (32010 - (port.getWorldHeight() / 2)) / platformerGame.PPM - port.getWorldHeight()/2 -(2*(port.getWorldHeight()-(64/platformerGame.PPM)));
        }
        if(player.getY()<292 && player.getY()>283 && !(player.getIsDead())){
            gamecam.position.y = (32010 - (port.getWorldHeight() / 2)) / platformerGame.PPM - port.getWorldHeight()/2 -(3*(port.getWorldHeight()-(64/platformerGame.PPM)));
        }
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
            sound.play();
            manager.finishLoading();
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
            sound.play();
            manager.finishLoading();
            player.body.applyLinearImpulse(new Vector2(0, 5f), player.body.getWorldCenter(), true);
            player.jump = 0;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        for(DisappearingTile d : disappearingTiles) {
            d.upodateVelocity();
        }
        saveFrameTime += delta;
        coinFrameTime += delta;
        if(saveFrameTime > .5) {
            saveFrameTime = 0;
            for (Save s : saves)
                if(!s.getTouched())
                    s.changeTile();
        }
        if(coinFrameTime > .15) {
            coinFrameTime = 0;
            for (Coin c : coins)
                if(!c.getTouched())
                    c.changeTile();
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
//        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        drawSpikes();
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, playerX, playerY));
            dispose();
        }
    }

    public void drawSpikes() {
        for (DownSpike d : downSpikes)
            if (!d.getDestroyed()) {
                d.draw(game.batch);
            }
        for (LeftSpike l : leftSpikes)
            if (!l.getDestroyed()) {
                l.draw(game.batch);
            }
        for(BallSpike b: ballSpikes)
            if(!b.getDestroyed()){
                b.draw(game.batch);
            }
        for(UpSpike u : upSpikes)
            if(!u.getDestroyed())
                u.draw(game.batch);
    }

    public void updateSpikes(float dt) {
        for (DownSpike d : downSpikes)
            d.update(dt);
        for (LeftSpike l : leftSpikes)
            l.update(dt);
        for (BallSpike b : ballSpikes)
            b.update(dt);
        for(UpSpike u : upSpikes)
            u.update(dt);
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
//        b2dr.dispose();
        hud.dispose();
        manager.dispose();
    }
}
