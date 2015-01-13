package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.GalleryViewHorizontal;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.utils.Log;
import com.aia.hichef.utils.UserInfo;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class FoodDetailView extends View {
	LabelStyle		style_normal;
	LabelStyle		style_big;
	public float	TOPBAR_HEIGHT	= Constants.HEIGHT_ACTIONBAR;
	public Table	table;
	public ItemFood	food;
	Table			topBar, tbDescription, tbDetail;
	Image			imgFood			= new Image();
	Image			back;
	Image			shareButton;
	String			text			= "";
	public int		id				= -1;
	Label			title, labelDescription;

	JsonValue		content;
	String			responseData	= "";
	boolean			isLoadData		= false;
	public String	parentView		= "";
	Array<String>	contents		= new Array<String>();
	Array<String>	urls			= new Array<String>();

	public FoodDetailView(int id, String parentView) {
		super();
		this.id = id;
		this.parentView = parentView;
	}

	void requestFood(int id) {
		Request.getInstance().getFoodDetail(id, new GetFoodDetailListener());
	}

	public FoodDetailView() {
		super();
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public FoodDetailView buildInfo(String name, Rectangle bound) {
		this.name = name;
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);
		table = new Table();
		table.setBounds(bound.x, bound.y, bound.width, bound.height
				- TOPBAR_HEIGHT);

		topBar = new Table();
		topBar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch,
				new Color(200 / 255f, 0, 0, 1))));
		topBar.setSize(getWidth(), TOPBAR_HEIGHT);
		topBar.setPosition(0, getHeight() - TOPBAR_HEIGHT);
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
		return this;
	}

	public FoodDetailView buildComponent() {

		imgFood = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch));
		imgFood.setSize(getWidth(), 300);
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(255 / 255f,
						255 / 255f, 255 / 255f, 1))));
		ScrollPane scrollPane = new ScrollPane(table);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setSmoothScrolling(true);
		scrollPane.setFlingTime(.1f);
		top();
		add(scrollPane).padTop(TOPBAR_HEIGHT);
		table.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(255 / 255f,
						255 / 255f, 255 / 255f, 1))));
		table.top();
		table.clearListeners();
		style_normal = new LabelStyle(
				Assets.instance.fontFactory.getRegular15(), new Color(
						77 / 255f, 77 / 255f, 77 / 255f, 1));
		style_big = new LabelStyle(Assets.instance.fontFactory.getRegular20(),
				new Color(77 / 255f, 77 / 255f, 77 / 255f, 1));

		food = new ItemFood(imgFood, "", "");

		tbDescription = new Table();
		float height = style_normal.font.getWrappedBounds(text,
				table.getWidth() - 40).height + 40;
		tbDescription.setSize(getWidth(), height);

		Table tbSelect = new Table();
		tbSelect.setSize(getWidth(), 40);

		tbDetail = new Table();
		tbDetail.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(255 / 255f,
						255 / 255f, 255 / 255f, 1))));
		tbDetail.setWidth(getWidth());

		// ============ Add Element ==============
		table.add(food);
		table.row();
		table.add(tbDescription).expand().fillX().expandY();
		table.row();
		table.add(tbSelect).expand().fillX().height(tbSelect.getHeight());
		table.row();
		table.add(tbDetail).fillX();
		table.row();

		buildTopBar(topBar);
		buildtbSelect(tbSelect);
		// new TabSelect(tbSelect);
		buildtbDes(tbDescription);
		addActor(topBar);
		topBar.toFront();
		setVisible(false);
		return this;
	}

	void buildTopBar(Table topbar) {
		topbar.left().align(Align.center);
		topbar.defaults().width(60).height(60);
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
		topbar.add(back).width(30).height(30).padLeft(20);

		LabelStyle style_normal = new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						255 / 255f, 255 / 255f, 255 / 255f, 1));
		title = new Label("", style_normal);
		title.setAlignment(Align.center);
		topbar.add(title).expandX().fillX().padRight(40);
		shareButton = new Image(Assets.instance.uiN.share);
		shareButton.setSize(10, 10);
		topBar.add(shareButton).width(40).height(40).padRight(20);
		shareButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_viewController.getGameParent().facebookConnector.share(url,
						des, foodName);
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

	GalleryViewHorizontal	titleView, homeView, subViewHorizontal;
	// GalleryViewVertical subViewVertical;
	Array<String>			tabs			= new Array<String>();
	Array<Label>			labels			= new Array<Label>();
	Color					colorSelect		= Color.RED;
	Color					colorUnselect	= Color.BLACK;

	void buildtbSelect(Table table) {

		LabelStyle style_normal = new LabelStyle(
				Assets.instance.fontFactory.getRegular20(), new Color(
						255 / 255f, 255 / 255f, 255 / 255f, 1));
		Array<String> tabs = new Array<String>();
		tabs.add("Nguyên Liệu");
		tabs.add("Cách Làm");

		titleView = new GalleryViewHorizontal(table, 2);
		titleView.pages.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		for (int i = 0; i < tabs.size; i++) {
			final int index = i;
			Table element = titleView
					.newPage(
							new NinePatchDrawable(new NinePatch(
									Assets.instance.uiM.bg_ninepatch,
									new Color(241 / 255f, 241 / 255f,
											241 / 255f, 0.85f)))).pad(20);
			Label label = new Label(tabs.get(i), style_normal);
			label.setAlignment(Align.center);
			label.setSize(element.getWidth(), element.getHeight());
			element.setTouchable(Touchable.enabled);
			element.addListener(new ActorGestureListener() {
				@Override
				public void tap(InputEvent event, float x, float y, int count,
						int button) {
					onTitleClick(index);
					super.tap(event, x, y, count, button);
				}

			});
			element.center().add(label).expand().fill();
			labels.add(label);
		}
		onHighlightSelect(0);

	}

	public void onHighlightSelect(int index) {
		for (int i = 0; i < labels.size; i++) {
			if (i == index)
				labels.get(i).setColor(colorSelect);
			else
				labels.get(i).setColor(colorUnselect);
		}
	}

	void onTitleClick(int index) {
		onHighlightSelect(index);
		if (index == 1) {
			FoodHowTo foodHowTo = new FoodHowTo(title.getText().toString(),
					contents, urls, url, des);
			foodHowTo.build(getStage(), getViewController());
			foodHowTo.buildInfo(
					getName(),
					StringSystem.VIEW_FOOD_HOWTO,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(foodHowTo);
			// _viewController.focusView(StringSystem.VIEW_FOOD_HOWTO);
			foodHowTo.show();
		}
	}

	void onContentClick(int index) {
		titleView.pages.focusOnPage(index);
		onHighlightSelect(index);
	}

	void buildtbDes(Table table) {
		LabelStyle style = new LabelStyle(
				Assets.instance.fontFactory.getLight20(), Color.BLACK);
		table.top().left();
		labelDescription = new Label(text, style);
		labelDescription.setBounds(20, 0, table.getWidth() - 40,
				table.getHeight());
		labelDescription.setWrap(true);
		labelDescription.setAlignment(Align.left);
		table.add(labelDescription).expand().fillX().fillY().padTop(20)
				.padBottom(20).padLeft(20).padRight(20);
	}

	public void buildPage1(Table table, Array<String> elementNames,
			Array<String> elementValues) {
		table.top().left();
		LabelStyle style = new LabelStyle(
				Assets.instance.fontFactory.getRegular20(), new Color(0f, 0f,
						0f, 1));
		LabelStyle styleValue = new LabelStyle(
				Assets.instance.fontFactory.getRegular20(), new Color(
						100f / 255f, 100 / 255f, 100 / 255f, 1));

		for (int i = 0; i < elementNames.size; i++) {
			Label name = new Label(elementNames.get(i), style);
			name.setAlignment(Align.left);
			name.setSize(style.font.getBounds(name.getText()).width + 20,
					style.font.getBounds(name.getText()).height + 5);

			Label value = new Label(elementValues.get(i), styleValue);
			value.setAlignment(Align.left);
			value.setSize(style.font.getBounds(value.getText()).width + 20,
					style.font.getBounds(value.getText()).height + 5);

			Image splitL = new Image(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(100 / 255f,
							100 / 255f, 100 / 255f, .4f))));
			Image splitR = new Image(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(100 / 255f,
							100 / 255f, 100 / 255f, .4f))));

			float height = style.font.getBounds(name.getText()).height + 20;
			table.add(name).expand().width(table.getWidth() / 2).height(height)
					.padBottom(5).padLeft(50);
			table.add(value).expand().width(table.getWidth() / 2)
					.height(height).padBottom(5).padLeft(50);
			table.row();
			if (i != elementNames.size - 1) {
				table.add(splitL).expand().fill().fillX().height(2).padLeft(20);
				table.add(splitR).expand().fill().fillX().height(2)
						.padRight(20);
				table.row();
			} else {
				table.row().height(40);
			}
		}
	}

	public void buildPage2(Table table, Array<String> contents,
			Array<String> urls) {
		table.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return true;
			};
		});
		GalleryViewHorizontal view = new GalleryViewHorizontal(table, 1);
		for (int i = 0; i < contents.size; i++) {
			Table element = view.newPage(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(230 / 255f,
							230 / 255f, 230 / 255f, .6f))));
			FoodirectionView subView = new FoodirectionView(element,
					ContentType.NO_IMAGE);
			subView.labelContent.setText(contents.get(i));
			if (i == contents.size - 1) {
				subView.stepLabel.setText("Hoàn Thành");
			} else
				subView.stepLabel.setText("Bước " + (i + 1));
		}

	}

	@Override
	public void update() {
		super.update();
		processDataResponse();
	}

	public String	url, des, foodName;

	void processDataResponse() {
		if (isLoadData) {
			String url = content.getString(ExtParamsKey.URL_IMAGE);
			this.url = url;

			ImageDownloader.getInstance().download(url, imgFood);
			int like = content.getInt(ExtParamsKey.LIKES);
			boolean isLike = content.getBoolean(ExtParamsKey.IS_LIKE);
			if (isLike) {
				food.icon_heart.setColor(new Color(220 / 255f, 0, 0, 1f));
			}
			food.stt_heart.setText("" + like);
			String fullDescription = content.getString(ExtParamsKey.FULL_DES);
			des = fullDescription;
			labelDescription.setText(fullDescription);
			String title = content.getString(ExtParamsKey.TITLE);
			foodName = title;
			// food.title.setText(title);
			this.title.setText(title);

			Array<String> names = new Array<String>();
			Array<String> values = new Array<String>();

			JsonValue arrayMaterial = content.get(ExtParamsKey.FOOD_INGREDIENT);
			
			

			for (int i = 0; i < arrayMaterial.size; i++) {
				JsonValue vl = arrayMaterial.get(i);

				String name = vl.getString(ExtParamsKey.MATERIAL_NAME);
				String value = vl.getString(ExtParamsKey.CONTENT);
				names.add(name);
				values.add(value);
			}

			buildPage1(tbDetail, names, values);
			int id = content.getInt(ExtParamsKey.ID);
			this.id = id;

			JsonValue arrayDirection = content.get(ExtParamsKey.FOOD_DIRECTION);
			for (int i = 0; i < arrayDirection.size; i++) {
				JsonValue vl = arrayDirection.get(i);
				String content = vl.getString(ExtParamsKey.CONTENT);
				String urlImg = vl.getString(ExtParamsKey.URL_IMAGE);
				contents.add(content);
				urls.add(urlImg);
			}
			layout();
			isLoadData = false;
			FullLoading.instance.hide();
		}
	}

	@Override
	public void show() {
		// _viewController.getView(StringSystem.VIEW_HOME).show();
		Action action = Actions.sequence(Actions.moveBy(Constants.WIDTH_SCREEN,
				0), Actions.run(new Runnable() {

			@Override
			public void run() {
				setVisible(true);
				setViewState(ViewState.SHOW);
			}
		}), Actions.moveBy(-Constants.WIDTH_SCREEN, 0, .2f,
				Interpolation.linear), Actions.run(new Runnable() {

			@Override
			public void run() {
				requestFood(id);
			}
		}));
		addAction(action);
		super.show();
	}

	@Override
	public void hide() {
		Action action = Actions.sequence(Actions.moveBy(Constants.WIDTH_SCREEN,
				0, .4f, Interpolation.sineOut), Actions.run(new Runnable() {

			@Override
			public void run() {
				_viewController.removeView(getName());
				setVisible(false);
				if (parentView.equalsIgnoreCase(StringSystem.VIEW_HOME)) {
					((Actor) _viewController.getView(parentView))
							.setTouchable(Touchable.enabled);
					TraceView.instance.reset();
					System.out.println("Back to HomeView");
				}
			}
		}));
		addAction(action);
		super.hide();
	}

	@Override
	public void setName(String name) {

		super.setName(name);
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	public void onBack() {
		back();
	}

	public class ItemFood extends Item {

		Image			bg;
		Label			content;
		Image			subbg;
		Label			stt_heart;
		public Image	icon_heart;

		public ItemFood(Image bg, String title, String content) {
			setWidth(480);
			setHeight(340);
			bg.setPosition(getX(), getY());
			bg.setSize(480, 340);
			subbg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					new Color(241 / 255f, 241 / 255f, 241 / 255f, 0.85f)));
			subbg.setSize(this.getWidth(), this.getHeight() / 6);
			subbg.setPosition(getX(), getY());
			icon_heart = new Image(Assets.instance.uiA.reg_iconHeart);
			icon_heart.setSize(24, 24);
			icon_heart.setColor(Color.GRAY);
			icon_heart.setPosition(subbg.getX() + subbg.getWidth() - 5
					- icon_heart.getWidth(), subbg.getY() + subbg.getHeight()
					/ 2 - icon_heart.getHeight() / 2);

			icon_heart.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (UserInfo.getInstance().userLogged()) {
						if (icon_heart.getColor().equals(Color.GRAY)) {
							Request.getInstance().requestLike(id,
									new LikeFoodListener(icon_heart));
						} else {
							Toast.makeText(getStage(),
									"Bạn đã like món ăn này rồi !",
									Toast.LENGTH_SHORT);
						}
					} else {
						LoginView loginView = new LoginView();
						loginView.build(getStage(), _viewController);
						loginView.buildInfo(
								StringSystem.VIEW_FOOD_DETAIL,
								StringSystem.VIEW_LOGIN,
								new Rectangle(0, 0, Constants.WIDTH_SCREEN,
										Constants.HEIGHT_SCREEN))
								.buildComponent();
						_viewController.addView(loginView);
						loginView.show();

					}
				}

			});

			stt_heart = new Label("0", style_normal);
			stt_heart.setPosition(icon_heart.getX() - 2 - stt_heart.getWidth(),
					icon_heart.getY() + 5);
			this.content = new Label(getSubString(content, stt_heart.getX()
					- subbg.getX() - 40), style_normal);

			addActor(bg);
			addActor(subbg);
			addActor(this.content);
			addActor(icon_heart);
			addActor(stt_heart);
		}

		private String getSubString(String str, float width) {
			String substr = str;
			for (int i = 0; i < str.length(); i++) {
				if (Assets.instance.fontFactory.getRegular12().getBounds(
						str.substring(0, i)).width > width
						- Assets.instance.fontFactory.getRegular12().getBounds(
								"...").width) {
					substr = str.substring(0, i) + "...";
					break;
				}
			}
			return substr;
		}

		public int getId() {
			return id;
		}
	}

	public class FoodirectionView {
		LabelStyle	style;
		LabelStyle	stepStyle;
		Table		content;
		ContentType	type;
		String		text	= "";
		Label		labelContent;
		Label		stepLabel;

		public FoodirectionView(Table content, ContentType contentType) {
			super();
			this.content = content;
			this.type = contentType;
			style = new LabelStyle(Assets.instance.fontFactory.getRegular20(),
					Color.BLACK);
			stepStyle = new LabelStyle(
					Assets.instance.fontFactory.getRegular20(), Color.RED);
			buildNoImage();
		}

		public void buildImage() {

		}

		public void buildNoImage() {
			Table tbContent = new Table();
			tbContent.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch)));
			tbContent
					.setSize(content.getWidth() - 20, content.getHeight() - 20);
			tbContent.top().left();

			stepLabel = new Label("Bước", stepStyle);
			stepLabel.setAlignment(Align.left);

			labelContent = new Label(text, style);
			labelContent.setAlignment(Align.left);
			labelContent.setWrap(true);
			labelContent.setSize(tbContent.getWidth() - 20,
					tbContent.getHeight());
			labelContent.setBounds(10, 10, tbContent.getWidth() - 20,
					tbContent.getHeight() - 20);

			tbContent.add(labelContent).expand().fill().padTop(20).padLeft(20);
			tbContent.addActor(stepLabel);
			content.add(tbContent).top().left().expand().fill().pad(10);
		}
	}

	private class GetFoodDetailListener implements HttpResponseListener {

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			responseData = httpResponse.getResultAsString();
			JsonValue value = (new JsonReader()).parse(responseData);
			content = value.get(ExtParamsKey.CONTENT);
			System.out.println(content);
			isLoadData = true;
		}

		@Override
		public void failed(Throwable t) {

		}

		@Override
		public void cancelled() {

		}

	}

	private class LikeFoodListener implements HttpResponseListener {
		Image	img;

		public LikeFoodListener(Image img) {
			super();
			this.img = img;
		}

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			responseData = httpResponse.getResultAsString();
			JsonValue value = (new JsonReader()).parse(responseData);
			System.out.println(value);
			String result = value.getString(ExtParamsKey.RESULT);
			if (result.equalsIgnoreCase("failed")) {
				img.setColor(Color.GRAY);
			} else {
				img.setColor(new Color(220 / 255f, 0, 0, 1f));
			}
		}

		@Override
		public void failed(Throwable t) {

		}

		@Override
		public void cancelled() {

		}

	}

	@Override
	public void back() {
		hide();
	}

	enum ContentType {
		IMAGE, NO_IMAGE;
	}

}
