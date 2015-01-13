package com.aia.hichef.views.imp;

import com.aia.hichef.Factory;
import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Img;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.enums.ViewState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class FullLoading {
	public Img					loading;
	public Img					bg;
	public ViewState			viewState	= ViewState.HIDE;
	Table						table;
	public boolean				isLoading	= false;
	public static FullLoading	instance	= new FullLoading();

	private FullLoading() {
		super();
	}

	public void build(Stage stage) {
		bg = new Img(Assets.instance.uiM.bg_ninepatch);
		bg.setBounds(0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		bg.addAction(Actions.alpha(.5f));

		loading = new Img(Assets.instance.uiA.reg_loading);
		loading.setSize(50, 50);
		loading.setOriginCenter();

		Vector2 center = Factory.getPosition(bg.getBound(), Direct.MIDDLE);
		loading.setPosition(center.x - loading.getWidth() / 2, center.y - loading.getHeight() / 2);
		table = new Table();
		table.addActor(bg);
		table.addActor(loading);
		stage.addActor(table);
		hide();
	}

	public void update(float delta) {
		if (viewState == ViewState.SHOW) {
			loading.rotateBy(Interpolation.swing.apply(-Gdx.graphics.getDeltaTime() * 50));
		}
	}

	public void show() {
		table.setVisible(false);
		table.toFront();
		setViewState(ViewState.SHOW);
	}

	public void hide() {
		setViewState(ViewState.HIDE);
		table.toBack();
		table.setVisible(false);
	}

	public void setViewState(ViewState viewState) {
		this.viewState = viewState;
		if (viewState == ViewState.SHOW) {
			isLoading = true;
		} else if (viewState == ViewState.HIDE) {
			isLoading = false;
		}

	}

}
