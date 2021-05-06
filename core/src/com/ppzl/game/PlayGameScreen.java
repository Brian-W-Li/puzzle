package com.ppzl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;

public class PlayGameScreen implements Screen {
	final MyPuzzleGame game;

	private Stage stage;
	private Table table;
	private ButtonGroup buttonGroup;
	//3bi
	private ArrayList<PuzzlePiece> piecesArray;
	private int levelNum;
	//This button helps you go back to the main screen
	private TextButton home;
	private boolean win;
	//the sound that plays when a piece is in the right place
	private Sound ding;

	public PlayGameScreen(final MyPuzzleGame game, String imagePath, String level) {
		this.game = game;

		ding = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		//creates button to go back to main screen
		home = new TextButton("Home", game.skin, "small");
		home.setSize(50, 50);
		home.setPosition(0, 750);
		home.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int buttonn) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return true;
			}
		});

		//Stage listens to all inputs
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(home);

		//to enforce that you can only select one piece
		buttonGroup = new ButtonGroup();
		buttonGroup.setMinCheckCount(0);

		//cuts the picture into small pieces
		Texture texture = new Texture(Gdx.files.internal(imagePath));
		piecesArray = createPieces(level, texture);

		//puts puzzle pieces into slots
		for(int i = 0; i<piecesArray.size(); i++) {
			PuzzlePiece onePiece = piecesArray.get(i);
			onePiece.setSlotId(i);
			buttonGroup.add(onePiece);
		}
	}

	private void swapPiece(PuzzlePiece checked, PuzzlePiece emptyPiece){
		int pictureId = checked.getId();
		int pictureSlotId = checked.getSlotId();
		int emptySlotId = emptyPiece.getSlotId();
		checked.setSlotId(emptySlotId);
		emptyPiece.setSlotId(pictureSlotId);
		piecesArray.set(emptySlotId, checked);
		piecesArray.set(pictureSlotId, emptyPiece);
		//this signifies when picture pieces reach where its supposed to be
		if(pictureId == emptySlotId){
			ding.play();
		}
	}
	private PuzzlePiece createOnePiece(int id, int width, int height, Texture texture){
		//to calculate location and size
		int rowNum = id/levelNum;
		int collumnNum = id%levelNum;
		int x = collumnNum*width;
		int y = rowNum*height;

		//creates the piece
		TextureRegion textureregion = new TextureRegion(texture, x, y, width, height);
		TextureRegionDrawable regionDrawableUp = new TextureRegionDrawable(textureregion);
		TextureRegionDrawable regionDrawableDown = new TextureRegionDrawable(textureregion);
		regionDrawableUp.setMinSize(150, 150);
		regionDrawableDown.setMinSize(130, 130);
		final PuzzlePiece piece;
		//if its an empty piece...
		if(id>=levelNum*levelNum){
			piece = new PuzzlePiece(id, regionDrawableUp, regionDrawableUp, regionDrawableUp);
			//adds actions to swap empty piece and picture piece
			piece.addListener(new InputListener() {
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int buttonn) {
					PuzzlePiece checked = (PuzzlePiece)buttonGroup.getChecked();
					if(checked != null){
						swapPiece(checked, piece);

						//defines when you win
						win = true;
						for(int i = 0; i<levelNum*levelNum; i++){
							PuzzlePiece onepiece = piecesArray.get(i);
							if(onepiece.getId() != onepiece.getSlotId()){
								win = false;
							}
						}
					}
					return true;
				}
			});
		} else{
			//if its a picture piece
			piece = new PuzzlePiece(id, regionDrawableUp, regionDrawableDown, regionDrawableDown);
		}
		return piece;
	}

	private ArrayList<PuzzlePiece> createPieces(String level, Texture texture){
		ArrayList<PuzzlePiece> puzzleArray = new ArrayList<>();

		//Defines the level name
		if(level.equals("Easy")){
			levelNum = 2;
		} else if(level.equals("Medium")){
			levelNum = 3;
		} else if(level.equals("Impossible")){
			levelNum = 4;
		}

		//calculates piece size
		int width = texture.getWidth()/levelNum;
		int height = texture.getHeight()/levelNum;

		//creates all picture pieces and adds it to the array
		for(int id = 0; id<levelNum*levelNum; id++){
			PuzzlePiece piece = createOnePiece(id, width, height, texture);
			puzzleArray.add(piece);
		}

		//shuffles the picture pieces
		Collections.shuffle(puzzleArray);

		//creates empty pieces
		Texture background = new Texture(Gdx.files.internal("background.jpeg"));
		for(int id = levelNum*levelNum; id<levelNum*levelNum+levelNum; id++) {
			PuzzlePiece piece = createOnePiece(id, width, height, background);
			puzzleArray.add(piece);
		}
		return(puzzleArray);
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//3bv
		//I use Table to organize the pieces
		table = new Table();

		//defines what happens when you win
		if(win){
			home.setSize(100,100);
			home.setPosition(350, 350);
			home.setText("You Win!");
		}

		for(int i = 0; i<piecesArray.size(); i++) {
			PuzzlePiece onePiece = piecesArray.get(i);
			table.add(onePiece);
			if(onePiece.getSlotId()%levelNum == levelNum - 1){
				table.row();
			}

			//don't add empty pieces when we win
			if(win && i==levelNum*levelNum-1){
				break;
			}
		}

		if(win){
			table.add(home).colspan(levelNum);
			stage.addActor(home);
		} else{
			stage.addActor(table);
		}
		table.setPosition(350, 400);

		game.batch.begin();
		table.draw(game.batch, 1);
		home.draw(game.batch, 1);
		game.batch.end();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		ding.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}