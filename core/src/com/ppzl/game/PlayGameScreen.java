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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class PlayGameScreen implements Screen {
	final MyPuzzleGame game;

	private Stage stage;
	private String imagePath;
	private String level;
	Table table;
	ButtonGroup buttonGroup;
	Texture texture;
	Array<PuzzlePiece> piecesArray;
	int levelNum;

	public PlayGameScreen(final MyPuzzleGame agame, String imagePath, String level) {
		this.game = agame;
		this.imagePath = imagePath;
		this.level = level;


		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		buttonGroup = new ButtonGroup();
		buttonGroup.setMinCheckCount(0);

		texture = new Texture(Gdx.files.internal(this.imagePath));



		piecesArray = createPieces(level, texture);

		table = new Table();
		for(PuzzlePiece onepiece : piecesArray){
			table.add(onepiece);
			if(onepiece.getId()%levelNum == levelNum - 1){
				table.row();
			}
			buttonGroup.add(onepiece);
		}


		table.setSize(100, 100);
		table.setPosition(350, 300);

		stage.addActor(table); //added table to the stage so all buttons can take input and output


	}

	private Array<PuzzlePiece> createPieces(String level, Texture texture){
		Array<PuzzlePiece> puzzleArray = new Array<>();
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
			regionDrawableUp.setMinSize(200, 200);
			regionDrawableDown.setMinSize(180, 180);
			PuzzlePiece piece = new PuzzlePiece(id, regionDrawableUp, regionDrawableDown, regionDrawableDown);
			puzzleArray.add(piece);
		}
		return(puzzleArray);
	}
	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);





		game.batch.begin();
		game.font.draw(game.batch, this.imagePath, 315, 550);
		game.font.draw(game.batch, this.level, 315, 650);
		stage.draw();
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