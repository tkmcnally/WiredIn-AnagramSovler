package com.tkmcnally.wordunscrambler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tkmcnally.wordunscrambler.sqlite.DesktopActionResolver;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "word-unscrambler";
		cfg.useGL20 = false;
		cfg.width = 1080;
		cfg.height = 1920;


		new LwjglApplication(new MyGdxGame(new DesktopActionResolver()), cfg);
	}
}
