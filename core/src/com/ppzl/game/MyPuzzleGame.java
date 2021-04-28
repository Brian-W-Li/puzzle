
package com.ppzl.game;

        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.files.FileHandle;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.g2d.TextureAtlas;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

        import java.util.logging.FileHandler;

public class MyPuzzleGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;


    public void create() {
        batch = new SpriteBatch();


        //TextureAtlas atlas = new TextureAtlas("metal/skin/metal-ui.atlas");
        skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));
        //BitmapFont font = skin.getFont("font");
        //FileHandle fh = Gdx.files.internal("metal/skin/font-export.fnt");
        font = skin.getFont("font");

        this.setScreen(new MainMenuScreen(this));
        //BitmapFont font = skin.getFont("large");
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}