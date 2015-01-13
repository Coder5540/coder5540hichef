package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.ui.MenuList;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuView extends View {

	private Group		content;
	private Image		bg;
	private Image		tranBg;

	private MenuList	menuList;

	public MainMenuView buildComponent(String name, Rectangle bound) {
		this.name = name;
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);

		bg = new Image(new NinePatch(Assets.instance.uiP.ninepatch_white));
		tranBg = new Image(new NinePatch(Assets.instance.uiP.transparent));
		tranBg.setSize(getWidth(), getHeight());
		tranBg.setVisible(false);
		tranBg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
			}
		});

		content = new Group();
		content.addActor(bg);
		content.setSize(2 * Constants.WIDTH_SCREEN / 3, getHeight());
		content.setX(-content.getWidth());
		bg.setSize(content.getWidth(), content.getHeight());

		menuList = new MenuList(_viewController, new Table(),
				content.getWidth(), content.getHeight());
		content.addActor(menuList);
		this.addActor(tranBg);
		tranBg.addAction(Actions.alpha(0));
		this.addActor(content);
		setViewState(ViewState.HIDE);
		return this;
	}

	@Override
	public void update() {
		if (tranBg.isVisible()) {
			float alpha = (content.getWidth() - content.getX())
					/ content.getWidth();
			if (content.getX() == 0)
				alpha = 1;
			tranBg.setColor(tranBg.getColor().r, tranBg.getColor().g,
					tranBg.getColor().b, alpha);
		}

		super.update();
	}

	@Override
	public void show() {
		super.show();
		tranBg.setVisible(true);
		content.addAction(Actions.sequence(
				Actions.moveTo(0, 0, 0.5f, Interpolation.pow5Out),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						menuList.refresh();
					}
				})));
		setTouchable(Touchable.enabled);
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
		setTouchable(Touchable.disabled);
		tranBg.setVisible(false);
		content.addAction(Actions.moveTo(-content.getWidth(), 0, 0.5f,
				Interpolation.pow5Out));
		TraceView.instance.removeView(this.getName());
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		addAction(Actions.alpha(visible ? 1f : 0f));
	}

	@Override
	public void back() {
		hide();
	}
}
