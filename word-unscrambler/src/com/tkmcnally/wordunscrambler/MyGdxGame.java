package com.tkmcnally.wordunscrambler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.tkmcnally.wordunscrambler.screens.MainMenu;
import com.tkmcnally.wordunscrambler.screens.Splash;
import com.tkmcnally.wordunscrambler.sqlite.ActionResolver;


public class MyGdxGame extends Game {
	public ActionResolver mActionResolver;

	public AssetManager manager = new AssetManager();

	
	
	private MainMenu menu;
	private Splash splash;

	
	public MyGdxGame(ActionResolver ar) {
		this.mActionResolver = ar;
	}

	@Override
	public void create() {
		//menu = new MainMenu(this);
		splash = new Splash(this);
		setScreen(splash);

	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}


	//Setting current active Screen implementation.
	@Override
	public void setScreen(Screen screen)
	{
		super.setScreen(screen);
		super.render();
	}

}
