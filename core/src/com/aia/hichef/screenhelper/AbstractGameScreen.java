package com.aia.hichef.screenhelper;

import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.GameState;
import com.aia.hichef.networks.Request;
import com.aia.hichef.utils.AppPreference;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.hd.input.keyboard.VirtualKeyboard;

public abstract class AbstractGameScreen implements Screen, InputProcessor,
		GestureListener {
	public GameCore					parent;
	public Viewport					viewport;
	public OrthographicCamera		camera;
	public Stage					stage;
	public static Toast				toast;
	public static ShapeRenderer		shapeRenderer;
	public Vector2					touchPoint;
	public GameState				gameState;
	public static VirtualKeyboard	keyboard;
	public static boolean			debugShape	= false;
	public static boolean			debugFPS	= true;

	public BitmapFont				font		= new BitmapFont();
	public Label					labelDebug;

	public AbstractGameScreen(GameCore game) {
		this.parent = game;
	}

	public abstract void resize(int width, int height);

	public void show() {
		camera = new OrthographicCamera(Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN);
		viewport = new StretchViewport(Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN, camera);

		stage = new Stage(viewport);
		toast = new Toast(stage);
		touchPoint = new Vector2();
		gameState = GameState.INITIAL;
		keyboard = new VirtualKeyboard((SpriteBatch) stage.getBatch());
		parent.inputMultiplexer = new InputMultiplexer(this, keyboard, stage,
				new GestureDetector(this));
		Gdx.input.setInputProcessor(getInputProcessor());
	}

	public abstract void update(float delta);

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		if (debugShape) {
			if (shapeRenderer == null)
				shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			drawShapeLine(shapeRenderer);
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.Filled);
			drawShapeFill(shapeRenderer);
			shapeRenderer.end();
		}
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
			labelDebug.setText("FPS : " + Gdx.graphics.getFramesPerSecond());
			labelDebug.toFront();
		}
		
	}

	public abstract void drawShapeLine(ShapeRenderer shapeRenderer);

	public abstract void drawShapeFill(ShapeRenderer shapeRenderer);

	public void pause() {
	}

	public void hide() {
	}

	public void resume() {
	}

	public InputProcessor getInputProcessor() {
		return parent.inputMultiplexer;
	}

	public void dispose() {
		Request.getInstance().requestQuitApp(AppPreference.instance._userID,
				AppPreference.instance._sessionID, null);
		keyboard.dispose();
		stage.dispose();
		if (shapeRenderer != null)
			shapeRenderer.dispose();
	}

	public void switchScreen(AbstractGameScreen screen,
			ScreenTransition transition) {
		if (transition == null)
			parent.setScreen(screen);
		else
			parent.setScreen(screen, transition);
	}

	// ===================== input method =======================
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {

		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {

		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}

}
