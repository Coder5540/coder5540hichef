package com.aia.hichef.listener;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class ViewListener extends ActorGestureListener {

	@Override
	public boolean handle(Event e) {
		return super.handle(e);
	}

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
		super.touchDown(event, x, y, pointer, button);
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

		super.touchUp(event, x, y, pointer, button);
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {

		super.tap(event, x, y, count, button);
	}

	@Override
	public boolean longPress(Actor actor, float x, float y) {
		return super.longPress(actor, x, y);
	}

	@Override
	public void fling(InputEvent event, float velocityX, float velocityY, int button) {
		super.fling(event, velocityX, velocityY, button);
	}

	@Override
	public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
		super.pan(event, x, y, deltaX, deltaY);
	}

	@Override
	public void zoom(InputEvent event, float initialDistance, float distance) {
		super.zoom(event, initialDistance, distance);
	}

	@Override
	public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		super.pinch(event, initialPointer1, initialPointer2, pointer1, pointer2);
	}

	@Override
	public GestureDetector getGestureDetector() {
		return super.getGestureDetector();
	}

	@Override
	public Actor getTouchDownTarget() {
		return super.getTouchDownTarget();
	}

}
