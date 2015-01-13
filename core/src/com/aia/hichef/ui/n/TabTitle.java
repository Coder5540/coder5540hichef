package com.aia.hichef.ui.n;

import com.aia.hichef.assets.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TabTitle extends Table {
	public static boolean		isMoved				= false;
	public static float			TITLE_TAB_HEIGHT	= 70;
	public static float			MARGIN_X			= 30;
	public static float			MARGIN_Y			= 15;
	private Label				label;
	public float				lastPost;
	public BitmapFont			scorePopupFont;
	public static LabelStyle	labelStyle;
	private Image				imgSplit;
	private Image				imgBottom, imageTouch;

	private float				xDown;

	public TabTitle(String text) {
		addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// System.out.println("title down x=" + x);
				xDown = x;
				isMoved = false;
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// System.out.println("title up x=" + x);
				if (Math.abs(x - xDown) > 3) {
					isMoved = true;
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		labelStyle = new LabelStyle(Assets.instance.fontFactory.getRegular20(),
				new Color(Color.RED));
		label = new Label(text, labelStyle);

		label.setX(MARGIN_X);
		label.setY(TITLE_TAB_HEIGHT / 2 - label.getHeight()
				* label.getFontScaleY() / 2);
		imgSplit = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(222 / 255f, 219 / 255f, 222 / 255f, 1)));
		imgSplit.setWidth(2);
		imgSplit.setHeight(TITLE_TAB_HEIGHT - 20);
		imgSplit.setY(9);
		imgSplit.setPosition(getWidth() - imgSplit.getWidth(), 0);

		imageTouch = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(Color.WHITE)));
		imageTouch.setWidth(getWidth());
		imageTouch.setHeight(getHeight());
		imageTouch.setPosition(0, 0);

		imgBottom = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(222 / 255f, 219 / 255f, 222 / 255f, 1)));
		imgBottom.setHeight(5);
		imgBottom.setWidth(getWidth() + 3);
		addActor(imageTouch);
		addActor(label);
		// setHeight(TITLE_TAB_HEIGHT);
		// setWidth(getWidth());
	}

	public TabTitle(LabelStyle style, String text) {
		label = new Label(text, style);
		// label.setFontScale(TITLE_TAB_HEIGHT / label.getHeight());
		label.setX(MARGIN_X);
		addActor(label);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		imgSplit.setPosition(getX() + getWidth() - imgSplit.getWidth(),
				getY() + 10);
		imgSplit.draw(batch, parentAlpha);
		imgSplit.setRotation(getRotation());
		imgSplit.setColor(getColor());
		imgSplit.setScale(getScaleX(), getScaleY());

		imgBottom.setPosition(getX(), getY());
		imgBottom.setWidth(getWidth());
		imgBottom.draw(batch, parentAlpha);
		imgBottom.setRotation(getRotation());
		imgBottom.setColor(getColor());
		imgBottom.setScale(getScaleX(), getScaleY());
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		TITLE_TAB_HEIGHT = height;
	}

	@Override
	public float getWidth() {
		return label.getWidth() * label.getFontScaleX() + MARGIN_X * 2;
	}

	@Override
	public float getHeight() {
		return TITLE_TAB_HEIGHT;
	}
}