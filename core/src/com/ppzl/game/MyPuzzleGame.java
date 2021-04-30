
package com.ppzl.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyPuzzleGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
        font = skin.getFont("font");
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}