package com.platformer.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.platformerGame;

/**
 * Created by User on 5/20/2017.
 */

public class Hud implements Disposable{

    public Stage stage;
    private Viewport port;
    Label worldLabel;

    public Hud(SpriteBatch sb){
        port = new FitViewport(platformerGame.gameWidth,platformerGame.gameHeight,new OrthographicCamera());
        stage = new Stage(port, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        worldLabel = new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(worldLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
