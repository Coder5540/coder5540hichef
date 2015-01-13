package com.aia.hichef.screens;

import com.aia.hichef.ViewController;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.views.imp.FullLoading;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameScreen extends AbstractGameScreen {
	ViewController	controller;

	Image			flash;

	public GameScreen(GameCore game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		FullLoading.instance.build(stage);
		controller = new ViewController(parent);
		controller.build(stage);
		controller.setFacebookConnector(parent.facebookConnector);
		toast.builToast();
		FullLoading.instance.hide();
		Gdx.input.setCatchBackKey(true);
		System.gc();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void update(float delta) {
		controller.update(delta);
		if (isExit) {
			timeExit += delta;
			if (timeExit >= 2) {
				timeExit = 0;
				isExit = false;
			}
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		stage.act(delta);
		stage.draw();

		keyboard.update(delta);
		keyboard.draw();

		if (debugFPS) {
			if (labelDebug == null) {
				LabelStyle style = new LabelStyle();
				style.font = font;
				style.fontColor = Color.GREEN;
				font.setScale(1.5f);
				labelDebug = new Label("FPS : "
						+ Gdx.graphics.getFramesPerSecond(), style);
				labelDebug.setPosition(20, Constants.HEIGHT_SCREEN - 60);
				labelDebug.setTouchable(Touchable.disabled);
				stage.addActor(labelDebug);
			}
			labelDebug.setText("FPS : " + Gdx.graphics.getFramesPerSecond()
					+ " Size : " + stage.getActors().size);
			labelDebug.toFront();
		}
	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	boolean	isExit		= false;
	float	timeExit	= 0;

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.A) {
			if (FullLoading.instance.viewState == ViewState.HIDE) {
				FullLoading.instance.show();
			} else {
				FullLoading.instance.hide();
			}
		}

		if (keycode == Keys.Q) {
			parent.saveDeviceInfo();

		}
		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			if (AbstractGameScreen.keyboard.isShowing()) {
				AbstractGameScreen.keyboard.hide();
				AbstractGameScreen.keyboard.clear();
			} else {
				TraceView.instance.debug();
				if (controller.getView(TraceView.instance.getLastView()) != null)
					controller.getView(TraceView.instance.getLastView()).back();
			}
		}
		if (keycode == Keys.ENTER) {

		}

		return false;
	}

	boolean	connect	= false;
	float	offset	= 8;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		viewport.unproject(touchPoint.set(screenX, screenY));
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	};

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

}
