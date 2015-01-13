package com.bgate.app.hichef.client;

import com.aia.hichef.enums.Constants;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.screens.FlashScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new GameCore() {
					@Override
					public void create() {
						setScreen(new FlashScreen(this));
					}
				};
        }
}