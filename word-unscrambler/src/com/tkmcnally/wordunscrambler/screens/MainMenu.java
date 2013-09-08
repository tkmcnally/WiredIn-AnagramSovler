package com.tkmcnally.wordunscrambler.screens;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
	
	private HashMap<Integer, List<String>> unscrambledEntries;

	private int RESULT_PAGE_NUM = 0;

	final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

	final Label scrambledLabel = new Label("Scrambled Word:", skin);
	final Label unscrambledLabel = new Label("Unscrambled Word:", skin);

	final TextField input = new TextField("",skin);

	final TextButton inputButton = new TextButton("Unscramble!", skin);
	final TextButton nextResults = new TextButton("More Results", skin);
	final TextButton prevResults = new TextButton("Prev Results", skin);

	// constructor to keep a reference to the main Game class
	public MainMenu(MyGdxGame game){
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Unscrambler.dbConnection = game.mActionResolver.getConnection();
		
	}


	public void createCallback(String[] unscrambled) {

		cb1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
			//	int fieldNum = unscrambled.size() - (MAX_RESULTS * RESULT_PAGE_NUM) - 1;
			//	int numFields = unscrambled.size();
				
			//	newFields[0].setText(unscrambled.toString());
/*
				if(fieldNum > MAX_RESULTS) {
					numFields = MAX_RESULTS;
				}

				if(RESULT_PAGE_NUM != 0) {
					if(fieldNum > MAX_RESULTS) {
						numFields = MAX_RESULTS;
					} else {
						numFields = MAX_RESULTS - fieldNum;
					}

				}

				for(int i = 0; i < numFields; i++) {
					if(RESULT_PAGE_NUM == 0) {
						for(int k = 0; k < unscrambled.get(4).size(); k++ ) {
							newFields[k].setText(unscrambled.get(4).get(k));
						}
						//newFields[i].setText(unscrambled.get(i).get(i));
					}
					//newFields[i].setText(unscrambled.get(i + (MAX_RESULTS * RESULT_PAGE_NUM)));
				}
				*/
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

		//font.draw(batch,Unscrambler.unscramble(),  Gdx.graphics.getWidth() / 2,  Gdx.graphics.getHeight() / 2);
		//background.draw(batch);
		stage.act();
		stage.draw();
		//Table.drawDebug(stage);

		//END DRAWING STUFF
		batch.end();

	}

	public void resetTable() {

		stage.clear();
		table = new Table();

		stage.addActor(background);
		stage.addActor(table);

		//Row 1
		table.add(scrambledLabel).right().pad(2, 0, 2, 10);
		table.add(input).width(Gdx.graphics.getWidth() * 0.4f).pad(2, 0, 2, 0);
		table.row();

		//Row 2
		//	table.add(unscrambledLabel).pad(2, 10, 2, 10);
		//	table.add(output).pad(2, 0, 2, 0);
		//	table.row();

		tfOrigPos = new float[4];


		//Row 3
		table.add();
		table.add(inputButton).pad(2, 0, 2, 0);

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

		camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		batch = new SpriteBatch();
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/CALIBRI.TTF"));
		//font12 = generator.generateFont(12); // font size 12 pixels
		font = generator.generateFont(72); // font size 25 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks
		
		//font = new BitmapFont();
		//font1 = new BitmapFont();

		backgroundTexture = new Texture(Gdx.files.internal("data/bg_1920-1080.png"));
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
					
					Label[] label = new Label[count];
					iter = unscrambledEntries.keySet().iterator();
					while(iter.hasNext()) {
						Integer index = iter.next();
						for(int i = 0; i < unscrambledEntries.get(index).size(); i++) {
							label[i] = new Label(unscrambledEntries.get(index).get(i), skin);
							label[i].scale(5);
							//System.out.println(unscrambledEntries.get(index).get(i));
							scrollTable.add(label[i]);
						    scrollTable.row();
							
						}
					}
					ScrollPane scrollPane = new ScrollPane(scrollTable, skin);
					table.row();
					table.add(scrollPane).width((float) (Gdx.graphics.getWidth() * 0.8)).height((float) (Gdx.graphics.getHeight() * 0.5));
				
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//	final Table scrollTable = new Table();
		
				
				
				
			/*	try {
					RESULT_PAGE_NUM = 0;
			
					//unscrambled = Unscrambler.unscrambleWord(game, input.getText().trim());
					
					resetTable();

					newFields = new TextField[MAX_RESULTS];
					createCallback(unscrambled);



					int numFields = unscrambled.size();
					//if(unscrambled.size() > MAX_RESULTS) {
						numFields = MAX_RESULTS;
					}

					for(int i = 0; i < numFields; i++) {
						newFields[i] = new TextField("", skin);

						table.getCell(input).row();
						if(i == 0) {
							table.add(unscrambledLabel).pad(2, 0, 2, 10);
						} else {
							table.add();
						}

						table.add(newFields[i]).width(Gdx.graphics.getWidth() * 0.4f).pad(2, 0, 2, 0);

						newFields[i].setColor(newFields[i].getColor().r, newFields[i].getColor().g, newFields[i].getColor().b, 0);
						newFields[i].setDisabled(true);
						Tween.to(newFields[i], TextFieldAccessor.ALPHA, 0.2f).target(1).setCallback(cb1).start(tweenManager);


						//output.setText(output.getText() + " " + unscrambled.get(i));
					}
					table.row();
					if(isPrevResults()){
						table.add(prevResults);
					}
					if(isMoreResults()) {
						table.add(nextResults);
					}
					table.add(inputButton);

					//output.setText(unscrambled.toString());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});

		/*nextResults.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				RESULT_PAGE_NUM++;
				resetTable();

				newFields = new TextField[MAX_RESULTS];
				createCallback(unscrambled);

				int fieldNum = unscrambled.size() - (MAX_RESULTS * RESULT_PAGE_NUM) - 1;
				int numFields = MAX_RESULTS;
				if(RESULT_PAGE_NUM != 0) {
					if(fieldNum > MAX_RESULTS) {
						numFields = MAX_RESULTS;
					} else {
						numFields = MAX_RESULTS - fieldNum;
					}
				}


				for(int i = 0; i < numFields; i++) {
					newFields[i] = new TextField("", skin);

					table.getCell(input).row();
					if(i == 0) {
						table.add(unscrambledLabel).pad(2, 0, 2, 10);
					} else {
						table.add();
					}

					table.add(newFields[i]).width(Gdx.graphics.getWidth() * 0.4f).pad(2, 0, 2, 0);

					newFields[i].setColor(newFields[i].getColor().r, newFields[i].getColor().g, newFields[i].getColor().b, 0);
					newFields[i].setDisabled(true);
					Tween.to(newFields[i], TextFieldAccessor.ALPHA, 0.2f).target(1).setCallback(cb1).start(tweenManager);


					//output.setText(output.getText() + " " + unscrambled.get(i));
				}
				table.row();
				if(isPrevResults()){
					table.add(prevResults);
				}
				if(isMoreResults()) {
					table.add(nextResults);
				}

				table.add(inputButton);
			}
		});

		prevResults.addListener( new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				RESULT_PAGE_NUM--;
				resetTable();

				newFields = new TextField[MAX_RESULTS];
				createCallback(unscrambled);

				int fieldNum = unscrambled.size() - (MAX_RESULTS * RESULT_PAGE_NUM) - 1;
				int numFields = MAX_RESULTS;
				if(fieldNum > MAX_RESULTS) {
					numFields = MAX_RESULTS;
				}

				if(RESULT_PAGE_NUM != 0) {
					if(fieldNum > MAX_RESULTS) {
						numFields = MAX_RESULTS;
					} else {
						numFields = MAX_RESULTS - fieldNum;
					}

				}


				for(int i = 0; i < numFields; i++) {
					newFields[i] = new TextField("", skin);

					table.getCell(input).row();
					if(i == 0) {
						table.add(unscrambledLabel).pad(2, 0, 2, 10);
					} else {
						table.add();
					}

					table.add(newFields[i]).width(Gdx.graphics.getWidth() * 0.4f).pad(2, 0, 2, 0);

					newFields[i].setColor(newFields[i].getColor().r, newFields[i].getColor().g, newFields[i].getColor().b, 0);
					newFields[i].setDisabled(true);
					Tween.to(newFields[i], TextFieldAccessor.ALPHA, 0.2f).target(1).setCallback(cb1).start(tweenManager);


					//output.setText(output.getText() + " " + unscrambled.get(i));
				}
				table.row();

				if(isPrevResults()){
					table.add(prevResults);
				}
				if(isMoreResults()) {
					table.add(nextResults);
				}

				table.add(inputButton);
			}
		});
*/
		//stage.setCamera(camera);
		stage.addActor(background);

		table = new Table();
		stage.addActor(table);

		//Row 1
		table.add(scrambledLabel).pad(2, 0, 2, 10);
		table.add(input).width(Gdx.graphics.getWidth() * 0.4f).pad(2, 0, 2, 0);
		table.row();

		//Row 2
		//	table.add(unscrambledLabel).pad(2, 10, 2, 10);
		//	table.add(output).pad(2, 0, 2, 0);
		//	table.row();

		tfOrigPos = new float[4];

	

		//Row 3
		table.add();
		table.add(inputButton).height((float) (Gdx.graphics.getHeight() * 0.05)).width((float) (Gdx.graphics.getWidth() * 0.3)).pad(2, 0, 2, 0);
		

	

		table.setFillParent(true);
		//	table.debug(); // turn on all debug lines (table, cell, and widget)



		//	Sprite test = new Sprite(output);
	}
}
