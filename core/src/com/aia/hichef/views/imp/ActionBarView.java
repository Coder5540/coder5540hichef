package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.ActionBar;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.OnCompleteListener;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ActionBarView extends View {

	Image bg;
	public ActionBar actionBar;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public ActionBarView buildName(String name) {
		setName(name);
		return this;
	}

	public ActionBarView buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public ActionBarView buildComponent() {
		init();
		return this;
	}

	@Override
	public void show(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {
		super.show(newViewAnimation, oldViewAnimationType, newViewCompleteListener,
				oldViewCompleteListener);
	}

	@Override
	public void show(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {
		super.show(newViewAnimation, newViewCompleteListener);
	}


	@Override
	public void hide(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {
		super.hide(newViewAnimation, oldViewAnimationType, newViewCompleteListener,
				oldViewCompleteListener);
	}

	@Override
	public void hide(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {
		super.hide(newViewAnimation, newViewCompleteListener);
	}
	

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void setViewState(ViewState state) {
		super.setViewState(state);
	}

	@Override
	public ViewState getViewState() {
		return super.getViewState();
	}

	@Override
	public Rectangle getBound() {
		return super.getBound();
	}

	@Override
	public void setName(String name) {
		super.setName(name);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	@Override
	public IViewController getViewController() {
		return super.getViewController();
	}

	@Override
	public boolean onLeftSide() {
		return super.onLeftSide();
	}

	@Override
	public boolean onRightSide() {
		return super.onRightSide();
	}

	public void init() {
		bg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f)));
		bg.setSize(getWidth(), getHeight());
		actionBar = new ActionBar(_viewController, new Rectangle(0, 0,
				getWidth(), getHeight()));
		this.addActor(bg);
		this.addActor(actionBar);
	}

	@Override
	public void back() {
		super.back();

	}

}
