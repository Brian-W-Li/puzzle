package com.ppzl.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PlayGameScreen implements Screen {
	final MyPuzzleGame game;

	private Stage stage;
	private String imagePath;
	private String level;
	Table table;
	ButtonGroup buttonGroup;
	Texture texture;
	ArrayList<PuzzlePiece> piecesArray;
	int levelNum;
	TextButton home;

	public PlayGameScreen(final MyPuzzleGame agame, String imagePath, String level) {
		this.game = agame;
		this.imagePath = imagePath;
		this.level = level;

		home = new TextButton("Home", game.skin, "small");
		home.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int buttonn) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return true;
			}
		});

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(home);
		home.setSize(50, 50);
		home.setPosition(0, 750);
		buttonGroup = new ButtonGroup();
		buttonGroup.setMinCheckCount(0);

		texture = new Texture(Gdx.files.internal(this.imagePath));



		piecesArray = createPieces(level, texture);


		//for(PuzzlePiece onepiece : piecesArray){

		for(int i = 0; i<piecesArray.size(); i++) {
			PuzzlePiece onepiece = piecesArray.get(i);

			onepiece.setSlotId(i);

			buttonGroup.add(onepiece);
		}








	}

	private ArrayList<PuzzlePiece> createPieces(String level, Texture texture){
		ArrayList<PuzzlePiece> puzzleArray = new ArrayList<>();
		if(level.equals("Easy")){
			levelNum = 2;
		} else if(level.equals("Medium")){
			levelNum = 3;
		} else if(level.equals("Impossible")){
			levelNum = 4;
		}
		int width = texture.getWidth()/levelNum;
		int height = texture.getHeight()/levelNum;
		for(int id = 0; id<levelNum*levelNum; id++){
			int rowNum = id/levelNum;
			int collumnNum = id%levelNum;
			int x = collumnNum*width;
			int y = rowNum*height;

			TextureRegion textureregion = new TextureRegion(texture, x, y, width, height);
			TextureRegionDrawable regionDrawableUp = new TextureRegionDrawable(textureregion);
			TextureRegionDrawable regionDrawableDown = new TextureRegionDrawable(textureregion);
			regionDrawableUp.setMinSize(150, 150);
			regionDrawableDown.setMinSize(130, 130);//IMPORTANT
			PuzzlePiece piece = new PuzzlePiece(id, regionDrawableUp, regionDrawableDown, regionDrawableDown);
			puzzleArray.add(piece);
		}
		Collections.shuffle(puzzleArray);
		Texture background = new Texture(Gdx.files.internal("background.jpeg"));
		for(int id = levelNum*levelNum; id<levelNum*levelNum+levelNum; id++) {
			int rowNum = id/levelNum;
			int collumnNum = id%levelNum;
			int x = collumnNum*width;
			int y = rowNum*height;

			TextureRegion textureregion = new TextureRegion(background, x, y, width, height);
			TextureRegionDrawable regionDrawableUp = new TextureRegionDrawable(textureregion);
			regionDrawableUp.setMinSize(150, 150);
			PuzzlePiece piece = new PuzzlePiece(id, regionDrawableUp, regionDrawableUp, regionDrawableUp);

			final PuzzlePiece finalPiece = piece;
			piece.addListener(new InputListener() {
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int buttonn) {
					PuzzlePiece checked = (PuzzlePiece)buttonGroup.getChecked();
					if(checked != null){
						int pictureId = checked.getId();
						int pictureSlotId = checked.getSlotId();
						int emptyId = finalPiece.getId();
						int emptySlotId = finalPiece.getSlotId();
						checked.setSlotId(emptySlotId);
						finalPiece.setSlotId(pictureSlotId);
						piecesArray.set(emptySlotId, checked);
						piecesArray.set(pictureSlotId, finalPiece);
						boolean win = true;
						for(int i = 0; i<levelNum*levelNum; i++){
							PuzzlePiece onepiece = piecesArray.get(i);
							if(onepiece.getId() == onepiece.getSlotId()){

							}
							else{
								win = false;
							}
						}
						if(win){
						home.setSize(100,100);
						home.setPosition(350, 350);
						}
					}
					return true;
				}
			});

			puzzleArray.add(piece);
		}


		return(puzzleArray);
	}
	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		table = new Table();

		for(int i = 0; i<piecesArray.size(); i++) {
			PuzzlePiece onepiece = piecesArray.get(i);
			table.add(onepiece);
			if(onepiece.getSlotId()%levelNum == levelNum - 1){
				table.row();
			}
		}

		table.setPosition(350, 400);
		stage.addActor(table); //added table to the stage so all buttons can take input and output


		game.batch.begin();
		stage.draw();
		home.draw(game.batch, 1);
		game.batch.end();

	}
	
	@Override
	public void dispose () {
		/*dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();*/
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