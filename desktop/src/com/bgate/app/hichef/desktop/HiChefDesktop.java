package com.bgate.app.hichef.desktop;

import com.aia.hichef.enums.Constants;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.screens.FlashScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class HiChefDesktop {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "HiChef Desktop";
		config.width = Constants.WIDTH_SCREEN - 60;
		config.height = Constants.HEIGHT_SCREEN - 100;

		GameCore game = new GameCore() {
			@Override
			public void create() {
				super.create();
				// setScreen(new TestStage(this));
				// setScreen(new GameScreen(this));
				setScreen(new FlashScreen(this));
			}
		};

		game.setPlatformResolver(new DesktopResolver());
		game.setFacebookConnector(new DesktopFBConnector());
		new LwjglApplication(game, config);
	}
}
