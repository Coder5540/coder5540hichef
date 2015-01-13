package com.aia.hichef.views.imp;


import java.util.HashMap;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.CustomTable;
import com.aia.hichef.components.CustomTextButton;
import com.aia.hichef.components.DialogInput;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.OnClickListener;
import com.aia.hichef.listener.OnResponseListener;
import com.aia.hichef.listener.ResponseListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.hd.input.keyboard.KeyboardConfig;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class UploadViewAnimation extends View {

	public String			parentView;
	public CustomTable		topBar;
	public CustomTable		content;
	public CustomTable		root;
	public CustomTable		tbIngredient;
	public CustomTable		tbStep;
	public float			BAR_HEIGHT		= 60;
	public Color			color_topbar;
	public Color			color_content;

	public Image			publishFood, back, foodCover;
	public LabelStyle		style, styleElement;

	// =================== user variable ==============
	String					viewTitle		= "";
	LabelStyle				style_normal;
	LabelStyle				style_bold;
	LabelStyle				style_small;
	BitmapFont				font_normal;
	BitmapFont				font_bold;
	BitmapFont				font_small;
	ScrollPane				scrollPane;
	// ==================================
	String					responseData	= "";
	boolean					isLoadData		= false;
	// ================== TextField ================
	TextField				tfTitle;
	HashMap<String, String>	fomula;

	public UploadViewAnimation() {
		super();
		font_bold = Assets.instance.fontFactory.getBold20();
		font_normal = Assets.instance.fontFactory.getLight20();
		font_small = Assets.instance.fontFactory.getRegular15();
		style_small = new LabelStyle(font_small, new Color(77 / 255f,
				77 / 255f, 77 / 255f, 1));
		style_normal = new LabelStyle(font_normal, new Color(0 / 255f,
				0 / 255f, 0 / 255f, 1));
		style_bold = new LabelStyle(font_bold, new Color(77 / 255f, 77 / 255f,
				77 / 255f, 1));
		viewTitle = "Đăng Món Ăn";
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public UploadViewAnimation buildInfo(String parentView, String name,
			Rectangle bound) {
		fomula = new HashMap<String, String>();
		this.parentView = parentView;
		this.name = name;
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);
		color_topbar = new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f);
		color_content = new Color(245 / 255f, 245 / 255f, 245 / 255f, 1f);

		content = new CustomTable();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_content)));
		content.setSize(bound.getWidth(), bound.height - BAR_HEIGHT);
		content.setPosition(0, 0);

		topBar = new CustomTable();
		topBar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_topbar)));
		topBar.setSize(bound.getWidth(), BAR_HEIGHT);
		topBar.setPosition(0, bound.height - BAR_HEIGHT);

		this.addActor(content);
		this.addActor(topBar);
		return this;
	}

	public UploadViewAnimation buildComponent() {
		buildTopBar();
		buildContent();
		buildListener();
		topBar.setVisible(false);
		content.setVisible(false);
		return this;
	}

	private void buildTopBar() {
		topBar.left().align(Align.center);
		topBar.defaults().width(BAR_HEIGHT).height(BAR_HEIGHT);
		topBar.setTouchable(Touchable.enabled);
		back = new Image(Assets.instance.uiA.reg_back);
		publishFood = new Image(Assets.instance.uiM.icono_settings);
		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(255 / 255f, 255 / 255f, 255 / 255f, 1));
		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(0 / 255f, 0 / 255f, 0 / 255f, 1));
		Label lbName = new Label(viewTitle, style);
		lbName.setAlignment(Align.center);

		topBar.add(back).width(30).height(30).padLeft(20);
		topBar.add(lbName).expandX().fillX();
		topBar.add(publishFood).width(40).height(40).padRight(20);
	}

	private void buildContent() {

		// ========= Build Scroll For Content ==============
		root = new CustomTable();
		root.align(Align.top);
		// root.debug();
		scrollPane = new ScrollPane(root);
		scrollPane.setScrollingDisabled(true, false);

		scrollPane.addListener(new ActorGestureListener() {

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.cancel();
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				System.out.println("Pan : " + deltaY);
			}
		});
		root.setTouchable(Touchable.childrenOnly);

		content.add(scrollPane).expand().fillX().align(Align.top);

		// ============== User avatar ================
		CustomTable tbFoodCover = new CustomTable();
		tbFoodCover.setSize(480, 260);
		buildTbFoodCover(tbFoodCover);

		root.add(tbFoodCover).width(tbFoodCover.getHeight())
				.height(tbFoodCover.getHeight()).center();
		root.row();

		addLabel(root, "Tên món ăn");

		NinePatch ninePatch = new NinePatch(Assets.instance.uiA.reg_ninepatch4,
				6, 6, 6, 6);
		tfTitle = buildTextField("", ninePatch);

		root.add(tfTitle).expand().fillX().center().padLeft(30).padRight(30)
				.padBottom(10).height(60).align(Align.top);
		root.row();

		addLabel(root, "Nguyên Liệu");
		tbIngredient = new CustomTable();
		tbIngredient.customUpdate = false;
		tbIngredient.defaults().expand().fillX().padLeft(20).padRight(20)
				.expandY();
		root.add(tbIngredient).center().align(Align.top).fillX().expandY();
		root.row();

		CustomTextButton addIngredient = getTextButton("Thêm Gia Vị",
				new NinePatch(Assets.instance.uiA.reg_ninepatch4, 6, 6, 6, 6),
				Color.GREEN, Color.WHITE, onAddIngredientClick);

		addIngredient.setColor(Color.GREEN);
		addIngredient.setSize(Constants.WIDTH_SCREEN, 60);
		root.add(addIngredient).expand().fillX().center().padLeft(30)
				.padRight(30).padBottom(10).height(60).align(Align.top);
		root.row();
		addLabel(root, "Các bước thực hiện");
		tbStep = new CustomTable();
		tbStep.customUpdate = false;
		tbStep.defaults().expand().fillX().padLeft(20).padRight(20).expandY();
		root.add(tbStep).center().align(Align.top).fillX().expandY();
		root.row();
		CustomTextButton addStep = getTextButton("Thêm bước làm",
				new NinePatch(Assets.instance.uiA.reg_ninepatch4, 6, 6, 6, 6),
				Color.GREEN, Color.WHITE, onAddStepClick);

		addStep.setColor(Color.GREEN);
		addStep.setSize(Constants.WIDTH_SCREEN, 60);
		root.add(addStep).expand().fillX().center().padLeft(30).padRight(30)
				.padBottom(10).height(60).align(Align.top);
		root.row();
	}

	public Label addLabel(CustomTable table, String name) {
		Label lb = new Label(name, style_normal);
		table.add(lb).height(60).expand().fillX().align(Align.left).padTop(20)
				.padLeft(20);
		table.row();
		lb.setTouchable(Touchable.disabled);
		return lb;
	}

	public CustomTextButton getTextButton(String text, NinePatch ninePatch,
			Color buttonColor, Color textColor, final OnClickListener listener) {
		Label lbText = new Label(text, style);
		lbText.setAlignment(Align.center);
		lbText.setColor(textColor);
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

	TextField buildTextField(String text, NinePatch background) {
		final TextField textField = new TextField(text,
				getTextFieldStyle(background));

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

		textField.setSize(2 * Constants.WIDTH_SCREEN / 3 - 40, 45);
		textField.addListener(new InputListener() {
			Vector2	touchPoint	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				textField.setOnscreenKeyboard(AbstractGameScreen.keyboard);
				textField.toFront();
				registerKeyBoard(textField, _onDoneListener, _onHideListener);
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

	TextField buildTextField(String text) {
		final TextField textField = new TextField(text, getTextFieldStyle());

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

		textField.setSize(2 * Constants.WIDTH_SCREEN / 3 - 40, 45);
		textField.addListener(new InputListener() {
			Vector2	touchPoint	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				textField.setOnscreenKeyboard(AbstractGameScreen.keyboard);
				textField.toFront();
				registerKeyBoard(textField, _onDoneListener, _onHideListener);
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
		textArea.addListener(new InputListener() {
			Vector2	touchPoint	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchPoint.set(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (new Vector2(x, y).epsilonEquals(touchPoint, 20)) {
					textArea.setOnscreenKeyboard(AbstractGameScreen.keyboard);
					textArea.toFront();
					registerKeyBoard(textArea);
				}
				super.touchUp(event, x, y, pointer, button);
			}

		});
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

	TextFieldStyle getTextFieldStyle(NinePatch background) {
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(background);
		style.font = Assets.instance.fontFactory.getRegular20();
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = Color.BLACK;
		return style;
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

	private void buildTbFoodCover(CustomTable tb) {
		foodCover = new Image(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch2, new Color(0f / 255f,
						0 / 255f, 0 / 255f, 0.2f)));
		foodCover.setSize(240, 240);
		foodCover.setTouchable(Touchable.disabled);
		foodCover.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (tfTitle.getText().equalsIgnoreCase("")) {
					Toast.makeText(getStage(), "Bạn chưa đặt tên món ăn !",
							Toast.LENGTH_LONG);
					return;
				}
				_viewController.getGameParent().uploader
						.choiceImageAndUploadToServer(responseUpload);
				super.clicked(event, x, y);
			}
		});
		tb.add(foodCover).expand().width(240).height(240).center();
	}

	Image getImage(Color color) {
		Image img = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				0, 0, 5, 5));
		img.setColor(color);
		return img;
	}

	@Override
	public void update() {
		processDataResponse();
		super.update();
	}

	public void processDataResponse() {
	}

	@Override
	public void show() {
		setVisible(false);
		clearActions();
		toFront();
		Action action = Actions.sequence(
				Actions.moveTo(Constants.WIDTH_SCREEN + 2, 0),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						setVisible(true);
						topBar.setVisible(true);
						content.setVisible(true);
					}
				}), Actions.moveTo(0, 0, .3f, Interpolation.exp10Out));
		addAction(action);
		setVisible(true);
		super.show();
	}

	@Override
	public void hide() {
		clearActions();
		Action action = Actions.sequence(
				Actions.moveTo(Constants.WIDTH_SCREEN + 2, getY(), .3f,
						Interpolation.exp10In), Actions.run(new Runnable() {
					@Override
					public void run() {
						setVisible(false);
						topBar.setVisible(false);
						content.setVisible(false);
						_viewController.removeView(getName());
					}
				}));
		addAction(action);
		super.hide();
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	@Override
	public void back() {
		hide();
	}

	private void onPublishFood() {

	}

	private void onBack() {
		back();
	}

	public void buildListener() {
		back.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 0.4f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 1f);
				onBack();
			}
		});

		topBar.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (x > topBar.getWidth() - 80) {
					publishFood.setColor(publishFood.getColor().r,
							publishFood.getColor().g, publishFood.getColor().b,
							0.4f);
					return true;
				}
				if (x < 80) {
					back.setColor(back.getColor().r, back.getColor().g,
							back.getColor().b, 0.4f);
					return true;
				}
				return false;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (publishFood.getColor().a < 0.5f) {
					publishFood.setColor(publishFood.getColor().r,
							publishFood.getColor().g, publishFood.getColor().b,
							1f);
					onPublishFood();
					return;
				}
				if (back.getColor().a < 0.5f) {
					back.setColor(back.getColor().r, back.getColor().g,
							back.getColor().b, 1f);
					onBack();
					return;
				}
			}

		});

		publishFood.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				publishFood.setColor(publishFood.getColor().r,
						publishFood.getColor().g, publishFood.getColor().b,
						0.4f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				publishFood.setColor(publishFood.getColor().r,
						publishFood.getColor().g, publishFood.getColor().b, 1f);
				onPublishFood();
			}
		});
	}

	OnClickListener		onAddIngredientClick	= new OnClickListener() {

													@Override
													public void onClick(
															float x, float y) {

														DialogInput dialog = new DialogInput();
														dialog.build(
																getStage(),
																getViewController());
														dialog.setOnResponseListener(
																fomula,
																responseListener);
														dialog.buildComponent(
																StringSystem.VIEW_DIALOG_INPUT,
																new Rectangle(
																		0,
																		0,
																		Constants.WIDTH_SCREEN,
																		Constants.HEIGHT_SCREEN));
														dialog.show();
													}
												};

	OnClickListener		onAddStepClick			= new OnClickListener() {

													@Override
													public void onClick(
															float x, float y) {
														CustomTable data = new Step(
																1).getRoot();
														tbStep.add(data)
																.pad(20)
																.expand()
																.fillX();
														tbStep.row();
													}
												};

	OnResponseListener	responseListener		= new OnResponseListener() {
													public void onOk() {
													};

													@Override
													public void onOk(
															String name,
															String quality) {
														addFomula(name, quality);
														Toast.makeText(
																getStage(),
																"Thêm thành công !",
																Toast.LENGTH_SHORT);
													}

													@Override
													public void onCancel() {
													}
												};

	OnResponseListener	testDialog				= new OnResponseListener() {

													@Override
													public void onOk(
															String name,
															String quality) {

													}

													@Override
													public void onOk() {
													}

													@Override
													public void onCancel() {
													}
												};

	public void addFomula(String name, String quality) {
		final String fomulaName = name;
		fomula.put(fomulaName, quality);
		final Label lb = new Label(name, style_normal);
		final Label lbQuality = new Label(quality, style_normal);
		final Image imageClear = new Image(Assets.instance.uiP.rgDel);
		final CustomTable table = new CustomTable();
		table.setTouchable(Touchable.enabled);
		table.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (x > imageClear.getX() - 10)
					return;
				editFomula(lb, lbQuality);
			};
		});

		table.setBackground(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch, Color.WHITE));

		table.add(lb).expand().height(60).align(Align.left).padLeft(20);

		table.add(lbQuality).height(60).expand().align(Align.left).padLeft(20);
		tbIngredient.add(table).expand().fillX().height(60).padBottom(20);

		imageClear.setOrigin(Align.center);
		imageClear.setSize(40, 40);
		imageClear.setPosition(440 - imageClear.getWidth(), 10);
		imageClear.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				table.addAction(Actions.sequence(Actions
						.moveBy(-Constants.WIDTH_SCREEN, 0, 0.4f,
								Interpolation.exp5Out), Actions
						.run(new Runnable() {

							@Override
							public void run() {
								tbIngredient.getCells().removeValue(
										tbIngredient.getCell(table), true);
								tbIngredient.removeActor(table);
								fomula.remove(fomulaName);
							}
						})));

				event.stop();
			};
		});

		table.addActor(imageClear);
		tbIngredient.row();
	}

	public void editFomula(Label lb1, Label lbQuality1) {
		final Label lb = lb1;
		final Label lbQuality = lbQuality1;
		DialogInput dialog = new DialogInput();
		dialog.build(getStage(), getViewController());
		dialog.setOnResponseListener(fomula, new OnResponseListener() {

			@Override
			public void onOk(String name, String quality) {
				lb.setText(name);
				lbQuality.setText(quality);
				Toast.makeText(getStage(), "Cập nhật thành công !",
						Toast.LENGTH_SHORT);
			}

			@Override
			public void onOk() {
			}

			@Override
			public void onCancel() {
			}
		});
		dialog.buildComponent(StringSystem.VIEW_DIALOG_INPUT, new Rectangle(0,
				0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN));
		dialog.setTitle("Sửa công thức");
		dialog.setFomulaName(lb.getText().toString());
		dialog.setQuality(lbQuality.getText().toString());
		dialog.setPositiveText("Xong");
		dialog.setNegativeText("Huỷ");
		dialog.show();
	}

	public class Step {
		public CustomTable	rootStep;
		int					index	= 0;
		Image				imgAdd, imgDelete, imgMoveUp;

		public Step(int index) {
			this.index = index;
			rootStep = new CustomTable();
			// rootStep.customUpdate= true;
			rootStep.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(60 / 255f,
							60 / 255f, 60 / 255f, .4f))));
			create();
		}

		public void create() {
			createTitle();
			createControl();
			createTextArea();

			createListener();
		}

		public void createControl() {
			CustomTable tbControl = new CustomTable();
			tbControl.right();

			imgMoveUp = new Image(Assets.instance.uiM.bg_icon_caterogy);
			imgMoveUp.setSize(40, 40);
			imgDelete = new Image(Assets.instance.uiM.bg_icon_caterogy);
			imgDelete.setSize(40, 40);
			imgAdd = new Image(Assets.instance.uiM.bg_icon_caterogy);
			imgAdd.setSize(40, 40);

			tbControl.add(imgMoveUp).padRight(20).center();
			tbControl.add(imgDelete).padRight(20).center();
			tbControl.add(imgAdd).padRight(10).center();
			tbControl.debug();
			rootStep.add(tbControl).pad(0).expand().fillX().height(50);
			rootStep.row();

		}

		private void createTitle() {
			addLabel(rootStep, "Bước " + index);
			rootStep.row();
		}

		private void createTextArea() {
			TextArea textArea = buildTextArea("", 100);
			NinePatch ninePatch = new NinePatch(
					Assets.instance.uiA.reg_ninepatch, 10, 10, 10, 10);
			textArea.getStyle().background = new NinePatchDrawable(ninePatch);
			textArea.setOnscreenKeyboard(AbstractGameScreen.keyboard);
			rootStep.add(textArea).expand().fillX().pad(10).height(100);
			rootStep.row();
		}

		private void createListener() {
			rootStep.setTouchable(Touchable.childrenOnly);
			imgDelete.addListener(new ClickListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					imgDelete.setOrigin(Align.center);
					imgDelete.addAction(Actions.scaleTo(1.2f, 1.2f));
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					imgDelete.setOrigin(Align.center);
					rootStep.addAction(Actions.sequence(Actions.scaleTo(1.2f,
							1.2f, 0.2f, Interpolation.swingIn), Actions.moveBy(
							-Constants.WIDTH_SCREEN, 0, 0.2f,
							Interpolation.exp10Out), Actions
							.run(new Runnable() {
								@Override
								public void run() {
									tbStep.getCells().removeValue(
											tbStep.getCell(rootStep), true);
									tbStep.removeActor(rootStep);
								}
							})));

				}
			});
		}

		public CustomTable getRoot() {
			return rootStep;
		}
	}

	ResponseListener	responseUpload	= new ResponseListener() {

											@Override
											public void success() {

											}

											@Override
											public void response(String string) {
												JsonValue value = (new JsonReader())
														.parse(string);
												String foodName = value
														.getString(ExtParamsKey.FOOD_NAME);
												String urlImage = value
														.getString(ExtParamsKey.URL_IMAGE);
												ImageDownloader.getInstance()
														.download(urlImage,
																foodCover);
											}

											@Override
											public void failed() {

											}
										};
}

