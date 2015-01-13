package com.aia.hichef.screens;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Img;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.Request;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.views.imp.FullLoading;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class FlashScreen extends AbstractGameScreen {
	Img			splash;
	Sprite		sprite;

	boolean		loaded		= false;
	boolean		showMessage	= false;
	SpriteBatch	spriteBatch;
	GameScreen gameScreen;

	public FlashScreen(GameCore game) {
		super(game);
		sprite = new Sprite(new Texture(Gdx.files.internal("Img/splash.png")));
		sprite.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		parent.saveDeviceInfo();
		if (AppPreference.instance._login) {
			Request.getInstance().userFaceBookLogin(
					AppPreference.instance._facebookID,
					AppPreference.instance._title,
					AppPreference.instance._avatar,
					AppPreference.instance._gender,
					AppPreference.instance._email,
					AppPreference.instance._phone,
					AppPreference.instance._deviceID,
					AppPreference.instance._deviceName,
					AppPreference.instance._version, listener);
		}
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	float	time	= 0;

	@Override
	public void update(float delta) {
		if (time <= 0.1f)
			time += delta;
		if (!loaded && time > 0.1f) {
			if (Assets.instance.assetManager.update()) {
				Assets.instance.init();
				FullLoading.instance.build(stage);
				FullLoading.instance.show();
				buildComponent(stage);
				toast.builToast();
				gameScreen = new GameScreen(parent);
				loaded = true;
			}
		}

		if (loaded && !showMessage) {
			showMessage = true;
		}
	}

	@Override
	public void dispose() {
		splash = null;
		sprite.getTexture().dispose();
		spriteBatch.dispose();
		super.dispose();
	}

	boolean	switchScreen	= false;

	void switchScreen() {
		if (!switchScreen) {
//			 parent.setScreen(new TestStage(parent));
			// parent.setScreen(new TableTest(parent));
			parent.setScreen(gameScreen);
			switchScreen = true;
		}
	}

	@Override
	public void render(float delta) {
		if (!loaded) {
			if (spriteBatch == null)
				spriteBatch = new SpriteBatch();
			spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
			spriteBatch.begin();
			sprite.draw(spriteBatch);
			spriteBatch.end();
		}

		FullLoading.instance.update(delta);
		if (showMessage && splash.getActions().size == 0) {
			switchScreen();
		}
		super.render(delta);
	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	void buildComponent(Stage stage) {
		splash = new Img(new Texture(Gdx.files.internal("Img/splash.png")));
		splash.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		// Action act = Actions.alpha(0);
		Action act0 = Actions.alpha(0f, 1f);
		// Action act1 = Actions.alpha(1f, 1f);
		// Action act2 = Actions.delay(.3f);
		// splash.addAction(Actions.sequence(act, act1, act2, act0));
		splash.addAction(Actions.sequence(act0));
		stage.addActor(splash);
	}

	HttpResponseListener	listener	= new HttpResponseListener() {

											@Override
											public void handleHttpResponse(
													HttpResponse httpResponse) {
												String responseData = httpResponse
														.getResultAsString();
												JsonValue value = (new JsonReader())
														.parse(responseData);
												String result = value
														.getString(ExtParamsKey.RESULT);
												if (result
														.equalsIgnoreCase("failed")) {
													System.out
															.println("Request Comfirm Login Failed");
												} else {
													System.out
															.println("Request Comfirm Login succes");
													AppPreference.instance._userID = value
															.getInt(ExtParamsKey.ID);
												}
												AppPreference.instance
														.saveUserInformation();
											}

											@Override
											public void failed(Throwable t) {

											}

											@Override
											public void cancelled() {

											}
										};
}
