package com.aia.hichef.components;

import java.util.HashMap;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.listener.OnClickListener;
import com.aia.hichef.listener.OnResponseListener;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.utils.UIUtils;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class DialogInput extends View {
	Table						root;
	public CustomTextField		tfName, tfQuality;
	public OnResponseListener	onResponseListener;
	HashMap<String, String>		fomula;
	CustomTextButton			btnOk, btnCancel;
	Label						lbTitle;

	public void buildComponent(String name, Rectangle bound) {
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);
		Image transparent = new Image(Assets.instance.uiM.bg_ninepatch);
		transparent.setBounds(bound.x, bound.y, bound.width, bound.height);
		transparent.setColor(new Color(0 / 255f, 0 / 255f, 0 / 255f, 0.4f));
		transparent.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (AbstractGameScreen.keyboard.isShowing())
					AbstractGameScreen.keyboard.hide();
				hide();
				super.clicked(event, x, y);
			}
		});
		this.addActor(transparent);

		root = new Table();
		root.setTouchable(Touchable.enabled);
		root.setSize(8 * bound.width / 9, bound.height / 3);
		root.top();
		root.defaults().expand().fillX().expandY().center();
		root.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(240 / 255f,
						240 / 255f, 240 / 255f, 1f))));

		tfName = new CustomTextField("",
				UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.uiA.reg_ninepatch2, 10, 10, 10, 10)));
		tfName.setMessageText("Tên nguyên liệu");

		tfQuality = new CustomTextField("",
				UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.uiA.reg_ninepatch2, 10, 10, 10, 10)));
		tfQuality.setMessageText("Số lượng");

		BitmapFont font_normal = Assets.instance.fontFactory.getRegular20();
		LabelStyle style_normal = new LabelStyle(font_normal, new Color(
				0 / 255f, 0 / 255f, 0 / 255f, 1));

		lbTitle = new Label("Thêm Công Thức", style_normal);

		NinePatch bg = new NinePatch(Assets.instance.uiA.reg_ninepatch4, 6, 6,
				6, 6);

		btnOk = UIUtils.getTextButton("Thêm", bg,
				Assets.instance.fontFactory.getRegular20(), Color.WHITE,
				Color.BLACK, onAddListener);
		btnCancel = UIUtils.getTextButton("Huỷ", bg,
				Assets.instance.fontFactory.getRegular20(), Color.WHITE,
				Color.BLACK, onCancelListener);

		Image image = new Image(Assets.instance.uiM.bg_ninepatch);
		image.setColor(Color.BLUE);
		root.add(lbTitle).height(60).colspan(2).padLeft(20);
		root.row();
		root.add(image).height(4).colspan(2).padBottom(20);
		root.row();
		root.add(tfName).padLeft(20).padRight(20).padBottom(20).height(60)
				.colspan(2);
		root.row();
		root.add(tfQuality).padLeft(20).padRight(20).padBottom(30).height(60)
				.colspan(2);
		root.row();

		root.add(btnOk).height(60).padBottom(10);
		root.add(btnCancel).height(60).padBottom(10);
		root.setPosition(getX() + getWidth() / 2 - root.getWidth() / 2, getY()
				+ getHeight() / 2 - root.getHeight() / 2);
		this.addActor(root);

		buildTextFieldListener();
	}

	@Override
	public void show() {
		_viewController.addView(this);
		super.show();
		setVisible(true);
	}

	@Override
	public void hide() {
		super.hide();
		setVisible(false);
		this.clear();
		_viewController.removeView(getName());
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	@Override
	public void back() {
		super.back();
	}

	public void setOnResponseListener(HashMap<String, String> fomula,
			OnResponseListener listener) {
		this.fomula = fomula;
		this.onResponseListener = listener;
	}

	public void buildTextFieldListener() {

		tfName.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				tfName.setOnscreenKeyboard(AbstractGameScreen.keyboard);
				tfName.toFront();
				UIUtils.registerKeyBoard(tfName, _onDoneListener,
						_onHideListener);
				root.addAction(Actions.moveTo(root.getX(), 400, .2f,
						Interpolation.exp10Out));
				return false;
			}
		});

		tfQuality.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				tfQuality.setOnscreenKeyboard(AbstractGameScreen.keyboard);
				tfQuality.toFront();
				UIUtils.registerKeyBoard(tfQuality, _onDoneListener,
						_onHideListener);
				root.addAction(Actions.moveTo(root.getX(), 400, .2f,
						Interpolation.exp10Out));
				return false;
			}
		});

	}

	public void setTitle(String text) {
		if (lbTitle != null)
			lbTitle.setText(text);
	}

	public void setFomulaName(String fomulaName) {
		if (tfName != null)
			tfName.setText(fomulaName);
	}

	public void setQuality(String quality) {
		if (tfQuality != null)
			tfQuality.setText(quality);
	}

	public void setNegativeText(String negative) {
		if (btnCancel != null)
			btnCancel.setText(negative);
	}

	public void setPositiveText(String positive) {
		if (btnOk != null)
			btnOk.setText(positive);
	}

	final OnDoneListener	_onDoneListener		= new OnDoneListener() {
													@Override
													public void done() {
														AbstractGameScreen.keyboard
																.reset();
														getStage()
																.setKeyboardFocus(
																		null);
													}
												};
	final OnHideListener	_onHideListener		= new OnHideListener() {
													@Override
													public void hide() {
														AbstractGameScreen.keyboard
																.reset();
														getStage()
																.setKeyboardFocus(
																		null);
														root.addAction(Actions.moveTo(
																root.getX(),
																getY()
																		+ getHeight()
																		/ 2
																		- root.getWidth()
																		/ 4,
																.2f,
																Interpolation.exp10Out));
													}
												};

	final OnClickListener	onAddListener		= new OnClickListener() {

													@Override
													public void onClick(
															float x, float y) {
														if (AbstractGameScreen.keyboard
																.isShowing())
															AbstractGameScreen.keyboard
																	.hide();

														if (tfName
																.getText()
																.equalsIgnoreCase(
																		"")) {
															Toast.makeText(
																	getStage(),
																	"Bạn chưa nhập tên nguyên liệu !",
																	Toast.LENGTH_SHORT);
															return;
														}
														if (tfQuality
																.getText()
																.equalsIgnoreCase(
																		"")) {
															Toast.makeText(
																	getStage(),
																	"Bạn chưa nhập khối lượng !",
																	Toast.LENGTH_SHORT);
															return;
														}
														if (onResponseListener != null) {
															String fName = tfName
																	.getText();
															String fQuality = tfQuality
																	.getText();

															if (fomula
																	.containsKey(fName)) {
																String value = fomula
																		.get(fName);
																if (value
																		.equalsIgnoreCase(fQuality)) {
																	Toast.makeText(
																			getStage(),
																			"Nguyên liệu đã tồn tại !",
																			Toast.LENGTH_SHORT);
																	return;
																}
															}

															onResponseListener
																	.onOk(tfName
																			.getText(),
																			tfQuality
																					.getText());
															hide();
														}

													}
												};

	final OnClickListener	onCancelListener	= new OnClickListener() {

													@Override
													public void onClick(
															float x, float y) {
														if (AbstractGameScreen.keyboard
																.isShowing())
															AbstractGameScreen.keyboard
																	.hide();
														hide();
													}
												};
}
