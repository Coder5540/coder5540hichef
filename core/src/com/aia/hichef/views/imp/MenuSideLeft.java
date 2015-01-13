package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MenuSideLeft extends View {
	public Image	transparent;
	public Table	content;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
		transparent = new Image(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(0, 0, 0, 0.5f))));
		transparent.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		transparent.setVisible(false);
		transparent.setTouchable(Touchable.disabled);
		transparent.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				content.addAction(Actions.sequence(Actions.moveTo(
						-content.getWidth(), 0, 0.2f, Interpolation.exp10Out),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								hide();
							}
						})));
				event.stop();
			}
		});
		addActor(transparent);
	}

	public void buildComponent(String name, Rectangle bound) {
		setName(name);
		this.setBounds(bound.x, bound.y, bound.width, bound.height);
		content = new Table();
		content.setBounds(bound.x - 3 * bound.width / 5, bound.y,
				3 * bound.width / 5, bound.height);
		content.setBackground(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch, Color.GRAY));
		content.setPosition(-content.getWidth(), 0);
		addActor(content);
	}

	@Override
	public void show() {
		setVisible(true);
		toFront();
		setTouchable(Touchable.enabled);
		setViewState(ViewState.SHOW);
		transparent.setVisible(true);
		transparent.addAction(Actions.alpha(0));
		transparent.setTouchable(Touchable.enabled);

	}

	@Override
	public void hide() {
		toBack();
		setVisible(false);
		setViewState(ViewState.HIDE);
		setTouchable(Touchable.disabled);
		transparent.addAction(Actions.alpha(0));
		content.setPosition(-content.getWidth(), 0);
		transparent.setVisible(false);
		transparent.setTouchable(Touchable.disabled);
	}

	@Override
	public void update() {
		if (content.isVisible()) {
			transparent.setColor(transparent.getColor().r,
					transparent.getColor().g, transparent.getColor().b,
					(content.getX() + content.getWidth()) / content.getWidth());
			if (_viewController.getView(StringSystem.VIEW_HOME) != null)
				_viewController.getView(StringSystem.VIEW_HOME).setPosition(
						content.getX() + content.getWidth(), 0);
		}
	}

	@Override
	public void back() {
		super.back();
	}

	@Override
	public boolean onLeftSide() {
		show();
		return true;
	}

	public void onTouchUp(Vector2 touchUp) {
		if (content.getX() < -content.getWidth() / 2)
			content.addAction(Actions.sequence(Actions.moveTo(
					-content.getWidth(), 0, 0.2f, Interpolation.exp10Out),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							hide();
						}
					})));
		else
			content.addAction(Actions
					.moveTo(0, 0, 0.2f, Interpolation.exp10Out));
	}

	public void onTouchDrag(Vector2 touchDrag) {
		if (touchDrag.x <= content.getWidth())
			content.setPosition(touchDrag.x - content.getWidth(),
					content.getY());
	}

}
