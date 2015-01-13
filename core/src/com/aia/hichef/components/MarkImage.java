package com.aia.hichef.components;

import com.aia.hichef.assets.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MarkImage extends Image {
//	ShapeRenderer	shapeRenderer;
	Image bound;
	

	public MarkImage(Texture texture) {
		super(texture);
		bound = new Image(Assets.instance.uiA.reg_avatarbound);
//		shapeRenderer = new ShapeRenderer();
	}

	public MarkImage(TextureRegion region) {
		super(region);
		bound = new Image(Assets.instance.uiA.reg_avatarbound);
//		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		bound.setSize(getWidth(), getHeight());
		bound.setPosition(getX(), getY());
		bound.draw(batch, parentAlpha);
	}
//	@Override
//	public void draw(Batch batch, float parentAlpha) {
//		batch.flush();
//		Gdx.gl.glColorMask(true, true, true, true);
//		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//		Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
//		super.draw(batch, parentAlpha);
//		batch.flush();
//		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
//		
//	}

//	void renderMaskShape(Batch batch) {
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//		Gdx.gl.glClearDepthf(1f);
//		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
//
//		Gdx.gl.glDepthFunc(GL20.GL_LESS);
//
//		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//
//		Gdx.gl.glDepthMask(true);
//		Gdx.gl.glColorMask(false, false, false, false);
//
//		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//		shapeRenderer.begin(ShapeType.Filled);
//
//		shapeRenderer.setColor(0f, 0f, 0f,1f);
//		shapeRenderer.circle(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2 + 1);
//
//		shapeRenderer.end();
//
//		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
//		Gdx.gl.glColorMask(true, true, true, true);
//	}
}
