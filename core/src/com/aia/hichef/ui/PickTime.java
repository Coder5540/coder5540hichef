package com.aia.hichef.ui;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.GalleryViewVertical;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.OnPickTimeListener;
import com.aia.hichef.utils.CommonChecker;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class PickTime implements IPickTime {
	// ============= data variable ================
	public int	dateIndex, monthIndex, yearIndex;
	public ArrayList<String>	dateValues, monthValues, yearValues;
	public String				leftText, rightText, titleText;

	// ============= UI variable =================
	public Table				root, tbTitle;
	public Table				content, contentDate, contentMonth,
			contentYear;
	public Table				tbOK, tbCancel;
	public BitmapFont			font;
	public LabelStyle			labelStyle;
	float						width, height;
	public Label				titleLabel;
	GalleryViewVertical			gDate, gMonth, gYear;

	public OnPickTimeListener	_listener;
	private Stage				stage;
	boolean						visible	= false;
	public Table				container;

	public IPickTime build(Stage stage) {
		this.stage = stage;

		config();
		return this;
	}

	public void config() {
		dateValues = new ArrayList<String>();
		monthValues = new ArrayList<String>();
		yearValues = new ArrayList<String>();
		font = Assets.instance.fontFactory.getMedium20();
		labelStyle = new LabelStyle(font, Color.BLACK);

		leftText = "OK";
		rightText = "CANCEL";

		width = 399;
		height = 400;

		container = new Table();
		container.setSize(width, height);
		container.setPosition(Constants.WIDTH_SCREEN / 2 - container.getWidth()
				/ 2, Constants.HEIGHT_SCREEN / 2 - container.getHeight() / 2);
		container.setBackground(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch, new Color(1, 1, 1, 1)));

		root = new Table();
		root.setSize(width, height);
		tbOK = new Table();
		tbOK.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		tbOK.setSize(root.getWidth() / 2 - 1, 60);
		tbOK.setTouchable(Touchable.enabled);
		tbOK.center();

		tbCancel = new Table();
		tbCancel.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		tbCancel.setSize(root.getWidth() / 2 - 1, 60);
		tbCancel.setTouchable(Touchable.enabled);
		tbCancel.center();

		tbOK.add(getLabel(leftText)).expand().fill();
		tbCancel.add(getLabel(rightText)).expand().fill();

		content = new Table();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		content.setSize(root.getWidth(), root.getHeight() - 60);
		content.setTouchable(Touchable.enabled);
		content.center();

		content.setPosition(0, 60);
		tbCancel.setPosition(root.getWidth() / 2 + 1, 0);
		tbOK.setPosition(0, 0);

		root.addActor(content);
		root.addActor(tbOK);
		root.addActor(tbCancel);

		contentDate = new Table();
		contentDate.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		contentDate.setSize(content.getWidth() / 3, content.getHeight());
		contentDate.setTouchable(Touchable.enabled);
		contentDate.center();

		contentMonth = new Table();
		contentMonth.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		contentMonth.setSize(content.getWidth() / 3, content.getHeight());
		contentMonth.setTouchable(Touchable.enabled);
		contentMonth.center();

		contentYear = new Table();
		contentYear.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		contentYear.setSize(content.getWidth() / 3, content.getHeight());
		contentYear.setTouchable(Touchable.enabled);
		contentYear.center();

		contentDate.setPosition(0, 0);
		contentMonth.setPosition(content.getWidth() / 3, 0);
		contentYear.setPosition(2 * content.getWidth() / 3, 0);

		content.addActor(contentDate);
		content.addActor(contentMonth);
		content.addActor(contentYear);

		gDate = new GalleryViewVertical(contentDate, 3);
		gMonth = new GalleryViewVertical(contentMonth, 3);
		gYear = new GalleryViewVertical(contentYear, 3);

		gDate.pages.activeScrollRound(true);
		gMonth.pages.activeScrollRound(true);
		gYear.pages.activeScrollRound(true);

		// stage.addActor(root);
		container.addActor(root);
		buildTitle();
		// stage.addActor(tbTitle);
		container.addActor(tbTitle);
		stage.addActor(container);
		buildListener();
	}

	void buildTitle() {
		titleText = "Chọn Ngày";
		tbTitle = new Table();
		tbTitle.setSize(root.getWidth(), 60);
		tbTitle.setPosition(container.getWidth() / 2 - tbTitle.getWidth() / 2,
				root.getY() + root.getHeight());
		Color titleColor = new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f);
		tbTitle.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, titleColor)));
		// tbTitle.setBackground(Assets.instance.uiA.getBackground(
		// Assets.instance.uiA.reg_ninepatch4, titleColor));

		titleLabel = getLabel(titleText);
		titleLabel.setAlignment(Align.center, Align.left);
		titleLabel.setX(20);
		tbTitle.add(titleLabel).expand().fill().left().padLeft(20);
	}

	public void buildListener() {
		tbOK.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				tbOK.addAction(Actions.alpha(0.4f));
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				tbOK.addAction(Actions.alpha(1f));
				String dateFomat = StringSystem.DATEFOMAT_DMY;
				String time = getTime(dateFomat);
				if (CommonChecker.validDate(time, dateFomat)) {
					_listener.onOk(getDate(), getMonth(), getYear());
				} else {
					Toast.makeText(stage, "Ngày " + time + " Không hợp lệ",
							Toast.LENGTH_SHORT);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		tbCancel.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				tbCancel.addAction(Actions.alpha(0.4f));
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				tbCancel.addAction(Actions.alpha(1f));
				_listener.onCancel(getDate(), getMonth(), getYear());
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public void show(AnimationType type) {
		container.toFront();
		root.toFront();
		tbTitle.toFront();
		visible = true;

		container.setVisible(true);

		root.setVisible(true);
		content.setVisible(true);
		tbOK.setVisible(true);
		tbCancel.setVisible(true);
		tbTitle.setVisible(true);
		root.addAction(Actions.delay(0.1f, Actions.run(new Runnable() {
			@Override
			public void run() {
				setDefaultDay(dateIndex);
				setDefaultMonth(monthIndex);
				setDefaultYear(yearIndex);
			}
		})));
	}

	@Override
	public void hide(AnimationType type) {
		visible = false;
		container.setVisible(false);
		root.setVisible(false);
		content.setVisible(false);
		tbOK.setVisible(false);
		tbCancel.setVisible(false);
		tbTitle.setVisible(false);
	}

	@Override
	public String getDate() {
		dateIndex = gDate.pages.getCurrentPage();
		return dateValues.get(gDate.pages.getCurrentPage());
	}

	@Override
	public String getMonth() {
		monthIndex = gMonth.pages.getCurrentPage();
		return monthValues.get(gMonth.pages.getCurrentPage());
	}

	@Override
	public String getYear() {
		yearIndex = gYear.pages.getCurrentPage();
		return yearValues.get(gYear.pages.getCurrentPage());
	}

	@Override
	public IPickTime setFirstValues(ArrayList<String> listDays) {
		this.dateValues = listDays;
		return this;
	}

	@Override
	public IPickTime setSecondValues(ArrayList<String> listMonths) {
		this.monthValues = listMonths;
		return this;
	}

	@Override
	public IPickTime setThirdValues(ArrayList<String> listYears) {
		this.yearValues = listYears;
		return this;
	}

	@Override
	public IPickTime setDefaultDay(int index) {
		this.dateIndex = index;
		gDate.pages.focusOnPage(index);
		return this;
	}

	@Override
	public IPickTime setDefaultMonth(int index) {
		this.monthIndex = index;
		gMonth.pages.focusOnPage(index);
		return this;
	}

	@Override
	public IPickTime setDefaultYear(int index) {
		this.yearIndex = index;
		gYear.pages.focusOnPage(index);
		return this;
	}

	public IPickTime setTitle(String title) {
		this.titleText = title;
		titleLabel.setText(title);
		return this;
	}

	@Override
	public String getTime(String fomat) {
		if (fomat.equalsIgnoreCase(StringSystem.DATEFOMAT_DMY))
			return getDate() + "-" + getMonth() + "-" + getYear();
		if (fomat.equalsIgnoreCase(StringSystem.DATEFOMAT_MDY))
			return getMonth() + "-" + getDate() + "-" + getYear();
		if (fomat.equalsIgnoreCase(StringSystem.DATEFOMAT_YMD))
			return getYear() + "-" + getMonth() + "-" + getDate();
		return getDate() + "-" + getMonth() + "-" + getYear();
	}

	@Override
	public void reset() {

	}

	public IPickTime validate() {
		Color color = new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f);
		// ======= Validate day ==============
		if (dateValues.size() > 0) {
			for (int i = 0; i < dateValues.size(); i++) {
				final int index = i;
				Table date = gDate.newPage(Assets.instance.uiA.getBackground(
						Assets.instance.uiA.reg_ninepatch4, color));
				date.setTouchable(Touchable.enabled);
				date.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (index == 0)
							gDate.pages.fling(0.5f, 0, 500);
						else if (index == dateValues.size() - 1)
							gDate.pages.fling(0.4f, 0, -400);
						else
							gDate.pages.focusOnPage(index);
						event.stop();
					}
				});
				date.add(getLabel(dateValues.get(i))).expand().fill();
			}
		}

		// ======= Validate month ==============
		if (monthValues.size() > 0) {
			for (int i = 0; i < monthValues.size(); i++) {
				final int index = i;
				// Table month = gMonth
				// .newPage(new NinePatchDrawable(new NinePatch(
				// Assets.instance.uiM.bg_ninepatch, color)));
				Table month = gMonth.newPage(Assets.instance.uiA.getBackground(
						Assets.instance.uiA.reg_ninepatch4, color));
				month.setTouchable(Touchable.enabled);
				month.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {

						if (index == 0)
							gMonth.pages.fling(0.5f, 0, 500);
						else if (index == monthValues.size() - 1)
							gMonth.pages.fling(0.4f, 0, -400);
						else
							gMonth.pages.focusOnPage(index);
						super.clicked(event, x, y);
					}
				});
				month.add(getLabel(monthValues.get(i))).expand().fill();
			}
		}

		// ======= Validate year ==============
		if (yearValues.size() > 0) {
			for (int i = 0; i < yearValues.size(); i++) {
				final int index = i;
				// Table year = gYear.newPage(new NinePatchDrawable(new
				// NinePatch(
				// Assets.instance.uiM.bg_ninepatch, color)));
				Table year = gYear.newPage(Assets.instance.uiA.getBackground(
						Assets.instance.uiA.reg_ninepatch4, color));
				year.setTouchable(Touchable.enabled);
				year.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (index == 0)
							gYear.pages.fling(0.5f, 0, 500);
						else if (index == yearValues.size() - 1)
							gYear.pages.fling(0.4f, 0, -400);
						else
							gYear.pages.focusOnPage(index);
						super.clicked(event, x, y);
					}
				});
				year.add(getLabel(yearValues.get(i))).expand().fill();
			}
		}
		return this;
	}

	public Label getLabel(String text) {
		Label label = new Label(text, labelStyle);
		label.setTouchable(Touchable.disabled);
		label.setAlignment(Align.center, Align.center);
		return label;
	}

	@Override
	public IPickTime setListener(OnPickTimeListener _listener) {
		this._listener = _listener;
		return this;
	}

	@Override
	public OnPickTimeListener getListener() {
		return _listener;
	}

	@Override
	public boolean visible() {
		return visible;
	}

	@Override
	public void dispose() {
		stage.getActors().removeValue(container, false);
	}
}
