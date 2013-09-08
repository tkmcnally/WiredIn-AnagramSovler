package com.tkmcnally.wordunscrambler.screens;

import java.io.IOException;

import javax.xml.transform.Result;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	private boolean doneSplash = false, dbLoaded = false, transitionStarted = false, screenUp = false, update = false;
	
	 public Splash(MyGdxGame game){
         this.game = game;
	 }
 
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		if(update)
			tweenManager.update(Gdx.graphics.getDeltaTime());
		
		
		//DRAW STUFF
		batch.begin();
		
		background.draw(batch);
		logo.draw(batch);
		
		batch.end();
		//END DRAWING STUFF

		
			if(game.manager.update() && batch.totalRenderCalls > 2) {
				if(!screenUp) {
					Gdx.app.postRunnable(new Runnable() {
				         @Override
				         public void run() {
				        	 loadMenu();

				         }
				      });
				}
				if(!dbLoaded) {
					dbLoaded=true;
					
					Timeline.createSequence()
				
					.push(Tween.to(logo, SpriteAccessor.POSITION, 3f).ease(Linear.INOUT).target(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 , Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3))
					.push(Tween.to(logo, SpriteAccessor.POSITION, 1f).setCallback(cb).target(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 ,(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3) + 15))
					
					.start(tweenManager);
					update = true;
					
				}
				
			}
			
			
		
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		game.manager.load("data/uiskin.json", Skin.class, new SkinLoader.SkinParameter("data/uiskin.atlas"));
	    game.manager.load("data/bg_1920-1080.png", Texture.class);
	    game.manager.load("data/wiredin.png", Texture.class);
	    game.manager.finishLoading();
		batch = new SpriteBatch();
		tweenManager = new TweenManager(); 
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		backgroundTexture = game.manager.get("data/bg_1920-1080.png");
		background = new Sprite(backgroundTexture);
		
		logoTexture =  game.manager.get("data/wiredin.png");
		logo = new Sprite(logoTexture);
		logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 3 , Gdx.graphics.getHeight() + 900);
		//createCallback();
		
		

			      
			      
	    
			 
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
			}
		};
		
		cb2 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {	
				long start = System.currentTimeMillis();
			
				//game.setScreen(menu);
				long end = System.currentTimeMillis();
				
				System.out.println(end - start);
			}
		};
		
	}
	
	public void loadMenu() {
		screenUp = true;
		long start = System.currentTimeMillis();

	
		menu = new MainMenu(game);
		menu.loadFont();
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		Unscrambler.dbConnection = game.mActionResolver.getConnection();
	
	
	}

}
