package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Screens.PlayScreen;

public class platformerGame extends Game {

	public SpriteBatch batch;
	public static final int gameWidth = 1280;
	public static final int gameHeight = 960;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short GROUND_BIT = 4;
	public static final short SPIKE_BIT = 8;
	public static final short DISAPPERING_BIT = 16;
	public static final short DESTROY_BIT = 32;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
