package com.aia.hichef.ui.n;

import com.aia.hichef.enums.Constants;
import com.aia.hichef.networks.ImageDownloader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ImageOn extends Image {
	public static int MAX = 20;
	public static int count = 0;
	private String url = "unknown";
	boolean reDownload = false;
	boolean isShow = true;

	public ImageOn(String url) {
		super();
		this.url = url;
		count++;
		isShow = true;
	}

	public ImageOn(NinePatch ninePatch) {
		super(ninePatch);
	}

	public ImageOn() {
		super();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public void act(float delta) {
		Vector2 pos = new Vector2(getX(), getY());
		localToStageCoordinates(pos);
		if ((pos.x < -Constants.WIDTH_SCREEN * 2 + 10
				|| pos.x > Constants.WIDTH_SCREEN * 2 - 5
				|| pos.y > Constants.HEIGHT_SCREEN * 8
				|| pos.y < -Constants.HEIGHT_SCREEN * 7 || getStage() == null)
				&& count > MAX && isShow) {
			long javaHeap = Gdx.app.getJavaHeap();
			@SuppressWarnings("unused")
			long nativeHeap = Gdx.app.getNativeHeap();
			try {
				TextureRegionDrawable tex = (TextureRegionDrawable) getDrawable();

				tex.getRegion().getTexture().dispose();
				tex = null;
			} catch (Exception e) {

			}
			System.gc();
			javaHeap = Gdx.app.getJavaHeap();
			nativeHeap = Gdx.app.getNativeHeap();
			System.out.println("Heap= " + javaHeap);

			isShow = false;
			count--;
		} else {
			if (!isShow && pos.x >= -5 && pos.x <= Constants.WIDTH_SCREEN + 5
					&& pos.y >= -5 && pos.y <= Constants.HEIGHT_SCREEN + 5) {
				isShow = true;
				count++;
				ImageDownloader.getInstance().download(url, this);
			}
		}
		super.act(delta);
	}
}
