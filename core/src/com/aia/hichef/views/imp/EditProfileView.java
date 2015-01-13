package com.aia.hichef.views.imp;

import java.util.ArrayList;

import uitls.input.GamePlatform;
import uitls.input.MyTextField;
import uitls.input.TextInputHelper.FilterStyle;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Img;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.OnCompleteListener;
import com.aia.hichef.listener.OnPickTimeListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.ui.IPickTime;
import com.aia.hichef.ui.PickTime;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.utils.CommonChecker;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.hd.input.keyboard.KeyboardConfig;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class EditProfileView extends View {

	String	parentView	= "";

	public Table	topbar, root, content;
	Table			tbSave, tbCancel;
	BitmapFont		fontBig, fontNormal, fontSmall;
	Color			color_topbar, color_content, color_fontBig,
			color_fontNormal, color_fontSmall;
	float			TOPBAR_HEIGHT	= 60;

	LabelStyle		style_big;
	LabelStyle		style_normal;
	LabelStyle		style_small;
	ScrollPane		scrollPane;
	IPickTime		pickTime;
	MalePicker		malePicker;
	public Image	transparent;
	Image			avatar;

	TextField		tfName, tfEmail, tfPhone;
	Label			lbBirthday;
	TextArea		taAbout;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
		buildPickTime();
	}

	public EditProfileView buildInfo(String parentView, String name,
			Rectangle bound) {
		this.parentView = parentView;
		setName(name);
		this.name = name;
		setBounds(bound.x, bound.y, bound.width, bound.height);
		transparent = new Image(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(0, 0, 0, 0.5f))));
		transparent.setSize(bound.width, bound.height);
		transparent.setVisible(false);
		transparent.setTouchable(Touchable.disabled);
		return this;
	}

	public void buildComponent() {
		config();
		topbar = new Table();
		topbar.setSize(getWidth(), TOPBAR_HEIGHT);
		topbar.setPosition(0, getHeight() - TOPBAR_HEIGHT);
		topbar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_topbar)));
		root = new Table();
		root.align(Align.top);
		root.setSize(getWidth(), getHeight() - TOPBAR_HEIGHT);
		root.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_content)));

		content = new Table();
		content.align(Align.top);
		scrollPane = new ScrollPane(content);
		root.add(scrollPane).expand().fillX().align(Align.top);
		this.addActor(root);
		this.addActor(topbar);

		buildTopbar(topbar);
		buildContent(content);

		setVisible(false);
		pickTime.hide(AnimationType.NONE);
		addActor(transparent);
		transparent.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pickTime.getListener().onCancel("", "", "");
				super.clicked(event, x, y);
			}

		});
	}

	public void buildPickTime() {
		pickTime = new PickTime();
		pickTime.build(getStage());
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> months = new ArrayList<String>();
		ArrayList<String> years = new ArrayList<String>();

		dates.add("28");
		dates.add("29");
		dates.add("30");
		for (int i = 0; i < 30; i++) {
			dates.add("" + (i + 1));
		}
		dates.add("1");
		dates.add("2");
		dates.add("3");

		months.add("Oct");
		months.add("Nov");
		months.add("Dec");
		months.add("Jan");
		months.add("Feb");
		months.add("Mar");
		months.add("Apr");
		months.add("May");
		months.add("Jun");
		months.add("Jul");
		months.add("Aug");
		months.add("Sep");
		months.add("Oct");
		months.add("Nov");
		months.add("Dec");
		months.add("Jan");
		months.add("Feb");
		months.add("Mar");

		for (int i = 0; i < 150; i++) {
			years.add("" + (1900 + i));
		}
		pickTime.setFirstValues(dates).setSecondValues(months)
				.setThirdValues(years).validate().setDefaultDay(18)
				.setDefaultMonth(3).setDefaultYear(92);

		pickTime.setListener(new OnPickTimeListener() {
			@Override
			public void onOk(String date, String month, String year) {
				lbBirthday.setText(date + "-" + month + "-" + year);
				pickTime.hide(null);
				transparent.setTouchable(Touchable.disabled);
				transparent.setVisible(false);
			}

			@Override
			public void onCancel(String date, String month, String year) {
				pickTime.hide(null);
				transparent.setTouchable(Touchable.disabled);
				transparent.setVisible(false);
			}
		});
	}

	void config() {
		fontBig = Assets.instance.fontFactory.getRegular20();
		fontNormal = Assets.instance.fontFactory.getRegular15();
		fontSmall = Assets.instance.fontFactory.getRegular12();

		color_content = new Color(220 / 255f, 220 / 255f, 220 / 255f, 1f);
		color_topbar = new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f);

		color_fontBig = new Color(0 / 255f, 0 / 255f, 0 / 255f, 1f);
		color_fontNormal = new Color(77 / 255f, 77 / 255f, 77 / 255f, 1f);
		color_fontSmall = new Color(77 / 255f, 77 / 255f, 77 / 255f, 1f);

		style_big = new LabelStyle(fontBig, color_fontBig);
		style_normal = new LabelStyle(fontNormal, color_fontNormal);
		style_small = new LabelStyle(fontSmall, color_fontSmall);
	}

	private void buildTopbar(Table topbar) {
		Img imgCancel = new Img(Assets.instance.uiA.reg_icon_cancel);
		imgCancel.setColor(Color.RED);
		imgCancel.setSize(30, 30);
		Img imgSave = new Img(Assets.instance.uiA.reg_icon_save);
		imgSave.setColor(Color.GREEN);
		imgSave.setSize(30, 30);

		LabelStyle styleTitle = new LabelStyle(fontBig, color_fontBig);
		Label lbSave = new Label("Save", styleTitle);
		Label lbCancel = new Label("Cancel", styleTitle);
		Img split = new Img(Assets.instance.uiM.bg_ninepatch);
		split.setColor(new Color(100 / 255f, 100 / 255f, 100 / 255f, 1f));

		tbCancel = new Table();
		tbSave = new Table();
		topbar.add(tbCancel).width(topbar.getWidth() / 2 - 2)
				.height(topbar.getHeight());
		topbar.add(split).width(4).height(3 * TOPBAR_HEIGHT / 4);
		topbar.add(tbSave).width(topbar.getWidth() / 2 - 2)
				.height(topbar.getHeight());

		tbCancel.center();
		tbCancel.add(imgCancel).center().padLeft(60);
		tbCancel.add(lbCancel).expand().fill().center();

		tbSave.center();
		tbSave.add(imgSave).center().padLeft(60);
		tbSave.add(lbSave).expand().fill().center();

		tbCancel.setTouchable(Touchable.enabled);
		tbSave.setTouchable(Touchable.enabled);
		tbCancel.addListener(topBarCancelListener);
		tbSave.addListener(topBarSaveListener);

		imgCancel.setTouchable(Touchable.disabled);
		imgSave.setTouchable(Touchable.disabled);
		lbCancel.setTouchable(Touchable.disabled);
		lbSave.setTouchable(Touchable.disabled);
		split.setTouchable(Touchable.disabled);

	}

	private void buildContent(Table content) {
		content.top();
		avatar = new Image(Assets.instance.uiP.tapFace);
		avatar.setSize(200, 200);
		if (AppPreference.instance._login) {
			ImageDownloader.getInstance().download(
					AppPreference.instance._avatar.replaceAll("100", "200"),
					avatar);
		}

		content.add(avatar).height(200).width(200).pad(20).align(Align.top);
		content.row();

		Label lbName = new Label("Name", style_big);
		content.add(lbName).expand().fillX().padLeft(20).padRight(20)
				.height(50);
		content.row();

		tfName = buildTextField(AppPreference.instance._title);
		content.add(tfName).expand().fillX().center().padLeft(20).padRight(20)
				.padBottom(20).height(50).align(Align.top);
		content.row();

		Label labelBirthday = new Label("Birthday", style_big);
		content.add(labelBirthday).expand().fillX().padLeft(20).padRight(20)
				.height(50);
		content.row();

		lbBirthday = new Label("16-01-1992", style_big);
		Table tbBirthday = new Table();
		tbBirthday.setSize(100, 50);
		tbBirthday.add(lbBirthday);
		tbBirthday.setTouchable(Touchable.enabled);
		tbBirthday.setBackground(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch4, new Color(245f / 255,
						245f / 255, 245f / 255, 1)));

		tbBirthday.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				onShowPickTime();
				event.stop();
			}
		});

		content.add(tbBirthday).padLeft(20).left().height(50);
		content.row();

		Label lbGender = new Label("Gender", style_big);
		content.add(lbGender).expand().fillX().padLeft(20).padRight(20)
				.padBottom(20).height(50);
		content.row();

		Table malePanel = new Table();
		malePanel.setSize(content.getWidth() - 40, 90);
		malePicker = new MalePicker(malePanel);
		malePicker.setChoice(malePicker
				.getChoice(AppPreference.instance._gender));
		content.add(malePanel).expand().fill().padLeft(20).padBottom(20)
				.padTop(20);
		content.row();

		Label lbEmail = new Label("Email", style_big);
		content.add(lbEmail).expand().fillX().padLeft(20).padRight(20)
				.height(50);
		content.row();

		tfEmail = buildTextField(AppPreference.instance._email);
		content.add(tfEmail).expand().fillX().center().padLeft(20).padRight(20)
				.padBottom(20).height(50).align(Align.top);
		content.row();

		Label lbPhone = new Label("Số điện thoại ", style_big);
		content.add(lbPhone).expand().fillX().padLeft(20).padRight(20)
				.height(50);
		content.row();

		tfPhone = buildTextField("" + AppPreference.instance._phone);
		content.add(tfPhone).expand().fillX().center().padLeft(20).padRight(20)
				.padBottom(20).height(50).align(Align.top);
		content.row();

		Label lbAbout = new Label("About me", style_big);
		content.add(lbAbout).expand().fillX().padLeft(20).padRight(20)
				.height(50);
		content.row();
		taAbout = buildTextArea("" + AppPreference.instance._about, 100);
		content.add(taAbout).expand().fillX().center().padLeft(20).padRight(20)
				.padBottom(20).height(200).align(Align.top);
		content.row();

	}

	public void onShowPickTime() {
		if (AbstractGameScreen.keyboard.isShowing()) {
			AbstractGameScreen.keyboard.hide();
			AbstractGameScreen.keyboard.clear();
		}
		transparent.setTouchable(Touchable.enabled);
		transparent.setVisible(true);
		pickTime.show(null);
	}

	void onKeyboardShow() {
		if (scrollPane.getY() > 0) {
			scrollPane.addAction(Actions.moveTo(0, 0, .2f,
					Interpolation.exp10Out));
		}
	}

	void onKeyboardHide() {
		if (scrollPane.getY() > 0) {
			scrollPane.addAction(Actions.moveTo(0, 0, .2f,
					Interpolation.exp10Out));
		}
	}

	MyTextField buildTextField(String text) {
		final MyTextField textField = new MyTextField("", getTextFieldStyle(),
				GamePlatform.helper, FilterStyle.ALPHANUMERIC);

		textField.addListener(new InputListener() {
			Vector2	touchPoint	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				textField.localToStageCoordinates(touchPoint.set(x, y));
				if (touchPoint.y < Constants.HEIGHT_SCREEN / 2) {
					scrollPane.addAction(Actions.moveBy(0, -touchPoint.y + y
							+ Constants.HEIGHT_SCREEN / 2, .2f,
							Interpolation.exp10Out));
				}
				return false;
			}
		});
		return textField;
	}

	TextArea buildTextArea(String text, float height) {
		final TextArea textArea = new TextArea(text, getTextFieldStyle());
		textArea.setOnscreenKeyboard(AbstractGameScreen.keyboard);
		textArea.setMaxLength(200);
		final OnDoneListener _onDoneListener = new OnDoneListener() {
			@Override
			public void done() {
				if (scrollPane.getY() > 0) {
					scrollPane.addAction(Actions.moveTo(0, 0, .2f,
							Interpolation.exp10Out));
				}
				AbstractGameScreen.keyboard.reset();
				getStage().setKeyboardFocus(null);
			}
		};
		final OnHideListener _onHideListener = new OnHideListener() {
			@Override
			public void hide() {
				AbstractGameScreen.keyboard.reset();
				getStage().setKeyboardFocus(null);
				if (scrollPane.getY() > 0) {
					scrollPane.addAction(Actions.moveTo(0, 0, .2f,
							Interpolation.exp10Out));
				}
			}
		};

		textArea.setSize(2 * Constants.WIDTH_SCREEN / 3 - 40, height);
		// textArea.addListener(new InputListener() {
		// Vector2 touchPoint = new Vector2();
		//
		// @Override
		// public boolean touchDown(InputEvent event, float x, float y,
		// int pointer, int button) {
		// touchPoint.set(x, y);
		// return true;
		// }
		//
		// @Override
		// public void touchUp(InputEvent event, float x, float y,
		// int pointer, int button) {
		// if (new Vector2(x, y).epsilonEquals(touchPoint, 20)) {
		// textArea.setOnscreenKeyboard(AbstractGameScreen.keyboard);
		// textArea.toFront();
		// registerKeyBoard(textArea);
		// }
		// super.touchUp(event, x, y, pointer, button);
		// }
		//
		// });
		textArea.addListener(new InputListener() {
			Vector2	touchPoint	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				textArea.setOnscreenKeyboard(AbstractGameScreen.keyboard);
				textArea.toFront();
				registerKeyBoard(textArea, _onDoneListener, _onHideListener);
				textArea.localToStageCoordinates(touchPoint.set(x, y));
				if (touchPoint.y < Constants.HEIGHT_SCREEN / 2) {
					scrollPane.addAction(Actions.moveBy(0, -touchPoint.y + y
							+ Constants.HEIGHT_SCREEN / 2, .2f,
							Interpolation.exp10Out));
				}
				return false;
			}
		});

		return textArea;
	}

	TextFieldStyle getTextFieldStyle() {
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.textfield, 5, 5, 5, 5));
		style.font = Assets.instance.fontFactory.getRegular20();
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = Color.BLACK;
		return style;
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void show(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {
		super.show(newViewAnimation, oldViewAnimationType,
				newViewCompleteListener, oldViewCompleteListener);
	}

	@Override
	public void show(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {
		super.show(newViewAnimation, newViewCompleteListener);
	}

	@Override
	public void show() {
		super.show();
		Action action = Actions.sequence(
				Actions.moveBy(0, -Constants.HEIGHT_SCREEN + 20),
				Actions.visible(true),
				Actions.moveTo(0, 0, .2f, Interpolation.exp5Out),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (AppPreference.instance._login) {
							ImageDownloader.getInstance().download(
									AppPreference.instance._avatar.replaceAll(
											"100", "200"), avatar);
						}
					}
				}));
		addAction(action);
	}

	@Override
	public void hide() {
		addAction(Actions.sequence(Actions.moveBy(0, Constants.HEIGHT_SCREEN,
				.2f, Interpolation.exp10In), Actions.run(new Runnable() {
			@Override
			public void run() {
				setViewState(ViewState.HIDE);
				setTouchable(Touchable.disabled);
				TraceView.instance.removeView(getName());
				setVisible(false);
				pickTime.dispose();
				_viewController.removeView(getName());
			}
		})));
	}

	@Override
	public void hide(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {

		super.hide(newViewAnimation, oldViewAnimationType,
				newViewCompleteListener, oldViewCompleteListener);
	}

	@Override
	public void hide(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {

		super.hide(newViewAnimation, newViewCompleteListener);
	}

	public void back() {
		hide();
	};

	public void saveInfo() {
		if (AbstractGameScreen.keyboard.isShowing())
			AbstractGameScreen.keyboard.hide();
		final String email = tfEmail.getText();
		if (!email.equalsIgnoreCase("") && !CommonChecker.validEmail(email)) {
			Toast.makeText(getStage(), "Email không hợp lệ !",
					Toast.LENGTH_LONG);
			return;
		}
		final String phone = tfPhone.getText();
		if (!phone.equalsIgnoreCase("") && !CommonChecker.validPhone(phone)) {
			Toast.makeText(getStage(), "Số điện thoại không hợp lệ !",
					Toast.LENGTH_LONG);
			return;
		}
		final String title = tfName.getText();
		final String content = taAbout.getText();
		final String birthday = lbBirthday.getText().toString();
		final int gender = malePicker.getGender();

		HttpResponseListener listener = new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				String responseData = httpResponse.getResultAsString();
				JsonValue value = (new JsonReader()).parse(responseData);
				System.out.println(value);
				String result = value.getString(ExtParamsKey.RESULT);
				if (result.equalsIgnoreCase("failed")) {
					Toast.makeText(getStage(), "Result Failed",
							Toast.LENGTH_SHORT);
				} else {
					saveSuccess(title, birthday, phone, gender, content, email);
				}
			}

			@Override
			public void failed(Throwable t) {
				Toast.makeText(getStage(), "Request Failed", Toast.LENGTH_SHORT);
			}

			@Override
			public void cancelled() {

			}
		};

		Request.getInstance().updateUserInfo(title, birthday, phone, gender,
				content, email, listener);

	}

	private void saveSuccess(String title, String birthday, String phone,
			int gender, String content, String email) {
		AppPreference.instance._title = title;
		AppPreference.instance._birthday = birthday;
		AppPreference.instance._phone = phone;
		AppPreference.instance._gender = gender;
		AppPreference.instance._about = content;
		AppPreference.instance._email = email;
		AppPreference.instance.saveUserInformation();
		Toast.makeText(getStage(), "Cập nhật thông tin thành công !",
				Toast.LENGTH_SHORT);
		addAction(Actions.delay(1f, Actions.run(new Runnable() {
			@Override
			public void run() {
				hide();
			}
		})));
	}

	public void registerKeyBoard(TextField textField,
			OnDoneListener onDoneListener, OnHideListener onHideListener) {
		textField.getOnscreenKeyboard().show(true);
		AbstractGameScreen.keyboard.registerTextField(textField,
				KeyboardConfig.NORMAL, KeyboardConfig.SINGLE_LINE,
				onDoneListener, onHideListener);
	}

	public void registerKeyBoard(TextArea textArea) {
		textArea.getOnscreenKeyboard().show(true);
		AbstractGameScreen.keyboard.registerTextField(textArea,
				KeyboardConfig.NORMAL, KeyboardConfig.SINGLE_LINE);
		getStage().setKeyboardFocus(textArea);
	}

	class MalePicker {
		Table	root;
		Image	iMale, iFemale, iAnother;
		Label	lbMale, lbFemale, lbAnother;
		Choice	choice;

		public MalePicker(Table root) {
			super();
			this.root = root;
			System.out.println("root size");
			create();
			setChoice(Choice.MALE);
		}

		public void create() {
			iMale = new Image(Assets.instance.uiP.radioNoChoice);
			iFemale = new Image(Assets.instance.uiP.radioNoChoice);
			iAnother = new Image(Assets.instance.uiP.radioNoChoice);
			iMale.setSize(30, 30);
			iFemale.setSize(30, 30);
			iAnother.setSize(30, 30);

			lbMale = new Label("Nam", style_big);
			lbFemale = new Label("Nữ", style_big);
			lbAnother = new Label("Khác", style_big);

			iMale.setPosition(20, 0);
			iFemale.setPosition((Constants.WIDTH_SCREEN - 20) / 3, 0);
			iAnother.setPosition(2 * (Constants.WIDTH_SCREEN - 3 * 20) / 3, 0);

			lbMale.setPosition(iMale.getX() + iMale.getWidth() + 10,
					iMale.getY());
			lbFemale.setPosition(iFemale.getX() + iFemale.getWidth() + 10,
					iFemale.getY());
			lbAnother.setPosition(iAnother.getX() + iAnother.getWidth() + 10,
					iAnother.getY());

			root.addActor(iMale);
			root.addActor(iFemale);
			root.addActor(iAnother);
			root.addActor(lbMale);
			root.addActor(lbFemale);
			root.addActor(lbAnother);
			buildListener();
		}

		public void buildListener() {
			ClickListener maleClick = new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setChoice(Choice.MALE);
					event.stop();
				}
			};
			ClickListener femaleClick = new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setChoice(Choice.FEMALE);
					event.stop();
				}
			};
			ClickListener anotherClick = new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setChoice(Choice.ANOTHER);
					event.stop();
				}
			};

			iMale.addListener(maleClick);
			lbMale.addListener(maleClick);
			iFemale.addListener(femaleClick);
			lbFemale.addListener(femaleClick);
			iAnother.addListener(anotherClick);
			lbAnother.addListener(anotherClick);
		}

		public void setChoice(Choice choice) {
			this.choice = choice;
			if (choice == Choice.MALE) {
				iMale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioChoice));
				iFemale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
				iAnother.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
			}
			if (choice == Choice.FEMALE) {
				iMale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
				iFemale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioChoice));
				iAnother.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
			}
			if (choice == Choice.ANOTHER) {
				iMale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
				iFemale.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioNoChoice));
				iAnother.setDrawable(new TextureRegionDrawable(
						Assets.instance.uiP.radioChoice));
			}
		}

		public String getText() {
			if (choice == Choice.MALE)
				return "Nam";
			if (choice == Choice.FEMALE)
				return "Nữ";
			if (choice == Choice.ANOTHER)
				return "Khác";
			return "";
		}

		public int getGender() {
			if (choice == Choice.MALE)
				return 1;
			if (choice == Choice.FEMALE)
				return 0;
			if (choice == Choice.ANOTHER)
				return -1;
			return 1;

		}

		public Choice getChoice(String text) {
			if (text.equalsIgnoreCase("Nam") || text.equalsIgnoreCase("male"))
				return Choice.MALE;
			if (text.equalsIgnoreCase("Nữ") || text.equalsIgnoreCase("female"))
				return Choice.FEMALE;
			return Choice.ANOTHER;
		}

		public Choice getChoice(int value) {
			if (value == 0)
				return Choice.FEMALE;
			if (value == 1)
				return Choice.MALE;
			return Choice.ANOTHER;
		}
	}

	ActorGestureListener	topBarCancelListener	= new ActorGestureListener() {

														public void touchDown(
																InputEvent event,
																float x,
																float y,
																int pointer,
																int button) {
															tbCancel.addAction(Actions
																	.alpha(.4f));
														};

														public void touchUp(
																InputEvent event,
																float x,
																float y,
																int pointer,
																int button) {
															System.out
																	.println(" Topbar Cancel is call");
															tbCancel.addAction(Actions
																	.alpha(1f));
															if (AbstractGameScreen.keyboard
																	.isShowing()) {
																AbstractGameScreen.keyboard
																		.hide();
																return;
															}
															hide();
														};
													};

	ActorGestureListener	topBarSaveListener		= new ActorGestureListener() {
														public void touchDown(
																InputEvent event,
																float x,
																float y,
																int pointer,
																int button) {
															tbSave.addAction(Actions
																	.alpha(.4f));
														};

														public void touchUp(
																InputEvent event,
																float x,
																float y,
																int pointer,
																int button) {
															System.out
																	.println(" Topbar save is call");
															saveInfo();
															tbSave.addAction(Actions
																	.alpha(1f));
														};
													};

	enum Choice {
		MALE, FEMALE, ANOTHER;
	}
}
