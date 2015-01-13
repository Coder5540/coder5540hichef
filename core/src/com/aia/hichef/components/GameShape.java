package com.aia.hichef.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GameShape extends Group {

	public static enum GameShapeType {
		RECTANGLE, CIRCLE;
	}

	ShapeRenderer	shapeRenderer;
	GameShapeType	type;

	public GameShape(ShapeRenderer shapeRenderer, GameShapeType type) {
		super();
		this.shapeRenderer = shapeRenderer;
		this.type = type;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (type != null) {
			batch.end();
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			shapeRenderer.begin(ShapeType.Filled);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.setColor(getColor());
			switch (type) {
				case RECTANGLE:
					// shapeRenderer.rect(getX(), getY(), getOriginX(),
					// getOriginY(), getWidth(), getHeight(), getScaleX(),
					// getScaleY(), getRotation());
					shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
					break;
				case CIRCLE:
					shapeRenderer.circle(getX() + getWidth() * 0.5f, getY() + getHeight() * 0.5f, getWidth() * 0.5f);
					break;
			}
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
			batch.begin();
		}
		super.draw(batch, parentAlpha);
	}
}
