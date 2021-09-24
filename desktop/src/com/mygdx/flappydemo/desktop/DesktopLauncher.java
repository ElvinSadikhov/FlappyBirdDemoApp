package com.mygdx.flappydemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.flappydemo.FlappyDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Flappy Demo";
		config.width = FlappyDemo.WIDTH;
		config.height = FlappyDemo.HEIGHT;
		new LwjglApplication(new FlappyDemo(), config);
	}
}
