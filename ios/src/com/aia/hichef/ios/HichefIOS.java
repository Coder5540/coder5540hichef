package com.aia.hichef.ios;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.screens.GameScreen;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class HichefIOS extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new GameCore() {
			@Override
			public void create() {
				setScreen(new GameScreen(this));
			}
		}, config);
    }
    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, HichefIOS.class);
        pool.close();
    }
}