package com.aia.hichef.utils;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.CustomTextButton;
import com.aia.hichef.listener.OnClickListener;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.gdx.hd.input.keyboard.KeyboardConfig;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class UIUtils {

	public static NinePatchDrawable getDrawable(TextureRegion region, int left,
			int right, int top, int bottom, Color color) {
		NinePatch ninePatch = new NinePatch(region, left, right, top, bottom);
		ninePatch.setColor(color);
		return new NinePatchDrawable(ninePatch);
	}

	public static TextFieldStyle getTextFieldStyle(NinePatch background) {
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(background);
		style.font = Assets.instance.fontFactory.getLight20();
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = Color.BLACK;
		return style;
	}

	public static TextFieldStyle getTextFieldStyle(NinePatch background,
			BitmapFont font) {
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(background);
		style.font = font;
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = Color.BLACK;
		return style;
	}

	public static TextFieldStyle getTextFieldStyle(NinePatch background,
			BitmapFont font, Color fontColor) {
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(background);
		style.font = font;
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = fontColor;
		return style;
	}

	public static void registerKeyBoard(TextField textField,
			OnDoneListener onDoneListener, OnHideListener onHideListener) {
		textField.getOnscreenKeyboard().show(true);
		AbstractGameScreen.keyboard.registerTextField(textField,
				KeyboardConfig.NORMAL, KeyboardConfig.SINGLE_LINE,
				onDoneListener, onHideListener);
	}

	public static CustomTextButton getTextButton(String text,
			NinePatch ninePatch, BitmapFont font, Color buttonColor,
			Color textColor, final OnClickListener listener) {
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = textColor;
		Label lbText = new Label(text, style);
		lbText.setAlignment(Align.center);
		final CustomTextButton textButton = new CustomTextButton(ninePatch,
				lbText);
		textButton.setColor(buttonColor);
		if (listener == null)
			textButton.setTouchable(Touchable.disabled);
		else {
			textButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					textButton.setOrigin(Align.center);
					textButton.setScale(0.99f);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					
					textButton.setScale(1f);
					listener.onClick(x, y);
					event.stop();
				}
			});
		}
		return textButton;
	}
}
