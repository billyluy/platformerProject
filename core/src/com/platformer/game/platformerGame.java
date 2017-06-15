package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Screens.PlayScreen;

public class platformerGame extends Game {

    public static SpriteBatch batch;
    public static final int gameWidth = 1800;
    public static final int gameHeight = 960;
    public static final float PPM = 100;

    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short DESTROY_BIT = 4;
    public static final short SPIKE_BIT = 8;
    public static final short DISAPPERING_BIT = 16;
    public static final short COIN_BIT = 32;
    public static final short MOVESPIKE_BIT = 64;
    public static final short BALL_BIT = 128;
    public static final short SAVE_BIT = 256;

    public float initialX;
    public float initialY;

//    public AssetManager manager;

    @Override
    public void create() {
//        manager = new AssetManager();
//        manager.load("audio/bg.mp3", Music.class);
//        manager.load("audio/jump.mp3", Sound.class);
//        manager.finishLoading();
        initialX = 128 / platformerGame.PPM;
        initialY = 31164 / platformerGame.PPM;
//        initialX = 3500 / platformerGame.PPM;
//        initialY = 28414 / platformerGame.PPM;
//        initialX = 775 / platformerGame.PPM;
//        initialY = 30030 / platformerGame.PPM;
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this, initialX, initialY));
    }

    @Override
    public void render() {
        super.render();
    }
}
