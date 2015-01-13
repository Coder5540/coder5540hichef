package com.aia.hichef.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Clipboard;

public class CustomTextField extends TextField {

	public CustomTextField(String text, TextFieldStyle style) {
		super(text, style);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected InputListener createInputListener() {

		return super.createInputListener();
	}

	@Override
	protected int letterUnderCursor(float x) {

		return super.letterUnderCursor(x);
	}

	@Override
	protected boolean isWordCharacter(char c) {

		return super.isWordCharacter(c);
	}

	@Override
	protected int[] wordUnderCursor(int at) {

		return super.wordUnderCursor(at);
	}

	@Override
	public void setMaxLength(int maxLength) {

		super.setMaxLength(maxLength);
	}

	@Override
	public int getMaxLength() {

		return super.getMaxLength();
	}

	@Override
	public void setOnlyFontChars(boolean onlyFontChars) {

		super.setOnlyFontChars(onlyFontChars);
	}

	@Override
	public void setStyle(TextFieldStyle style) {

		super.setStyle(style);
	}

	@Override
	public TextFieldStyle getStyle() {

		return super.getStyle();
	}

	@Override
	protected void calculateOffsets() {

		super.calculateOffsets();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		Stage stage = getStage();
		boolean focused = stage != null && stage.getKeyboardFocus() == this;

		final BitmapFont font = getStyle().font;
		final Color fontColor = (isDisabled() && getStyle().disabledFontColor != null) ? getStyle().disabledFontColor
				: ((focused && getStyle().focusedFontColor != null) ? getStyle().focusedFontColor
						: getStyle().fontColor);
		final Drawable selection = getStyle().selection;
		final Drawable cursorPatch = getStyle().cursor;
		final Drawable background = (isDisabled() && getStyle().disabledBackground != null) ? getStyle().disabledBackground
				: ((focused && getStyle().focusedBackground != null) ? getStyle().focusedBackground
						: getStyle().background);

		Color color = getColor();
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();

		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		float bgLeftWidth = 0;
		if (background != null) {
			background.draw(batch, x, y, width, height);
			bgLeftWidth = background.getLeftWidth();
		}

		float textY = getTextY(font, background);
		calculateOffsets();

		if (focused && hasSelection && selection != null) {
			drawSelection(selection, batch, font, x + bgLeftWidth, y + textY);
		}

		float yOffset = font.isFlipped() ? -textHeight : 0;
		super.draw(batch, parentAlpha);
		if (displayText.length() == 0) {
			if (getMessageText() != null) {
				if (getStyle().messageFontColor != null) {
					font.setColor(getStyle().messageFontColor.r,
							getStyle().messageFontColor.g,
							getStyle().messageFontColor.b,
							getStyle().messageFontColor.a * parentAlpha);
				} else
					font.setColor(0.7f, 0.7f, 0.7f, parentAlpha);
				BitmapFont messageFont = getStyle().messageFont != null ? getStyle().messageFont
						: font;
				messageFont.draw(batch, getMessageText(), x + bgLeftWidth, y
						+ textY + yOffset);
			}
		}
	}

	@Override
	protected float getTextY(BitmapFont font, Drawable background) {

		return super.getTextY(font, background);
	}

	@Override
	protected void drawSelection(Drawable selection, Batch batch,
			BitmapFont font, float x, float y) {

		super.drawSelection(selection, batch, font, x, y);
	}

	@Override
	protected void drawText(Batch batch, BitmapFont font, float x, float y) {

		super.drawText(batch, font, x, y);
	}

	@Override
	protected void drawCursor(Drawable cursorPatch, Batch batch,
			BitmapFont font, float x, float y) {

		super.drawCursor(cursorPatch, batch, font, x, y);
	}

	@Override
	public void copy() {

		super.copy();
	}

	@Override
	public void cut() {

		super.cut();
	}

	@Override
	public void next(boolean up) {

		super.next(up);
	}

	@Override
	public InputListener getDefaultInputListener() {
		return super.getDefaultInputListener();
	}

	@Override
	public void setTextFieldListener(TextFieldListener listener) {

		super.setTextFieldListener(listener);
	}

	@Override
	public void setTextFieldFilter(TextFieldFilter filter) {

		super.setTextFieldFilter(filter);
	}

	@Override
	public TextFieldFilter getTextFieldFilter() {

		return super.getTextFieldFilter();
	}

	@Override
	public void setFocusTraversal(boolean focusTraversal) {

		super.setFocusTraversal(focusTraversal);
	}

	@Override
	public String getMessageText() {
		return super.getMessageText();
	}

	@Override
	public void setMessageText(String messageText) {
		super.setMessageText(messageText);
	}

	@Override
	public void setText(String str) {

		super.setText(str);
	}

	@Override
	public String getText() {

		return super.getText();
	}

	@Override
	public int getSelectionStart() {

		return super.getSelectionStart();
	}

	@Override
	public String getSelection() {

		return super.getSelection();
	}

	@Override
	public void setSelection(int selectionStart, int selectionEnd) {
		super.setSelection(selectionStart, selectionEnd);
	}

	@Override
	public void selectAll() {

		super.selectAll();
	}

	@Override
	public void clearSelection() {

		super.clearSelection();
	}

	@Override
	public void setCursorPosition(int cursorPosition) {

		super.setCursorPosition(cursorPosition);
	}

	@Override
	public int getCursorPosition() {

		return super.getCursorPosition();
	}

	@Override
	public OnscreenKeyboard getOnscreenKeyboard() {

		return super.getOnscreenKeyboard();
	}

	@Override
	public void setOnscreenKeyboard(OnscreenKeyboard keyboard) {

		super.setOnscreenKeyboard(keyboard);
	}

	@Override
	public void setClipboard(Clipboard clipboard) {

		super.setClipboard(clipboard);
	}

	@Override
	public float getPrefWidth() {

		return super.getPrefWidth();
	}

	@Override
	public float getPrefHeight() {

		return super.getPrefHeight();
	}

	@Override
	public void setRightAligned(boolean rightAligned) {

		super.setRightAligned(rightAligned);
	}

	@Override
	public void setPasswordMode(boolean passwordMode) {

		super.setPasswordMode(passwordMode);
	}

	@Override
	public boolean isPasswordMode() {

		return super.isPasswordMode();
	}

	@Override
	public void setPasswordCharacter(char passwordCharacter) {

		super.setPasswordCharacter(passwordCharacter);
	}

	@Override
	public void setBlinkTime(float blinkTime) {

		super.setBlinkTime(blinkTime);
	}

	@Override
	public void setDisabled(boolean disabled) {

		super.setDisabled(disabled);
	}

	@Override
	public boolean isDisabled() {

		return super.isDisabled();
	}

	@Override
	protected void moveCursor(boolean forward, boolean jump) {

		super.moveCursor(forward, jump);
	}

	@Override
	protected boolean continueCursor(int index, int offset) {

		return super.continueCursor(index, offset);
	}

}
