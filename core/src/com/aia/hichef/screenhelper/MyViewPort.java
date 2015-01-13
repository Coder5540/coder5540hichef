//package com.aia.hichef.screenhelper;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//public class MyViewPort extends Viewport {
//
//	public MyViewPort(float worldWidth, float worldHeight, Camera camera) {
//		this.worldWidth = worldWidth;
//		this.worldHeight = worldHeight;
//		this.camera = camera;
//	}
//
//	@Override
//	public void update(int screenWidth, int screenHeight, boolean centerCamera) {
//		float aspect = worldHeight / worldWidth;
//		viewportWidth = screenWidth;
//		viewportX = 0;
//		viewportHeight = Math.round(screenWidth * aspect);
//		viewportY = screenHeight - viewportHeight;
//
//		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
//		camera.viewportWidth = worldWidth;
//		camera.viewportHeight = worldHeight;
//		if (centerCamera)
//			camera.position.set(worldWidth / 2, worldHeight / 2, 0);
//		camera.update();
//	}
//}