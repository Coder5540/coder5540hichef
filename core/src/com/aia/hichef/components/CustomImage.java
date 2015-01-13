package com.aia.hichef.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class CustomImage extends Image {
	public Label	lbText;

	public CustomImage() {
		super();
	}

	public CustomImage(Drawable drawable, Scaling scaling, int align) {
		super(drawable, scaling, align);
	}

	public CustomImage(Drawable drawable, Scaling scaling) {
		super(drawable, scaling);
	}

	public CustomImage(Drawable drawable) {
		super(drawable);
	}

	public CustomImage(NinePatch patch) {
		super(patch);
	}

	public CustomImage(Skin skin, String drawableName) {
		super(skin, drawableName);
	}

	public CustomImage(Texture texture) {
		super(texture);
	}

	public CustomImage(TextureRegion region) {
		super(region);
	}

	public void validate() {
		lbText.setWrap(true);
		lbText.setColor(lbText.getColor().r, lbText.getColor().g,
				lbText.getColor().b, getColor().a);
		lbText.setBounds(getX(), getY(), getWidth(), getHeight());
		lbText.setScale(getScaleX(), getScaleY());
		lbText.setPosition(getX() / 2 + getWidth() / 2, getY() / 2
				+ getHeight() / 2, Align.center);
	}
}
