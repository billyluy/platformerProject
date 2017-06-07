package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Screens.PlayScreen;

public class platformerGame extends Game {

	public static SpriteBatch batch;
	public static final int gameWidth = 1280;
	public static final int gameHeight = 960;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short DESTROY_BIT = 4;
	public static final short SPIKE_BIT = 8;
	public static final short DISAPPERING_BIT = 16;
	public static final short COIN_BIT = 32;
	public static final short MOVESPIKE_BIT = 64;

	public float initialX;
	public float initialY;
	
	@Override
	public void create () {
		initialX = 128/platformerGame.PPM;
		initialY = 31164/platformerGame.PPM;
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this, initialX, initialY));
	}

	@Override
	public void render () {
		super.render();
	}
}
