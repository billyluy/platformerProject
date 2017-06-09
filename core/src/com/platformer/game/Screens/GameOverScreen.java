package com.platformer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.platformerGame;

/**
 * Created by Student 6 on 6/7/2017.
 */

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private float playerX;
    private float playerY;

    public GameOverScreen(Game game, float x, float y) {
        this.game = game;
        viewport = new FitViewport(platformerGame.gameWidth, platformerGame.gameHeight, new OrthographicCamera());
        stage = new Stage(viewport, ((platformerGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Click to Play Again", font);

        gameOverLabel.setFontScale(10);
        playAgainLabel.setFontScale(5);
        table.add(gameOverLabel).expand();
        table.row();
        table.add(playAgainLabel).expand().padTop(10f);

        stage.addActor(table);
        playerX = x;
        playerY = y;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            //later replace with save button coordinates
            game.setScreen(new PlayScreen((platformerGame) game, playerX, playerY));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
