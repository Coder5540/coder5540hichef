package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.MarkImage;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.networks.Constant;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.utils.UserInfo;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
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
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class UserProfile extends View {

	public String		parentView;
	public Table		topBar;
	public Table		content;
	public Table		tbActivity;
	public float		BAR_HEIGHT		= 60;
	public Color		color_topbar;
	public Color		color_content;

	public Image		setting, back;
	MarkImage			avatar;
	public LabelStyle	style, styleElement;

	private ListInfo	listInfo;
	public JsonValue	contentLiked;

	// =================== user variable ==============
	String				userName		= "";
	LabelStyle			style_normal;
	LabelStyle			style_small;
	LabelStyle			style_bold;
	BitmapFont			font_normal;
	BitmapFont			font_bold;
	BitmapFont			font_small;

	// ==================================
	String				responseData	= "";
	boolean				isLoadData		= false;

	public UserProfile() {
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
		userName = AppPreference.instance._title;
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public UserProfile buildInfo(String parentView, String name, Rectangle bound) {
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

	public UserProfile buildComponent() {
		buildTopBar();
		buildContent();
		topBar.setVisible(false);
		content.setVisible(false);
		return this;
	}

	private void buildTopBar() {
		topBar.left().align(Align.center);
		topBar.defaults().width(BAR_HEIGHT).height(BAR_HEIGHT);
		topBar.setTouchable(Touchable.enabled);
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

		topBar.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (x > topBar.getWidth() - 80) {
					setting.setColor(setting.getColor().r,
							setting.getColor().g, setting.getColor().b, 0.4f);
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
				if (setting.getColor().a < 0.5f) {
					setting.setColor(setting.getColor().r,
							setting.getColor().g, setting.getColor().b, 1f);
					onSetting();
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

		setting = new Image(Assets.instance.uiM.icono_settings);
		setting.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				setting.setColor(setting.getColor().r, setting.getColor().g,
						setting.getColor().b, 0.4f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setting.setColor(setting.getColor().r, setting.getColor().g,
						setting.getColor().b, 1f);
				onSetting();
			}
		});

		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(255 / 255f, 255 / 255f, 255 / 255f, 1));
		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(0 / 255f, 0 / 255f, 0 / 255f, 1));
		Label lbName = new Label(" Thông tin người dùng ", style);
		lbName.setAlignment(Align.center);

		topBar.add(back).width(30).height(30).padLeft(20);
		topBar.add(lbName).expandX().fillX();
		topBar.add(setting).width(40).height(40).padRight(20);
	}

	private void buildContent() {

		// ========= Build Scroll For Content ==============
		tbActivity = new Table();
		ScrollPane scrollPane = new ScrollPane(tbActivity);
		scrollPane.setScrollingDisabled(true, false);
		content.add(scrollPane).expand().fillX();

		// ============== User avatar ================
		Table tbAvatar = new Table();
		tbAvatar.setSize(480, 300);
		buildTbAvar(tbAvatar);

		tbActivity.add(tbAvatar).width(tbAvatar.getHeight())
				.height(tbAvatar.getHeight()).center();
		tbActivity.row();

		tbActivity
				.add(getImage(new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f)))
				.expand().fillX().height(10);
		tbActivity.row();

		// ============== Build Scroll List info ================

		Table tbList = new Table();
		tbList.setWidth(content.getWidth());
		listInfo = new ListInfo(tbList);
		listInfo.addElement(StringSystem.para_recipes, 0);
		listInfo.addElement(StringSystem.para_likes, 0);
		listInfo.addElement(StringSystem.para_Comments, 0);

		ScrollPane scrollPane2 = new ScrollPane(tbList);
		Table table = new Table();
		table.setSize(getWidth(), 100);
		table.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		table.add(scrollPane2).expand().fill();

		tbActivity.add(table).fillX().expandY();
		tbActivity.row();

		Label lb = new Label("Hoạt động", style);
		tbActivity.add(lb).height(60).expand().fillX().align(Align.left)
				.padTop(20);
		tbActivity.row();

		tbActivity
				.add(getImage(new Color(240 / 255f, 240 / 255f, 240 / 255f, 1f)))
				.expand().fillX().height(10);
		tbActivity.row();
	}

	private void buildTbAvar(Table tb) {
		avatar = new MarkImage(Assets.instance.uiM.bg_ninepatch);
		avatar.setSize(150, 150);
		tb.add(avatar).expand().width(150).height(150).center().padTop(40);

		tb.row();
		Label label = new Label(AppPreference.instance._title, style);
		label.setAlignment(Align.center);
		tb.add(label).colspan(4).expand().fill().center().padBottom(40);

		if (AppPreference.instance._login) {
			ImageDownloader.getInstance().download(
					AppPreference.instance._avatar.replaceAll("100", "200"),
					avatar);
		}
	}

	private Image getImage(Color color) {
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
		if (isLoadData) {
			JsonValue value = (new JsonReader()).parse(responseData);
			System.out.println(value);

			// ================= getData =====================
			int nComment = value.getInt(ExtParamsKey.PROFILE_COMMENTS);
			int nLike = value.getInt(ExtParamsKey.PROFILE_LIKE);
			int nRecipe = value.getInt(ExtParamsKey.PROFILE_MY_RECIPES);
			listInfo.getElement(StringSystem.para_Comments).setNumber(nComment);
			listInfo.getElement(StringSystem.para_likes).setNumber(nLike);
			listInfo.getElement(StringSystem.para_recipes).setNumber(nRecipe);

			// ================ List Activities =======================
			JsonValue activities = value.get(ExtParamsKey.PROFILE_ACTIVITY);
			contentLiked = activities;
			for (int i = 0; i < activities.size; i++) {
				// detail for activity
				JsonValue activity = activities.get(i);

				int activityID = activity.getInt(ExtParamsKey.ACTIVITY_ID);
				final int foodId = activity.getInt(ExtParamsKey.FOOD_ID);
				Long time = activity.getLong(ExtParamsKey.TIME);
				if (activityID == Constant.ACTIVITY_LIKE) {
					// thong tin chi tiet mon an
					JsonValue content = activity.get(ExtParamsKey.CONTENT);
					String foodName = content.getString(ExtParamsKey.TITLE);
					String shorDescription = content
							.getString(ExtParamsKey.SHORT_DES);
					String urlImage = content.getString(ExtParamsKey.URL_IMAGE);
					int like = content.getInt(ExtParamsKey.LIKES);
					tbActivity
							.add(new ActivityFeedLike(urlImage, foodId,
									foodName, shorDescription, like, ""
											+ userName + " đã thích món "
											+ foodName, getTime(time))
									.getRoot()).expand().width(440).pad(20);
					tbActivity.row();
				}

				if (activityID == Constant.ACTIVITY_LOGIN) {
					tbActivity
							.add(new ActivityLogin("" + userName
									+ " Đã đăng nhập vào hệ thống",
									getTime(time)).getRoot()).expand()
							.width(440).pad(20);
					tbActivity.row();
				}

			}

			isLoadData = false;
		}
	}

	public void onTitleClick(String title) {
		if (title.equalsIgnoreCase(StringSystem.para_likes)) {
			ListFoodLiked listFoodLiked = new ListFoodLiked(contentLiked);
			listFoodLiked.build(getStage(), getViewController());
			listFoodLiked.buildInfo(
					getName(),
					StringSystem.VIEW_FOOD_LIKED,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(listFoodLiked);
			listFoodLiked.show();
		}

		if (title.equalsIgnoreCase(StringSystem.para_recipes)) {
			UserReciepeView userReciepeView = new UserReciepeView();
			userReciepeView.build(getStage(), getViewController());
			userReciepeView.buildInfo(
					getName(),
					StringSystem.VIEW_USER_RECIEPE,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(userReciepeView);
			userReciepeView.show();
		}
	}

	private String getTime(long time) {
		long distance = System.currentTimeMillis() - time;
		int hour = (int) (distance / 3600000);
		if (hour > 24) {
			int day = hour / 24;

			if (day > 7) {
				int week = day / 7;
				if (day > 30) {
					int month = day / 30;
					return month + " m";
				}
				return week + " w";
			}
			return day + " d";
		}

		if (hour == 0)
			return distance / 60000 + " min";

		return hour + " h";
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

						IViews view2 = _viewController
								.getView(StringSystem.VIEW_LOGIN);
						if (view2 != null) {
							_viewController.removeView(StringSystem.VIEW_LOGIN);
						}
						requestGetProfile();
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

	public class ListInfo {
		Table			content;
		Array<Element>	listElement;

		public ListInfo(Table content) {
			super();
			this.content = content;
			listElement = new Array<UserProfile.ListInfo.Element>();
			config();
		}

		public Element getElement(String name) {
			for (Element element : listElement) {
				if (element.lbtitle.getText().toString().equalsIgnoreCase(name)) {
					return element;
				}
			}
			return null;
		}

		private void config() {
			content.defaults().height(100);
		}

		public void addElement(String title, int number) {
			final Element element = new Element();
			element.build(title, number);
			listElement.add(element);
			element.setTouchable(Touchable.enabled);

			element.addListener(new ActorGestureListener() {

				Vector2	touchPoint	= new Vector2();

				@Override
				public void touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					super.touchDown(event, x, y, pointer, button);
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (touchPoint.epsilonEquals(x, y, 10)) {
						element.addAction(Actions.sequence(
								Actions.alpha(0.5f, .1f), Actions.alpha(1f),
								Actions.run(new Runnable() {

									@Override
									public void run() {
										System.out.println("touch "
												+ element.title);
										onTitleClick(element.title);
									}
								})));

					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void pan(InputEvent event, float x, float y,
						float deltaX, float deltaY) {
					if (!touchPoint.epsilonEquals(x, y, 10)) {
						touchPoint.set(0, 0);
						event.cancel();
					}
					super.pan(event, x, y, deltaX, deltaY);
				}

			});
			content.add(element).width(160).height(60).center();
			// if (listElement.size % 3 == 0)
			// content.row();
		}

		public class Element extends Table {
			Label	lbtitle, lbNumber;
			String	title	= "name";
			int		number	= 0;

			public Element() {
				super();

				lbNumber = new Label("" + number, style);
				lbtitle = new Label(title, style);
				lbNumber.setWrap(true);
				lbtitle.setWrap(true);
				lbNumber.setAlignment(Align.center, Align.center);
				lbtitle.setAlignment(Align.center, Align.center);
				add(lbNumber).expand().fill().center().padTop(20);
				row();
				add(lbtitle).expand().fill().center();
			}

			public Element(String title, int number) {
				super();
				this.title = title;
				this.number = number;
				lbNumber = new Label("" + number, style);

				lbtitle = new Label(title, style);
				lbNumber.setWrap(true);
				lbtitle.setWrap(true);
				lbNumber.setAlignment(Align.center, Align.center);
				lbtitle.setAlignment(Align.center, Align.center);
				add(lbNumber).expand().fill().center().padTop(20);
				row();
				add(lbtitle).expand().fill().center();
				updateColor();
			}

			public void build(String title, int number) {
				this.title = title;
				this.number = number;
				lbtitle.setText(title);
				lbNumber.setText("" + number);
				updateColor();
			}

			private void updateColor() {
				if (number == 0)
					lbNumber.setColor(lbNumber.getColor().r,
							lbNumber.getColor().g, lbNumber.getColor().b, .5f);
				else
					lbNumber.setColor(lbNumber.getColor().r,
							lbNumber.getColor().g, lbNumber.getColor().b, 1f);
			}

			public void setNumber(int number) {
				this.number = number;
				lbNumber.setText("" + number);
				updateColor();
			}

		}
	}

	public class ActivityFeedLike {
		public Table	root, tbAva, tbInfo, tbContent, tbTime, tbFood;
		public Image	iconClock;
		Label			lbInfo, lbTime;
		final int		id;

		public ActivityFeedLike(String urlImage, int foodId, String foodName,
				String shortDescription, int numberLike, String content,
				String time) {
			super();
			root = new Table();
			root.setSize(400, 300);
			id = foodId;
			root.setTouchable(Touchable.enabled);
			root.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					event.stop();
					root.addAction(Actions.sequence(Actions.alpha(0.4f),
							Actions.alpha(1f, .1f), Actions.run(new Runnable() {
								@Override
								public void run() {
									FoodDetailView foodDetailView = new FoodDetailView(
											id, getName());
									foodDetailView.build(getStage(),
											_viewController);
									foodDetailView.buildInfo(
											StringSystem.VIEW_FOOD_DETAIL,
											new Rectangle(0, 0,
													Constants.WIDTH_SCREEN,
													Constants.HEIGHT_SCREEN))
											.buildComponent();
									_viewController.addView(foodDetailView);
									foodDetailView.show();
								}
							})));
					super.clicked(event, x, y);
				}
			});
			buildTime(time);
			buildInfor(content);
			buildAvatar();
			config();
			buildFood(urlImage, id, foodName, shortDescription, numberLike);

		}

		public void config() {
			root.add(tbAva).expandY().width(60).height(60);
			root.add(tbInfo).expand().fill();
			root.add(tbTime).expandY().width(100);
			root.row();
		}

		public void buildTime(String time) {
			tbTime = new Table();
			Image image = new Image(Assets.instance.uiA.reg_clock_icon);
			image.setSize(20, 20);
			image.setColor(Color.GRAY);
			tbTime.add(image).expand().width(20).height(20).right().padRight(5);

			lbTime = new Label(time, style_small);
			lbTime.setAlignment(Align.center, Align.left);
			lbTime.setWrap(true);
			tbTime.add(lbTime).expand().fill();
		}

		public void buildAvatar() {
			tbAva = new Table();
			MarkImage image = new MarkImage(Assets.instance.uiM.bg_ninepatch);
			image.setSize(100, 100);
			tbAva.add(image).expand().fillY();
			if (AppPreference.instance._login) {
				ImageDownloader.getInstance().download(
						AppPreference.instance._avatar, image);
			}
		}

		public void buildInfor(String data) {
			tbInfo = new Table();
			lbInfo = new Label(data, style_normal);
			lbInfo.setAlignment(Align.center, Align.left);
			lbInfo.setWrap(true);
			tbInfo.add(lbInfo).expand().fill().padLeft(5);
		}

		public void buildFood(String urlImage, int Id, String foodname,
				String shortDescription, int numberLike) {
			tbFood = new Table();
			tbFood.setSize(root.getWidth(), 300);
			Image imgFood = new Image(new NinePatch(
					Assets.instance.uiM.bg_ninepatch));
			imgFood.setSize(root.getWidth(), 300);
			ItemFood food = new ItemFood(urlImage, id, imgFood, foodname,
					shortDescription, numberLike);
			ImageDownloader.getInstance().download(urlImage, imgFood);
			tbFood.add(food).expand().fill().center();
			root.add(tbFood).expand().fill().colspan(5).center().padLeft(30)
					.padTop(20);
		}

		public Table getRoot() {
			return root;
		}
	}

	public class ActivityLogin {

		public Table	root, tbAva, tbInfo, tbContent, tbTime, tbFood;
		public Image	iconClock;
		Label			lbInfo, lbTime;

		public ActivityLogin(String content, String time) {
			super();
			root = new Table();
			root.setSize(400, 300);
			root.setTouchable(Touchable.disabled);
			buildTime(time);
			buildInfor(content);
			buildIconAvatar();
			config();
			buildImageAvatar(UserInfo.getInstance().getAvatarUrl(), UserInfo
					.getInstance().getName());

		}

		public void config() {
			root.add(tbAva).expandY().width(60).height(60);
			root.add(tbInfo).expand().fill();
			root.add(tbTime).expandY().width(100);
			root.row();
		}

		public void buildTime(String time) {
			tbTime = new Table();
			Image image = new Image(Assets.instance.uiA.reg_clock_icon);
			image.setSize(20, 20);
			image.setColor(Color.GRAY);
			tbTime.add(image).expand().width(20).height(20).right().padRight(5);

			lbTime = new Label(time, style_small);
			lbTime.setAlignment(Align.center, Align.left);
			lbTime.setWrap(true);
			tbTime.add(lbTime).expand().fill();
		}

		public void buildIconAvatar() {
			tbAva = new Table();
			MarkImage image = new MarkImage(Assets.instance.uiM.bg_ninepatch);
			image.setSize(100, 100);
			tbAva.add(image).expand().fillY();
			if (AppPreference.instance._login) {
				ImageDownloader.getInstance().download(
						AppPreference.instance._avatar, image);
			}
		}

		public void buildInfor(String data) {
			tbInfo = new Table();
			lbInfo = new Label(data, style_normal);
			lbInfo.setAlignment(Align.center, Align.left);
			lbInfo.setWrap(true);
			tbInfo.add(lbInfo).expand().fill().padLeft(5);
		}

		public void buildImageAvatar(String urlImage, String name) {
			tbFood = new Table();
			tbFood.setSize(root.getWidth(), 300);
			Image imgFood = new Image(new NinePatch(
					Assets.instance.uiM.bg_ninepatch));
			imgFood.setSize(root.getWidth(), 300);
			ImageDownloader.getInstance().download(
					urlImage.replaceAll("100", "250"), imgFood);
			tbFood.add(imgFood).expand().fill().center().height(250).width(350);
			root.add(tbFood).expand().fill().colspan(5).center().padLeft(30)
					.padTop(20);
		}

		public Table getRoot() {
			return root;
		}

	}

	public class ItemFood extends Item {

		Image			bg;
		Image			subbg;
		Label			stt_heart;
		public Image	icon_heart;
		final int		foodId;
		String			urlImage;

		public ItemFood(String urlImage, int id, Image bg, String title,
				String content, int numberLikes) {
			this.foodId = id;
			setWidth(400);
			setHeight(300);
			bg.setPosition(getX(), getY());
			bg.setSize(400, 300);
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
							Request.getInstance().requestLike(foodId,
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

			stt_heart = new Label("" + numberLikes, style_normal);
			stt_heart.setPosition(icon_heart.getX() - 2 - stt_heart.getWidth(),
					icon_heart.getY());
			// this.content = new Label(getSubString(content, stt_heart.getX() -
			// subbg.getX() - 40), style_normal);

			addActor(bg);
			addActor(subbg);
			// addActor(this.content);
			addActor(icon_heart);
			addActor(stt_heart);
		}

		public int getId() {
			return foodId;
		}
	}

	private void onSetting() {
		EditProfileView editProfileView = new EditProfileView();
		editProfileView.build(getStage(), _viewController);
		editProfileView.buildInfo(
				StringSystem.VIEW_USER_INFOR,
				StringSystem.VIEW_EDIT_USER_INFO,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN)).buildComponent();
		_viewController.addView(editProfileView);
		editProfileView.show();
	}

	private void onBack() {
		back();
	}

	public void requestGetProfile() {
		Request.getInstance().requestProfile(listener);
	}

	HttpResponseListener	listener	= new HttpResponseListener() {

											@Override
											public void handleHttpResponse(
													HttpResponse httpResponse) {
												System.out
														.println("GetProfile Success");
												responseData = httpResponse
														.getResultAsString();
												isLoadData = true;
											}

											@Override
											public void failed(Throwable t) {
												System.out
														.println("GetProfile Failed");
											}

											@Override
											public void cancelled() {

											}
										};
}

class LikeFoodListener implements HttpResponseListener {
	Image	img;

	public LikeFoodListener(Image img) {
		super();
		this.img = img;
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		String responseData = httpResponse.getResultAsString();
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
