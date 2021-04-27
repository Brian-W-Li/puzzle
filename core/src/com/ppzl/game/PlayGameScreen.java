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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class PlayGameScreen implements Screen {
	final MyPuzzleGame game;

	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	//private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Vector3 touchPos;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	private String imagePath;
	private String buttonText;

	public PlayGameScreen(final MyPuzzleGame agame, String imagePath, String buttonText) {
		this.game = agame;
		this.imagePath = imagePath;
		this.buttonText = buttonText;

		// load the images for the droplet and the bucket, 64x64 pixels each
		FileHandle gg = Gdx.files.internal("droplet.png");
		dropImage = new Texture(gg);
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		float x = 800 / 2 - 64 / 2;
		float y = 20;
		float width = 64;
		float height = 64;
		bucket = new Rectangle(x, y, width, height);

		touchPos = new Vector3();

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
		// ... more to come ...
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);





		game.batch.begin();
		game.font.draw(game.batch, this.imagePath, 315, 550);
		game.font.draw(game.batch, this.buttonText, 315, 650);

		game.batch.end();

	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();

	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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