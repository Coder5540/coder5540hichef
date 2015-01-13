package com.aia.hichef.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class AssetUIA {
	public TextureRegion	reg_loading;
	public TextureRegion	reg_flash;
	public TextureRegion	reg_floating_button_check;
	public TextureRegion	reg_floating_button_uncheck;
	public TextureRegion	reg_iconHeart;
	public TextureRegion	reg_back;
	public TextureRegion	reg_cursor_highlight;
	public TextureRegion	reg_cursor_normal;
	public TextureRegion	reg_step;
	public TextureRegion	reg_bg;
	public TextureRegion	reg_avatarbound;
	public TextureRegion	reg_9patch_line;
	public TextureRegion	reg_clock_icon;
	public TextureRegion	reg_icon_cancel;
	public TextureRegion	reg_icon_save;
	public TextureRegion	reg_ninepatch;
	public TextureRegion	reg_ninepatch2;
	public TextureRegion	reg_ninepatch3;
	public TextureRegion	reg_ninepatch4;

	private TextureRegion	reg_camera, reg_edit, reg_delete, reg_moveUp;

	TextureAtlas			textureAtlas;

	public AssetUIA(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
		reg_loading = textureAtlas.findRegion("UIA/loading");
		reg_flash = textureAtlas.findRegion("UIA/splash");
		reg_floating_button_check = textureAtlas
				.findRegion("UIA/store_app_active");
		reg_floating_button_uncheck = textureAtlas.findRegion("UIA/store_app");
		reg_iconHeart = textureAtlas.findRegion("UIA/iconHeart");
		reg_back = textureAtlas.findRegion("UIA/regback");
		reg_cursor_highlight = textureAtlas.findRegion("UIA/cursor_highlight");
		reg_cursor_normal = textureAtlas.findRegion("UIA/cursor_normal");
		reg_step = textureAtlas.findRegion("UIA/bg1");
		reg_bg = textureAtlas.findRegion("UIA/bg2");
		reg_avatarbound = textureAtlas.findRegion("UIA/bound");
		reg_9patch_line = textureAtlas.findRegion("UIA/9patch");
		reg_clock_icon = textureAtlas.findRegion("UIA/clock_icon");

		reg_icon_save = textureAtlas.findRegion("UIA/icon_save");
		reg_icon_cancel = textureAtlas.findRegion("UIA/icon_cancel");
		reg_ninepatch = textureAtlas.findRegion("UIA/ninepatch");
		reg_ninepatch2 = textureAtlas.findRegion("UIA/ninepatch2");
		reg_ninepatch3 = textureAtlas.findRegion("UIA/ninepatch3");
		reg_ninepatch4 = textureAtlas.findRegion("UIA/ninepatch4");
	}

	public TextureRegion getRegionCamera() {
		if (reg_camera == null)
			reg_camera = textureAtlas.findRegion("UIA/camera");
		return reg_camera;
	}

	public TextureRegion getRegionEdit() {
		if (reg_edit == null)
			reg_edit = textureAtlas.findRegion("UIA/edit");
		return reg_edit;
	}

	public TextureRegion getRegionDelete() {
		if (reg_delete == null)
			reg_delete = textureAtlas.findRegion("UIA/trash");
		return reg_delete;
	}

	public TextureRegion getRegionMoveUp() {
		if (reg_moveUp == null)
			reg_moveUp = textureAtlas.findRegion("UIA/up");
		return reg_moveUp;
	}

	public Drawable getBackground(TextureRegion ninePatchRegion, Color color) {
		NinePatch ninePatch = new NinePatch(ninePatchRegion, 10, 10, 10, 10);
		ninePatch.setColor(color);
		return new NinePatchDrawable(ninePatch);
	}
}