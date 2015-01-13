package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.MarkImage;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UserReciepeView extends View {

	public String		parentView;
	public Table		topBar;
	public Table		content;
	public Table		root;
	public float		BAR_HEIGHT		= 60;
	public Color		color_topbar;
	public Color		color_content;

	public Image		addRecipe, back, foodCover;
	public LabelStyle	style, styleElement;

	// =================== user variable ==============
	String				viewTitle		= "";
	LabelStyle			style_normal;
	LabelStyle			style_small;
	LabelStyle			style_bold;
	BitmapFont			font_normal;
	BitmapFont			font_bold;
	BitmapFont			font_small;

	// ==================================
	String				responseData	= "";
	boolean				isLoadData		= false;

	public UserReciepeView() {
		super();
		font_bold = Assets.instance.fontFactory.getBold20();
		font_normal = Assets.instance.fontFactory.getRegular20();
		font_small = Assets.instance.fontFactory.getRegular15();
		style_small = new LabelStyle(font_small, new Color(77 / 255f,
				77 / 255f, 77 / 255f, 1));
		style_normal = new LabelStyle(font_normal, new Color(77 / 255f,
				77 / 255f, 77 / 255f, 1));
		style_bold = new LabelStyle(font_bold, new Color(77 / 255f, 77 / 255f,
				77 / 255f, 1));
		viewTitle = "Các món đã đăng";
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public UserReciepeView buildInfo(String parentView, String name,
			Rectangle bound) {
		this.parentView = parentView;
		this.name = name;
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);

		color_topbar = new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f);
		color_content = new Color(255 / 255f, 255 / 255f, 255 / 255f, 1f);

		content = new Table();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_content)));
		content.setSize(bound.getWidth(), bound.height - BAR_HEIGHT);
		content.setPosition(0, 0);

		topBar = new Table();
		topBar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_topbar)));
		topBar.setSize(bound.getWidth(), BAR_HEIGHT);
		topBar.setPosition(0, bound.height - BAR_HEIGHT);

		this.addActor(content);
		this.addActor(topBar);
		return this;
	}

	public UserReciepeView buildComponent() {
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

		addRecipe = new Image(Assets.instance.uiM.icono_settings);

		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(255 / 255f, 255 / 255f, 255 / 255f, 1));
		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(0 / 255f, 0 / 255f, 0 / 255f, 1));
		Label lbName = new Label(viewTitle, style);
		lbName.setAlignment(Align.center);

		topBar.add(back).width(30).height(30).padLeft(20);
		topBar.add(lbName).expandX().fillX();
		topBar.add(addRecipe).width(40).height(40).padRight(20);
	}

	private void buildContent() {

		// ========= Build Scroll For Content ==============
		root = new Table();
		root.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(240 / 255f,
						240 / 255f, 240 / 255f, 1f))));
		this.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(240 / 255f,
						240 / 255f, 240 / 255f, 1f))));

		root.align(Align.top);
		ScrollPane scrollPane = new ScrollPane(root);
		scrollPane.setScrollingDisabled(true, false);
		content.add(scrollPane).expand().fillX().align(Align.top);

		for (int i = 0; i < 10; i++) {
			Table rcp = new Reciepe("Món chưa đặt tên",
					AppPreference.instance._title).getRoot();
			root.add(rcp).width(rcp.getWidth()).height(rcp.getHeight())
					.center().padTop(20);
			root.row();
		}
	}

	void buildTbFoodCover(Table tb) {
		foodCover = new Image(Assets.instance.uiA.getBackground(
				Assets.instance.uiA.reg_ninepatch2, new Color(0f / 255f,
						0 / 255f, 0 / 255f, 1f)));
		foodCover.setSize(240, 240);
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
				}), Actions.moveTo(0, 0, .3f, Interpolation.exp10Out),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						IViews view = _viewController
								.getView(StringSystem.VIEW_MAIN_MENU);
						if (view.getViewState() == ViewState.SHOW) {
							view.hide();
						}
					}
				}));
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

	private void onAddReciepe() {
		UploadFoodView uploadReciepe = new UploadFoodView();
		uploadReciepe.build(getStage(), getViewController());
		uploadReciepe.buildInfo(
				getName(),
				StringSystem.VIEW_UPLOAD_RECIEPE,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN)).buildComponent();
		_viewController.addView(uploadReciepe);
		uploadReciepe.show();

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
					addRecipe.setColor(addRecipe.getColor().r,
							addRecipe.getColor().g, addRecipe.getColor().b,
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
				if (addRecipe.getColor().a < 0.5f) {
					addRecipe.setColor(addRecipe.getColor().r,
							addRecipe.getColor().g, addRecipe.getColor().b, 1f);
					onAddReciepe();
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

		addRecipe.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				addRecipe.setColor(addRecipe.getColor().r,
						addRecipe.getColor().g, addRecipe.getColor().b, 0.4f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRecipe.setColor(addRecipe.getColor().r,
						addRecipe.getColor().g, addRecipe.getColor().b, 1f);
				onAddReciepe();
			}
		});
	}

	public class Reciepe {
		Table		container, tbFood, tbTitle;
		String		titleReciepe;
		Image		foodCover;
		MarkImage	avatar;
		String		userName;

		public Reciepe(Table container, String titleReciepe, String userName) {
			super();
			this.titleReciepe = titleReciepe;
			this.container = container;
			this.userName = userName;
			buildReciepe(container);
		}

		public Reciepe(String titleReciepe, String userName) {
			super();
			container = new Table();
			container
					.setSize(
							Constants.WIDTH_SCREEN - 10,
							(Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR) / 3 - 10);
			container.setBackground(Assets.instance.uiA.getBackground(
					Assets.instance.uiA.reg_ninepatch4, Color.WHITE));
			this.titleReciepe = titleReciepe;
			this.userName = userName;
			buildReciepe(container);
		}

		public void buildReciepe(Table container) {

			foodCover = new Image(Assets.instance.uiA.getBackground(
					Assets.instance.uiA.reg_ninepatch, new Color(200 / 255f,
							200 / 255f, 200 / 255f, 1f)));
			foodCover.setSize(250, 200);
			tbTitle = new Table();
			tbTitle.align(Align.left);

			LabelStyle style = new LabelStyle(
					Assets.instance.fontFactory.getLight20(), new Color(
							45 / 255f, 45 / 255f, 45 / 255f, 1f));

			Label lbReciepe = new Label(titleReciepe, style);
			lbReciepe.setWrap(true);
			lbReciepe.setWidth(180);
			lbReciepe.setAlignment(Align.center, Align.left);

			avatar = new MarkImage(Assets.instance.uiM.bg_ninepatch);
			avatar.setSize(40, 40);
			ImageDownloader.getInstance().download(
					AppPreference.instance._avatar.replaceAll("100", "40"),
					avatar);
			Label lbUserName = new Label(userName, style_small);
			lbUserName.setAlignment(Align.center, Align.left);

			tbTitle.add(lbReciepe).width(lbReciepe.getWidth())
					.align(Align.left).pad(10).colspan(4);
			tbTitle.row();
			tbTitle.add(avatar).width(avatar.getWidth())
					.height(avatar.getHeight()).padLeft(10);
			tbTitle.add(lbUserName).padLeft(5);

			container.add(foodCover).expand().fill().pad(10).center();
			container.add(tbTitle).align(Align.left);

		}

		public Table getRoot() {
			return container;
		}

	}
}
