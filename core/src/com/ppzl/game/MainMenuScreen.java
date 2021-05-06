package com.ppzl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class MainMenuScreen implements Screen {

    final MyPuzzleGame game;

    private Stage stage;
    OrthographicCamera camera;
    Table table;
    Array<TextButton> buttons;
    Array<ImageButton> imageButtons;
    ButtonGroup buttonGroup;
    public MainMenuScreen(final MyPuzzleGame agame) {
        this.game = agame;
        imageButtons = new Array<>();
        buttons = new Array<>();

        stage = new Stage();
        buttonGroup = new ButtonGroup();
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        buttons.add(new TextButton("Easy", game.skin, "small"));
        buttons.add(new TextButton("Medium", game.skin, "small"));
        buttons.add(new TextButton("Impossible", game.skin, "small"));
        for(TextButton button: buttons){
            final String buttonText = button.getText().toString();
            button.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int buttonn) {
                    Button checked = buttonGroup.getChecked();
                    if(checked != null){
                        String nameOfImage = checked.getName();
                        game.setScreen(new PlayGameScreen(game, nameOfImage, buttonText));
                        dispose();
                    }
                    return true;
                }
            });
        }

        for(int i = 1; i<=3; i++) {
            Texture texture = new Texture(Gdx.files.internal("Projectpicture" + i + ".jpg"));

            TextureRegionDrawable regionDrawableUp = new TextureRegionDrawable(texture);
            TextureRegionDrawable regionDrawableDown = new TextureRegionDrawable(texture);
            regionDrawableUp.setMinSize(200, 200);
            regionDrawableDown.setMinSize(180, 180);

            ImageButton imageButton = new ImageButton(regionDrawableUp, regionDrawableDown, regionDrawableDown);
            imageButton.setName("Projectpicture" + i + ".jpg");

            imageButtons.add(imageButton);
            buttonGroup.add(imageButton);
        }


        table = new Table();
        for(TextButton button: buttons){
            table.add(button);
        }

        table.row();

        for(ImageButton imageButton : imageButtons){
            table.add(imageButton);
        }

        table.setSize(100, 100);
        table.setPosition(350, 300);
        stage.addActor(table); //added table to the stage so all buttons can take input and output

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        stage.act();

        game.batch.begin();
        game.font.draw(game.batch, "Choose a level!!! ", 315, 550);
        game.font.draw(game.batch, "Select a picture!!!", 315, 650);
        stage.draw();

        game.batch.end();
    }
    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
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

    public void dispose () {
    }
}
