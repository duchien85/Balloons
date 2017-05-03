package com.kara4k.balloons.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kara4k.balloons.Balloons;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Balloons";
        config.width = Balloons.WIDTH;
        config.height = Balloons.HEIGHT;
		new LwjglApplication(new Balloons(), config);
	}
}
