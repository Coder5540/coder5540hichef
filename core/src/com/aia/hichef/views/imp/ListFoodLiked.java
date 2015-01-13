package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.Constant;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.ui.ListFood;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.utils.JsonValue;

public class ListFoodLiked extends View {
	public Direct	direct		= Direct.MIDDLE;
	public Table	topBar;
	public Table	content;
	public Table	tableScroll;
	float			BAR_HEIGHT	= 60;
	Color			color_topbar;
	Color			color_content;
	Image			back;
	Label			title;
	LabelStyle		style;
	String			parentView;
	JsonValue		contentValue;

	LabelStyle		style_normal;
	LabelStyle		style_bold;
	BitmapFont		font_normal;
	BitmapFont		font_bold;
	ListFood		listFood;

	public ListFoodLiked(JsonValue contentValue) {
		super();
		this.contentValue = contentValue;
		style = new LabelStyle(Assets.instance.fontFactory.getRegular20(),
				Color.BLACK);
		font_bold = Assets.instance.fontFactory.getBold20();
		font_normal = Assets.instance.fontFactory.getRegular20();
		style_normal = new LabelStyle(font_normal, new Color(77 / 255f,
				77 / 255f, 77 / 255f, 1));
		style_bold = new LabelStyle(font_bold, new Color(77 / 255f, 77 / 255f,
				77 / 255f, 1));

	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public ListFoodLiked buildInfo(String parentView, String name,
			Rectangle bound) {
		this.parentView = parentView;
		super.setName(name);
		this.name = name;
		super.setBounds(bound.x, bound.y, bound.width, bound.height);

		color_topbar = new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f);
		color_content = new Color(255 / 255f, 255 / 255f, 255 / 255f, 1f);

		content = new Table();
		content.debug();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_content)));
		content.setSize(bound.getWidth(), bound.height - BAR_HEIGHT);
		content.setPosition(0, 0);

		tableScroll = new Table();
		tableScroll.setSize(content.getWidth(), content.getHeight());
		ScrollPane scroll = new ScrollPane(tableScroll);
		content.add(scroll).expand().fill();

		topBar = new Table();
		topBar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_topbar)));
		topBar.setSize(bound.getWidth(), BAR_HEIGHT);
		topBar.setPosition(0, bound.height - BAR_HEIGHT);

		addActor(content);
		addActor(topBar);
		content.setVisible(false);
		topBar.setVisible(false);

		return this;
	}

	public ListFoodLiked buildComponent() {
		buildTopBar();
		buildContent(contentValue);
		return this;
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
		LabelStyle style_normal = new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						255 / 255f, 255 / 255f, 255 / 255f, 1));
		title = new Label("Các món đã thích", style_normal);
		title.setAlignment(Align.center);
		topBar.add(title).expandX().fillX().padRight(40);

	}

	public void buildContent(JsonValue activities) {
		float width = Constants.WIDTH_SCREEN;
		float height = 280;
		content.defaults().width(width).height(height).center();

		for (int i = 0; i < activities.size; i++) {
			JsonValue activity = activities.get(i);
			int activityID = activity.getInt(ExtParamsKey.ACTIVITY_ID);

			if (activityID == Constant.ACTIVITY_LIKE) {
				JsonValue food = activity.get(ExtParamsKey.CONTENT);
				System.out.println("Food Info : " + food);
				final int id = food.getInt(ExtParamsKey.ID);
				final String title = food.getString(ExtParamsKey.TITLE);
				String short_des = food.getString(ExtParamsKey.SHORT_DES);
				int views = food.getInt(ExtParamsKey.VIEWS);
				int likes = food.getInt(ExtParamsKey.LIKES);
				String url = food.getString(ExtParamsKey.URL_IMAGE);
				Image img = new Image(Assets.instance.uiM.bg_ninepatch);
				img.setDrawable(new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch)));
				img.setSize(width, height);
				ImageDownloader.getInstance().download(url, img);
				ItemFood itemNewFood = new ItemFood(id, img, title, short_des,
						likes, views, width, height);
				itemNewFood.setListener(new ItemListener() {
					@Override
					public void onItemClick() {
						onDetail(id);
					}
				});
				tableScroll.add(itemNewFood).expand();
				tableScroll.row();
			}
		}
	}

	@Override
	public void show() {
		clearActions();
		toFront();
		Action action = Actions.sequence(
				Actions.moveTo(Constants.WIDTH_SCREEN + 2, 0),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						topBar.setVisible(true);
						content.setVisible(true);
						setVisible(true);
					}
				}), Actions.moveTo(0, 0, .2f, Interpolation.linear));
		addAction(action);
		super.show();
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
		clearActions();
		Action action = Actions.sequence(Actions.moveTo(
				Constants.WIDTH_SCREEN + 2, getY(), .2f, Interpolation.linear),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						setVisible(false);
						content.setVisible(false);
						topBar.setVisible(false);
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

	void onBack() {
		back();
	}
	@Override
	public void back() {
		hide();
	}

	public class ItemFood extends Item {
		private int	id;
		Label		title;
		Label		content;
		Image		subbg;
		Label		stt_eye;
		Label		stt_heart;
		Image		icon_eye;
		Image		icon_heart;

		public ItemFood(int id, Image bg, String title, String short_des,
				int like, int view, float width, float height) {
			setWidth(width);
			setHeight(height);
			this.id = id;
			bg.setPosition(getX(), getY());
			bg.setSize(width, height);
			subbg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					new Color(241 / 255f, 241 / 255f, 241 / 255f, 0.85f)));
			subbg.setSize(this.getWidth(), this.getHeight() / 4);
			subbg.setPosition(getX(), getY());
			icon_eye = new Image(Assets.instance.uiM.icon_heart_06);
			icon_heart = new Image(Assets.instance.uiM.icon_heart_03);
			icon_heart.setPosition(subbg.getX() + subbg.getWidth() - 5
					- icon_heart.getWidth(), subbg.getY() + subbg.getHeight()
					/ 2 - icon_heart.getHeight() / 2);
			stt_heart = new Label(getStrNumber(like), style_normal);
			stt_heart.setPosition(icon_heart.getX() - 4 - stt_heart.getWidth(),
					icon_heart.getY() + icon_heart.getHeight() / 2
							- font_normal.getBounds(like + "").height);
			icon_eye.setPosition(stt_heart.getX() - 20 - icon_eye.getWidth(),
					subbg.getY() + subbg.getHeight() / 2 - icon_eye.getHeight()
							/ 2);
			stt_eye = new Label(getStrNumber(view), style_normal);
			stt_eye.setPosition(
					icon_eye.getX() - 4 - stt_eye.getWidth(),
					icon_eye.getY() + icon_eye.getHeight() / 2
							- font_normal.getBounds(view + "").height);
			this.title = new Label(getSubString(title,
					stt_eye.getX() - subbg.getX() - 50), style_bold);
			this.title.setPosition(
					subbg.getX() + 5,
					subbg.getY() + subbg.getHeight() - 5
							- this.title.getHeight());
			this.content = new Label(getSubString(short_des, stt_eye.getX()
					- subbg.getX() - 50), style_normal);
			this.content.setPosition(this.title.getX(), subbg.getY() + 5);
			addActor(bg);
			addActor(subbg);
			addActor(this.title);
			addActor(this.content);
			addActor(icon_eye);
			addActor(icon_heart);
			addActor(stt_eye);
			addActor(stt_heart);
		}

		private String getStrNumber(int number) {
			String str = "";
			if (number >= 1000) {
				number *= 10;
				int number2 = number / 1000;
				float number3 = number2 / 10f;
				str = number3 + "k";
			} else {
				str = number + "";
			}
			return str;
		}

		private String getSubString(String str, float width) {
			String substr = str;
			for (int i = 0; i < str.length(); i++) {
				if (font_bold.getBounds(str.substring(0, i)).width > width
						- font_bold.getBounds("...").width) {
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

	public void onDetail(int id) {
		FoodDetailView foodDetailView = new FoodDetailView(id, getName());
		foodDetailView.build(getStage(), _viewController);
		foodDetailView.buildInfo(
				StringSystem.VIEW_FOOD_DETAIL,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN)).buildComponent();
		_viewController.addView(foodDetailView);
		foodDetailView.show();
		_viewController.getView(StringSystem.VIEW_ACTION_BAR).hide();
		((Actor) _viewController.getView(StringSystem.VIEW_FOOD_DETAIL))
				.toFront();

	}
}
