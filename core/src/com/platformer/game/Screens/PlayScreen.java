package com.platformer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

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
        map = mapLoader.load("marioTest/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(port.getWorldWidth()/2,port.getWorldHeight()/2,0);
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
            gamecam.position.x += 100*dt;
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        renderer.render();
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
