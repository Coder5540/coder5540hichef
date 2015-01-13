package com.aia.hichef.screenhelper;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class HorizontalViewPort extends ScalingViewport {
	/** Creates a new viewport using a new {@link OrthographicCamera}. */
	public HorizontalViewPort (float worldWidth, float worldHeight) {
		super(Scaling.fillX, worldWidth, worldHeight);
	}

	public HorizontalViewPort (float worldWidth, float worldHeight, Camera camera) {
		super(Scaling.fillX, worldWidth, worldHeight, camera);
	}
}
