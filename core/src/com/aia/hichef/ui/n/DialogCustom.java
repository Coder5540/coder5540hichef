package com.aia.hichef.ui.n;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.enums.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ObjectMap;

public class DialogCustom extends MyDialog {

	ObjectMap<Object, Runnable> runMap = new ObjectMap<Object, Runnable>();
	int i = 0;
	Image back;

	public DialogCustom(String title, Skin skin) {
		super(title, skin);
	}

	public DialogCustom(String title) {
		super(title, new Skin());
		if (buttonStyle == null) {
			NinePatch nine = new NinePatch(Assets.instance.uiP.textfield, 4, 4,
					4, 4);
			nine.setColor(Color.GREEN);
			Drawable draw1 = new NinePatchDrawable(nine);
			NinePatch nine2 = new NinePatch(Assets.instance.uiP.textfield, 4,
					4, 4, 4);
			nine2.setColor(Color.GRAY);
			Drawable draw2 = new NinePatchDrawable(nine2);
			buttonStyle = new TextButtonStyle(draw1, draw2, draw1,
					Assets.instance.fontFactory.getRegular20());
		}

		getButtonTable().pad(30, 100, 030, 100);
	}

	public DialogCustom(String title, WindowStyle windowStyle) {
		super(title, windowStyle);
	}

	@Override
	public MyDialog show(Stage stage) {
		back = new Image(new NinePatch(Assets.instance.uiM.ninepatch,
				Color.WHITE));
		back.getColor().a = 0.8f;

		back.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		stage.addActor(back);
		return super.show(stage);
	}

	@Override
	public void hide() {
		back.remove();
		super.hide();
	}

	public static TextButtonStyle buttonStyle = null;

	public void button(String text, Runnable run) {

		button(text, i).setHeight(10);
		runMap.put(i, run);
		i++;
	}

	@Override
	protected void result(Object object) {
		try {
			runMap.get(object).run();
			runMap.remove(object);
		} catch (Exception e) {

		}
	}
}
