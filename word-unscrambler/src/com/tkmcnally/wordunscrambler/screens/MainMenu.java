package com.tkmcnally.wordunscrambler.screens;

import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sun.font.TrueTypeFont;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tkmcnally.wordunscrambler.MyGdxGame;
import com.tkmcnally.wordunscrambler.sprites.TextFieldAccessor;
import com.tkmcnally.wordunscrambler.unscrambler.Unscrambler;

public class MainMenu implements Screen {

	private static final int MAX_RESULTS = 5;

	private MyGdxGame game;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private Image background;
	private BitmapFont font,font1;
	private OrthographicCamera camera;
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;
	private float[] tfOrigPos;
	private TextField[] newFields = new TextField[MAX_RESULTS];
	private TweenCallback cb1;
	private boolean shown = false;
	
	private HashMap<Integer, List<String>> unscrambledEntries;

	private int RESULT_PAGE_NUM = 0;

	private Skin skin;

	private Label scrambledLabel;
	private Label unscrambledLabel;

	private TextField input;

	private TextButton inputButton;
	private int screen_H = Gdx.graphics.getHeight();
	private int screen_W = Gdx.graphics.getWidth();


	// constructor to keep a reference to the main Game class
	public MainMenu(MyGdxGame game){
	
		this.game = game;
		skin = game.manager.get("data/uiskin.json");
		scrambledLabel = new Label("Scrambled Word:", skin);
		unscrambledLabel = new Label("Unscrambled Word:", skin);
		inputButton = new TextButton("Unscramble!", skin);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);		
	}
	
	public void loadTextures() {
		
		
		
		//////////////////////////////////////////////
		
		

	}
	
	public void loadFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/CALIBRI.TTF"));
		font = generator.generateFont(72); 
		font1 = generator.generateFont(60);// font size 25 pixels
		generator.dispose();
	}

	public void createCallback(String[] unscrambled) {

		cb1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
			
			}
		};

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		batch.dispose();

	}

	public boolean isMoreResults() {
		//int fieldNum = unscrambled.size() - (MAX_RESULTS * (RESULT_PAGE_NUM + 1)) - 1;
	//	if(fieldNum >= 1) {
		//	return true;
	//	}

		return false;
	}

	public boolean isPrevResults() {
		if(RESULT_PAGE_NUM != 0) {
			return true;
		}

		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);

		//	camera.update();
		//	batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//DRAW STUFF

		//font.draw(batch,Unscrambler.unscramble(),  screen_W / 2,  screen_H / 2);
		//background.draw(batch);
		stage.act();
		stage.draw();
		Table.drawDebug(stage);
		
		//END DRAWING STUFF
		batch.end();

	}

	public void resetTable() {
		stage.clear();
		table = new Table();
		table.top().pad(10, 0, 0, 0);
		
		stage.addActor(background);
		stage.addActor(table);

		//Row 1
		table.add(scrambledLabel).right().pad(2, 0, 2, 10);
		table.add(input).height(screen_W * 0.15f).width(screen_W * 0.7f).pad(2, 0, 2, 0);
		table.row();

		//Row 2
		//	table.add(unscrambledLabel).pad(2, 10, 2, 10);
		//	table.add(output).pad(2, 0, 2, 0);
		//	table.row();

		tfOrigPos = new float[4];


		//Row 3
		table.add();
		table.add(inputButton).height((float) (screen_H * 0.05)).width((float) (screen_W * 0.3)).pad(2, 0, 2, 0);

		table.setFillParent(true);
		table.debug(); // turn on all debug lines (table, cell, and widget)
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(TextField.class, new TextFieldAccessor());

		camera = new OrthographicCamera(screen_W, screen_H);
		camera.setToOrtho(false, screen_W, screen_H);



		batch = new SpriteBatch();
		
		
		//font12 = generator.generateFont(12); // font size 12 pixels
		 // don't forget to dispose to avoid memory leaks
		
		//font = new BitmapFont();
		//font1 = new BitmapFont();
		TextFieldStyle ts = new TextFieldStyle();
		ts.font = font1;
		ts.background = skin.getDrawable("textfield");
		ts.fontColor = Color.WHITE;
		input = new TextField("", ts);
		
		backgroundTexture = game.manager.get("data/bg_1920-1080.png");
		background = new Image(backgroundTexture);


		inputButton.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				try {
					unscrambledEntries = Unscrambler.unscrambleWord(game, input.getText().trim());
					Iterator<Integer> iter = unscrambledEntries.keySet().iterator();
					final Table scrollTable = new Table();
					int count = 0;
					while(iter.hasNext()) {
						Integer index = iter.next();
						count = count + unscrambledEntries.get(index).size();
						//for(int i = 0; i < unscrambledEntries.get(index).size(); i++) {
							
						//}
					}
					//Reset Table to original state.
					resetTable();
					
					//Create labels for all solved words.
					Label[] label = new Label[count];
					
					//Create iterator for solved entries.
					iter = unscrambledEntries.keySet().iterator();
					
					//Create style for Label (Font, Color etc...)
					LabelStyle lts = new LabelStyle();
					lts.font = font;
					
					//Populate labels with solved words and add to new ScrollTable.
					while(iter.hasNext()) {
						Integer index = iter.next();
						for(int i = 0; i < unscrambledEntries.get(index).size(); i++) {
							label[i] = new Label(unscrambledEntries.get(index).get(i), lts);
							label[i].scale(5);
							scrollTable.add(label[i]);
						    scrollTable.row();
							
						}
					}
					
					
					resetTable();
					
					//Add scrollPane to main Table.
					ScrollPaneStyle sps = skin.get(ScrollPaneStyle.class);
					sps.background = null;
					ScrollPane scrollPane = new ScrollPane(scrollTable, sps);
					table.row();
					table.add(scrollPane).width((float) (screen_W * 0.8)).height((float) (screen_H * 0.5)).colspan(2);
					table.debug();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		//stage.setCamera(camera);
		stage.addActor(background);

		table = new Table();
		stage.addActor(table);
		table.top().pad(10, 0, 0, 0);
		//Row 1
		table.add(scrambledLabel).pad(2, 0, 2, 10);
		table.add(input).height(screen_W * 0.15f).width(screen_W * 0.7f).pad(2, 0, 2, 0);
		table.row();

		//Row 2
		//	table.add(unscrambledLabel).pad(2, 10, 2, 10);
		//	table.add(output).pad(2, 0, 2, 0);
		//	table.row();

		tfOrigPos = new float[4];

	

		//Row 3
		table.add();
		table.add(inputButton).height((float) (screen_H * 0.05)).width((float) (screen_W * 0.3)).pad(2, 0, 2, 0);
		

	

		table.setFillParent(true);
		//	table.debug(); // turn on all debug lines (table, cell, and widget)

	

		//	Sprite test = new Sprite(output);
	}
}
