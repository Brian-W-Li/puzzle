package com.ppzl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final MyPuzzleGame game;
    TextButton button1;
    TextButton button2;
    TextButton button3;
    OrthographicCamera camera;

    public MainMenuScreen(final MyPuzzleGame agame) {
        this.game = agame;
        button1 = new TextButton("Click me!", game.skin, "default");
        button2 = new TextButton("Click me!", game.skin, "default");
        button3 = new TextButton("Click me!", game.skin, "default");
        button1.setWidth(140);
        button1.setHeight(140);
        button1.setX(50);
        button1.setY(300);
        button2.setWidth(140+300);
        button2.setHeight(140);
        button2.setX(50);
        button2.setY(200);
        button3.setWidth(140+300+300);
        button3.setHeight(140);
        button3.setX(50);
        button3.setY(100);
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
        button1.draw(game.batch, 1f);
        button2.draw(game.batch, 1f);
        button3.draw(game.batch, 1f);
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
