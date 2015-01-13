package com.aia.hichef;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.utils.AppPreference;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Factory {
	public static Image	avatar;

	public static void createAvatar() {
		if (AppPreference.instance._login) {
			avatar = new Image(Assets.instance.uiP.tapFace);
			avatar.setSize(100, 100);
			ImageDownloader.getInstance().download(
					AppPreference.instance._avatar, avatar);
		}
	}

	public static Vector2 getPosition(Rectangle bounds, Direct direct) {
		switch (direct) {
			case TOP_LEFT:
				return new Vector2(bounds.x, bounds.y + bounds.height);
			case TOP_RIGHT:
				return new Vector2(bounds.x + bounds.width, bounds.y
						+ bounds.height);
			case TOP:
				return new Vector2(bounds.x + bounds.width / 2, bounds.y
						+ bounds.height);
			case BOTTOM:
				return new Vector2(bounds.x + bounds.width / 2, bounds.y);
			case BOTTOM_LEFT:
				return new Vector2(bounds.x, bounds.y);
			case BOTTOM_RIGHT:
				return new Vector2(bounds.x + bounds.width, bounds.y);
			case MIDDLE:
				return new Vector2(bounds.x + bounds.width / 2, bounds.y
						+ bounds.height / 2);
			case MIDDLE_LEFT:
				return new Vector2(bounds.x, bounds.y + bounds.height / 2);
			case MIDDLE_RIGHT:
				return new Vector2(bounds.x + bounds.width, bounds.y
						+ bounds.height / 2);
			default:
				return new Vector2();
		}
	}

	public static TextureRegion[] getArrayTextureRegion(
			TextureRegion textureRegion, int FRAME_COLS, int FRAME_ROWS) {
		float width = textureRegion.getRegionWidth() / FRAME_COLS;
		float height = textureRegion.getRegionHeight() / FRAME_ROWS;

		TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS
				* FRAME_ROWS];
		TextureRegion[][] temp = textureRegion.split((int) width, (int) height);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				textureRegions[index++] = temp[i][j];
			}
		}
		return textureRegions;
	}
}
