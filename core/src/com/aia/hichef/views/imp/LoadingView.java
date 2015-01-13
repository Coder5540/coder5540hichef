package com.aia.hichef.views.imp;

import com.aia.hichef.Factory;
import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Img;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoadingView extends View {
	public Img	loading;
	public Img	bg;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
		setViewState(ViewState.HIDE);
	}

	public LoadingView buildName(String name) {
		this.name = name;
		return this;
	}

	public LoadingView buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public LoadingView buildComponent() {
		Rectangle bound = getBound();

		bg = new Img(Assets.instance.uiM.bg_ninepatch);
		bg.setBounds(bound.x, bound.y, bound.width, bound.height);
		bg.addAction(Actions.alpha(.5f));

		loading = new Img(Assets.instance.uiA.reg_loading);
		loading.setSize(50, 50);
		loading.setOriginCenter();

		Vector2 center = Factory.getPosition(bound, Direct.MIDDLE);
		loading.setPosition(center.x - loading.getWidth() / 2, center.y - loading.getHeight() / 2);

		setViewState(ViewState.SHOW);
		buildListener();
		addActor(loading);
		addActor(bg);
		return this;
	}

	void buildListener() {
		bg.addListener(new ClickListener() {

		});

		loading.addListener(new ClickListener() {

		});
	}

	@Override
	public void update() {
		super.update();
		if (getViewState() == ViewState.SHOW) {
			loading.rotateBy(Interpolation.swing.apply(-Gdx.graphics.getDeltaTime() * 50));
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		addAction(Actions.alpha(visible ? 1f : 0f));
	}

	@Override
	public void show( ) {
		setVisible(true);
		super.show();
	}

	public void hide() {
		setVisible(false);
		super.hide();
	};

}
