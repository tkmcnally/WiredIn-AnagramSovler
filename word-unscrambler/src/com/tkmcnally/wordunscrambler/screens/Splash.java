package com.tkmcnally.wordunscrambler.screens;

import java.io.IOException;

import javax.xml.transform.Result;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tkmcnally.wordunscrambler.MyGdxGame;
import com.tkmcnally.wordunscrambler.sprites.SpriteAccessor;
import com.tkmcnally.wordunscrambler.unscrambler.Unscrambler;

public class Splash implements Screen {

	private MyGdxGame game;
	
	private SpriteBatch batch;
	private Texture backgroundTexture, logoTexture;
	private Stage stage;
	private TweenManager tweenManager;
	private Sprite background, logo;
	private MainMenu menu;
	private TweenCallback cb, cb2;
	private boolean doneSplash = false;
	
	 public Splash(MyGdxGame game){
         this.game = game;
	 }
 
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		
		if(doneSplash == true && Unscrambler.loaded) {
			Tween.to(logo, SpriteAccessor.ALPHA, 0.2f).setCallback(cb2).target(0).start(tweenManager);
		}
		
		//DRAW STUFF
		batch.begin();
		
		background.draw(batch);
		logo.draw(batch);
		
		batch.end();
		//END DRAWING STUFF
	
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		tweenManager = new TweenManager(); 
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		backgroundTexture = new Texture(Gdx.files.internal("data/bg_1920-1080.png"));
		background = new Sprite(backgroundTexture);
		
		logoTexture = new Texture(Gdx.files.internal("data/wiredin.png"));
		logo = new Sprite(logoTexture);
		logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 , Gdx.graphics.getHeight());
		createCallback();
	//	Tween.set(logo, SpriteAccessor.POSITION).target(100, 100).start(tweenManager);
		//Tween.to(logo, SpriteAccessor.POSITION, 1).delay(0.1f).target(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 , Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3).start(tweenManager);
		Timeline.createSequence()
			.push(Tween.to(logo, SpriteAccessor.POSITION, 0.8f).delay(0.5f).target(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 , Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3))
			.push(Tween.to(logo, SpriteAccessor.POSITION, 0.3f).setCallback(cb).target(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 ,(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3) + 15))
			.start(tweenManager);
		//	Tween.to(logo, SpriteAccessor.POSITION, 1).delay(0.1f).target(logo.getX(), logo.getY() + 10).start(tweenManager);
		
		new Thread(new Runnable() {
		     public void run() {
		         // Do something on the main thread
		         //Unscrambler.setupMap();
		         
		     }
		  }).start();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void createCallback() {
		cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {		
					doneSplash = true;	
				//menu = new MainMenu(game);
				//game.setScreen(menu);
			}
		};
		
		cb2 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {		
				menu = new MainMenu(game);
				game.setScreen(menu);
			}
		};
		
	}

}
