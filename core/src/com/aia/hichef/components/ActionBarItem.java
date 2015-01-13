package com.aia.hichef.components;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.ItemListener;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ActionBarItem extends Table {

	private Image imgFocus;

	public ActionBarItem(final ItemListener listener) {
//		this.bottom();
		imgFocus = new Image(new NinePatch(Assets.instance.uiP.transparent));
		this.setHeight(Constants.HEIGHT_ACTIONBAR);
		this.addActor(imgFocus);
		imgFocus.setHeight(getHeight());
		imgFocus.getColor().a = 0;
		this.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				imgFocus.getColor().a = 1;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				imgFocus.getColor().a = 0;
				listener.onItemClick();
			}
		});
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		imgFocus.setWidth(width);
	}

	public void addToItem(Actor subItem) {
		this.add(subItem);
//				.padBottom((getHeight() - subItem.getHeight()) / 2);
	}
}
