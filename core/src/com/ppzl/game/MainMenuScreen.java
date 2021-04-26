package com.ppzl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final MyPuzzleGame game;


    OrthographicCamera camera;
    Table table;
    Array<TextButton> buttons;
    Array<ImageButton> imageButtons;
    Array<Texture> imageTextures;
    Array<TextureRegionDrawable> imageDrawables;
    public MainMenuScreen(final MyPuzzleGame agame) {
        this.game = agame;
        imageButtons = new Array<ImageButton>();
        buttons = new Array<TextButton>();
        imageTextures = new Array<>();
        imageDrawables = new Array<>();

        buttons.add(new TextButton("Easy", game.skin, "small"));
        buttons.add(new TextButton("Medium", game.skin, "small"));
        buttons.add(new TextButton("Impossible", game.skin, "small"));

        imageTextures.add(new Texture(Gdx.files.internal("Projectpicture1.jpg")));
        imageTextures.add(new Texture(Gdx.files.internal("Projectpicture2.jpg")));
        imageTextures.add(new Texture(Gdx.files.internal("Projectpicture3.jpg")));

        for(Texture texture:imageTextures) {
            TextureRegionDrawable regionDrawable = new TextureRegionDrawable(texture);
            TextureRegionDrawable regionDrawablex = new TextureRegionDrawable(texture);
            regionDrawable.setMinSize(200, 200);
            regionDrawablex.setMinSize(160, 160);
            imageDrawables.add(regionDrawable);
            imageDrawables.add(regionDrawablex);
            //to make a drawable from textures
            imageButtons.add(new ImageButton(regionDrawable));
            imageButtons.add(new ImageButton(regionDrawablex));
        }

        table = new Table();
        for(TextButton button: buttons){
            table.add(button);
           // button.setSize(50, 50);
        }

        table.row();
        for(ImageButton imageButton : imageButtons){
            table.add(imageButton);

           // imageButton.setSize(50, 50);
        }

        table.setSize(100, 100);
        table.setPosition(350, 300);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        table.draw(game.batch, 1f);
       /* button1.draw(game.batch, 1f);
        button2.draw(game.batch, 1f);
        button3.draw(game.batch, 1f);*/
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new PlayGameScreen(game));
            dispose();
        }

    }
    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {
    }
    //...Rest of class omitted for succinctness.
    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    public void dispose () {

    }
}
