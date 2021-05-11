package com.ppzl.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ppzl.game.MyPuzzleGame;
import com.ppzl.game.PlayGameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Picture Puzzle";
		config.width = 800;
		config.height = 800;
		new LwjglApplication(new MyPuzzleGame(), config);
	}
}