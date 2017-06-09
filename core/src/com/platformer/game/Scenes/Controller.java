package com.platformer.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.platformerGame;

/**
 * Created by User on 6/3/2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean leftPress, rightPress, upPress;
    OrthographicCamera cam;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(platformerGame.gameWidth, platformerGame.gameHeight, cam);
        stage = new Stage(viewport, platformerGame.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image left = new Image(new Texture("control/left.png"));
        left.setSize(100, 100);
        left.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPress = false;
            }
        });

        Image right = new Image(new Texture("control/right.png"));
        right.setSize(100, 100);
        right.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPress = false;
            }
        });

        Image up = new Image(new Texture("control/up.png"));
        up.setSize(100, 100);
        up.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPress = false;
            }
        });

        table.add(left).size(left.getWidth(), left.getHeight());
        table.add().pad(30, 30, 30, 30);
        table.add(right).size(right.getWidth(), right.getHeight());
        table.add().pad(0, 400, 0, 400);
        table.add(up).size(up.getWidth(), up.getHeight());
        table.pad(30, 30, 30, 30);


        stage.addActor(table);

    }

    public void draw() {
        stage.draw();
    }

    public boolean isLeftPressed() {
        return leftPress;
    }

    public boolean isRightPressed() {
        return rightPress;
    }

    public boolean isUpPress() {
        return upPress;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
