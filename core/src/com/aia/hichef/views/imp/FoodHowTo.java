package com.aia.hichef.views.imp;

import utils.download.DownloadManager;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.GalleryViewHorizontal;
import com.aia.hichef.components.PointView;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.FocusListener;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class FoodHowTo extends View {

	public Direct			direct		= Direct.MIDDLE;
	public Table			topBar;
	public Table			content;
	float					BAR_HEIGHT	= 60;
	Color					color_topbar;
	Color					color_content;
	Image					back;
	Label					title;
	Image					shareButton;
	LabelStyle				style;
	String					parentView;

	GalleryViewHorizontal	viewHorizontal;
	int						time		= 0;
	int						index		= 0;
	PointView				pointView;

	Array<String>			contents;
	Array<String>			urls;
	String					parentUrl, decription;
	String					foodName;

	public FoodHowTo(String foodName, Array<String> contents,
			Array<String> urls, String parentUrl, String decription) {
		super();
		this.parentUrl = parentUrl;
		this.decription = decription;
		this.contents = contents;
		this.urls = urls;
		this.foodName = foodName;
		style = new LabelStyle(Assets.instance.fontFactory.getRegular20(),
				Color.BLACK);
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public FoodHowTo buildInfo(String parentView, String name, Rectangle bound) {
		this.parentView = parentView;
		super.setName(name);
		this.name = name;
		super.setBounds(bound.x, bound.y, bound.width, bound.height);

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

		addActor(content);
		addActor(topBar);

		return this;
	}

	public FoodHowTo buildComponent() {
		buildTopBar();
		hide();
		viewHorizontal = new GalleryViewHorizontal(content, 1);
		viewHorizontal.setScrollOver(false, false);
		int size = contents.size;
		LabelStyle stepStyle = new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), Color.WHITE);
		for (int i = 0; i < size; i++) {
			Table element = viewHorizontal.newPage(new NinePatchDrawable(
					new NinePatch(Assets.instance.uiM.bg_ninepatch, new Color(
							230 / 255f, 230 / 255f, 230 / 255f, 1f))));
			element.setHeight(content.getHeight());
			element.top();
			element.align(Align.top);
			Image bg = new Image(new NinePatch(
					Assets.instance.uiM.bg_icon_caterogy, 5, 5, 5, 5));
			bg.setBounds(10, 50, element.getWidth() - 20,
					element.getHeight() - 100);
			element.addActor(bg);
			buildElement(element, contents.get(i), urls.get(i));
			Image step = new Image(new NinePatch(Assets.instance.uiA.reg_step));
			step.setPosition(bg.getX() + bg.getWidth() / 2 - step.getWidth()
					/ 2, bg.getY() + bg.getHeight() - step.getHeight() / 2 + 6);
			element.addActor(step);
			String text = (i == (size - 1)) ? "Hoàn Thành"
					: ("Bước " + (i + 1));
			Label lbStep = new Label(text, stepStyle);
			lbStep.setPosition(step.getX() + step.getWidth() / 2
					- stepStyle.font.getBounds(text).width / 2, step.getY()
					+ step.getHeight() / 2
					- stepStyle.font.getBounds(text).height / 2 - 6);
			element.addActor(lbStep);
		}
		pointView = new PointView(content, contents.size, Direct.LEFT,
				new FocusPage());
		pointView.build(Color.BLACK, Color.BLACK);
		pointView.setHeight(10);
		return this;
	}

	public void buildElement(Table table, String str, String url) {
		table.top().left();
		Table subtable = new Table();
		subtable.setSize(table.getWidth() - 40, 400);
		subtable.setPosition(table.getWidth() / 2 - subtable.getWidth() / 2,
				table.getHeight() - subtable.getHeight() - 60);
		subtable.top().left();

		Table data = new Table();
		data.setSize(table.getWidth() - 40,
				table.getHeight() - subtable.getHeight() - 100);
		data.setPosition(table.getWidth() / 2 - data.getWidth() / 2, 60);
		data.top();

		table.addActor(subtable);
		table.addActor(data);

		Image food = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(100 / 255f, 100 / 255f, 100 / 255f, 1f)));
		food.setSize(subtable.getWidth() - 40, subtable.getHeight() - 40);
		subtable.add(food).top().width(food.getWidth()).pad(20)
				.height(food.getHeight());
		getDownLoader().download(url, food);
		buildWordTable(data, str);
	}

	private void buildWordTable(Table tbdata, String data) {
		Table tempt = new Table();
		tempt.top();
		ScrollPane scroll = new ScrollPane(tempt);
		Label labelContent = new Label(data, style);
		labelContent.setAlignment(Align.top, Align.left);
		labelContent.setWrap(true);
		tempt.add(labelContent).expand().fill().top();
		tbdata.add(scroll).expand().fill().top().padTop(20);

		if (style.font.getWrappedBounds(data, tbdata.getWidth() - 20).height < tbdata
				.getHeight()) {
			tbdata.setTouchable(Touchable.disabled);
		}

		tbdata.addListener(new ActorGestureListener() {
			public Vector2	touchPoint	= new Vector2();

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchPoint.set(x, y);
				event.stop();
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (Math.abs(deltaX) < Math.abs(deltaY)) {
					event.stop();
				} else {
					super.pan(event, x, y, deltaX, deltaY);
				}
			}
		});

	}

	@Override
	public void update() {
		super.update();
		pointView.update();
		pointView.setIndex(viewHorizontal.pages.index);

	}

	@Override
	public void show() {
		clearActions();
		toFront();
		Action action = Actions.sequence(
				Actions.moveTo(Constants.WIDTH_SCREEN + 2, 0),
				Actions.visible(true),
				Actions.moveTo(0, 0, .2f, Interpolation.linear));
		addAction(action);
		super.show();
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
		setTouchable(Touchable.disabled);
		clearActions();
		Action action = Actions.sequence(Actions.moveTo(
				Constants.WIDTH_SCREEN + 2, getY(), .2f, Interpolation.linear),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						setVisible(false);
						_viewController.removeView(getName());
						TraceView.instance.removeView(getName());
					}
				}));
		addAction(action);
	}

	@Override
	public void setViewState(ViewState state) {

		super.setViewState(state);
	}

	@Override
	public void destroyComponent() {

		super.destroyComponent();
	}

	void onBack() {
		back();
	}

	@Override
	public void back() {
		DownloadManager.getInstance().removeDownLoader(getName());
		hide();
	}

	void buildTopBar() {
		topBar.left().align(Align.center);
		topBar.defaults().width(BAR_HEIGHT).height(BAR_HEIGHT);
		topBar.setTouchable(Touchable.enabled);
		topBar.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

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
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 1f);
				onBack();
			}

		});
		back = new Image(Assets.instance.uiA.reg_back);
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
		topBar.add(back).width(30).height(30).padLeft(20);
		// topBar.debug();
		LabelStyle style_normal = new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						255 / 255f, 255 / 255f, 255 / 255f, 1));
		title = new Label(foodName, style_normal);
		title.setAlignment(Align.center);
		topBar.add(title).expandX().fillX().padRight(40);

		shareButton = new Image(Assets.instance.uiN.share);
		shareButton.setSize(10, 10);
		topBar.add(shareButton).width(40).height(40).padRight(20);
		shareButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_viewController.getGameParent().facebookConnector.share(
						parentUrl, decription, foodName);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				shareButton.setColor(Color.GRAY);
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				shareButton.setColor(Color.WHITE);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	public class FocusPage implements FocusListener {

		@Override
		public void touchIndex(int index) {
			viewHorizontal.pages.focusOnPage(index);
		}

	}

}
