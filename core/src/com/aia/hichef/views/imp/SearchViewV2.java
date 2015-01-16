package com.aia.hichef.views.imp;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.hd.input.keyboard.KeyboardConfig;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class SearchViewV2 extends View {
	private Image		bg;
	private String		response;
	private boolean		isLoad;
	private Group		groupTop;
	private Image		bgTop;
	private ImageButton	btnBack;
	private ImageButton	btnSearch;
	private ImageButton	btnDel;
	private ImageButton	btnSubMenu;
	private Image		bgtfSearch;
	public TextField	tfSearch;
	public ListView		listSearch;
	private Table		content;

	private Table		history;
	private Table		historyContent;
	private ImageLabel	btnClearHistory;
	private Label		titleHistory;

	private String		currentTextField	= "", beforeTextField = "";

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public SearchViewV2 buildName(String name) {
		setName(name);
		return this;
	}

	public SearchViewV2 buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public SearchViewV2 buildComponent() {
		bg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(Color.WHITE)));
		bg.setSize(getWidth(), getHeight());
		bg.setPosition(getX(), getY());
		top();
		groupTop = new Group();
		groupTop.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR);
		bgTop = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f)));
		bgTop.setSize(groupTop.getWidth(), groupTop.getHeight());
		// tfSearch
		TextFieldStyle style = new TextFieldStyle();
		style.background = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.textfield, 4, 4, 4, 4));
		style.font = Assets.instance.fontFactory.getRegular20();
		style.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		style.fontColor = Color.BLACK;
		final float heighKeyboad = AbstractGameScreen.keyboard.getHeight()
				* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight();
		tfSearch = new TextField("", style);
		bgtfSearch = new Image(new NinePatch(Assets.instance.uiP.textfield, 4,
				4, 4, 4));
		bgtfSearch.setSize(2 * Constants.WIDTH_SCREEN / 3, 45);
		tfSearch.setSize(2 * Constants.WIDTH_SCREEN / 3 - 40, 45);
		tfSearch.setOnscreenKeyboard(AbstractGameScreen.keyboard);
		tfSearch.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (tfSearch.getText().equals("")) {
					content.setVisible(true);
					listSearch.setVisible(false);
				} else {
					listSearch.setVisible(true);
				}
				toFront();
				registerKeyBoard();
				listSearch.setHeight(Constants.HEIGHT_SCREEN
						- Constants.HEIGHT_ACTIONBAR - heighKeyboad);
				listSearch.setY(heighKeyboad);
				return true;
			}

			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (!tfSearch.getText().isEmpty()) {
					btnDel.setVisible(true);
					content.setVisible(false);
					listSearch.setVisible(true);
				} else {
					btnDel.setVisible(false);
					listSearch.removeAll();
					listSearch.setVisible(false);
					historyContent.clearChildren();
					loadHistory();
					content.setVisible(true);
				}
				return true;
			}

		});

		// btn Back
		btnBack = new ImageButton(new Image(Assets.instance.uiA.reg_back), 50,
				bgTop.getHeight());
		btnBack.setListener(new ItemListener() {
			@Override
			public void onItemClick() {
				tfSearch.getOnscreenKeyboard().show(false);
				AbstractGameScreen.keyboard.clear();
				hide();
				if (_viewController
						.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
					_viewController.getView(StringSystem.VIEW_SEARCHRESULT)
							.hide();
				}
				_viewController.getView(StringSystem.VIEW_HOME).show();
				((HomeView) _viewController.getView(StringSystem.VIEW_HOME))
						.setTouchable(Touchable.enabled);
			}
		});

		// button Search
		btnSearch = new ImageButton(new Image(Assets.instance.uiP.iconSearch),
				50, bgTop.getHeight());
		btnSearch.setListener(new ItemListener() {
			@Override
			public void onItemClick() {
				if (!tfSearch.getText().equals("")) {
					if (!checkHistory(tfSearch.getText())) {
						AppPreference.instance.saveNewHistory(tfSearch
								.getText());
					} else {
						AppPreference.instance.frontHistory(tfSearch.getText());
					}
					_viewController.getView(StringSystem.VIEW_HOME).hide();
					if (!_viewController
							.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
						ViewSearchResult viewSearchResult = new ViewSearchResult();
						viewSearchResult.build(getStage(), _viewController);
						viewSearchResult
								.buildName(StringSystem.VIEW_SEARCHRESULT)
								.buildBound(
										new Rectangle(0, 0,
												Constants.WIDTH_SCREEN,
												Constants.HEIGHT_SCREEN))
								.buildComponent();
						_viewController.addView(viewSearchResult);
					}
					((Actor) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT)).toFront();
					_viewController.getView(StringSystem.VIEW_SEARCHRESULT)
							.show();

					((ViewSearchResult) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT))
							.setKeySearch(tfSearch.getText());
					tfSearch.getOnscreenKeyboard().show(false);
					getStage().setKeyboardFocus(null);
				}
			}
		});

		// button Del
		btnDel = new ImageButton(new Image(Assets.instance.uiP.rgDel), 50,
				bgTop.getHeight());
		btnDel.setVisible(false);
		btnDel.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				btnDel.setVisible(false);
				historyContent.clearChildren();
				loadHistory();
				content.setVisible(true);
				listSearch.removeAll();
				listSearch.setVisible(false);
				AbstractGameScreen.keyboard.clear();
				registerKeyBoard();
				toFront();
			}
		});
		// button SubMenu
		btnSubMenu = new ImageButton(
				new Image(Assets.instance.uiP.iconSubMenu), 50,
				bgTop.getHeight());
		btnSubMenu.icon.setSize(50, 50);
		btnSubMenu.icon.setPosition(btnSubMenu.getX() + btnSubMenu.getWidth()
				/ 2 - btnSubMenu.icon.getWidth() / 2, btnSubMenu.getY()
				+ btnSubMenu.getHeight() / 2 - btnSubMenu.icon.getHeight() / 2);
		btnSubMenu.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
			}
		});

		btnBack.setPosition(
				groupTop.getX(),
				groupTop.getY() + groupTop.getHeight() / 2
						- btnBack.getHeight() / 2);
		btnSearch.setPosition(
				btnBack.getX() + btnBack.getWidth(),
				groupTop.getY() + groupTop.getHeight() / 2
						- btnSearch.getHeight() / 2);
		tfSearch.setPosition(
				btnSearch.getX() + btnSearch.getWidth(),
				groupTop.getY() + groupTop.getHeight() / 2
						- tfSearch.getHeight() / 2);
		bgtfSearch.setPosition(tfSearch.getX(), tfSearch.getY());
		btnDel.setPosition(
				tfSearch.getX() + bgtfSearch.getWidth() - btnDel.getWidth(),
				groupTop.getY() + groupTop.getHeight() / 2 - btnDel.getHeight()
						/ 2);
		btnSubMenu.setPosition(groupTop.getX() + groupTop.getWidth()
				- btnSubMenu.getWidth(), groupTop.getY() + groupTop.getHeight()
				/ 2 - btnSubMenu.getHeight() / 2);
		bgTop.setPosition(groupTop.getX(), groupTop.getY());

		groupTop.addActor(bgTop);
		groupTop.addActor(btnBack);
		groupTop.addActor(bgtfSearch);
		groupTop.addActor(btnSearch);
		groupTop.addActor(tfSearch);
		groupTop.addActor(btnDel);
		groupTop.addActor(btnSubMenu);

		//
		content = new Table();
		content.setBounds(getX(), AbstractGameScreen.keyboard.getHeight()
				* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight(),
				getWidth(), Constants.HEIGHT_SCREEN
						- AbstractGameScreen.keyboard.getHeight()
						* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight()
						- Constants.HEIGHT_ACTIONBAR);
		content.setY(AbstractGameScreen.keyboard.getHeight()
				* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight());

		listSearch = new ListView(_viewController, new Table());
		listSearch.setBounds(content.getX(), content.getY(),
				content.getWidth() - 2, content.getHeight());

		// history
		history = new Table();
		history.setWidth(getWidth());
		history.top().left();
		historyContent = new Table();
		titleHistory = new Label("Lịch sử tìm kiếm", new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						Color.BLACK)));
		titleHistory.setSize(
				Assets.instance.fontFactory.getMedium20().getBounds(
						"Lịch sử tìm kiếm").width,
				Assets.instance.fontFactory.getMedium20().getBounds(
						"Lịch sử tìm kiếm").height);
		historyContent.setWidth(history.getWidth());
		historyContent.left();
		btnClearHistory = new ImageLabel("Xóa lịch sử", new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), Color.BLACK));
		btnClearHistory.bg.getColor().a = 1;
		btnClearHistory.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				btnClearHistory.touchPoint.set(x, y);
				btnClearHistory.bg.setColor(200 / 255f, 200 / 255f, 200 / 255f,
						1);
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				btnClearHistory.bg.setColor(230 / 255f, 230 / 255f, 230 / 255f,
						1);
				if (btnClearHistory.touchPoint.epsilonEquals(x, y, 20)) {
					AppPreference.instance.resetHistory();
					historyContent.clearChildren();
					loadHistory();
				}
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {

				if (!btnClearHistory.touchPoint.epsilonEquals(x, y, 40)) {
					btnClearHistory.touchPoint.set(0, 0);
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
		history.add(titleHistory).width(history.getWidth() / 2).left()
				.padLeft(15);
		history.add(btnClearHistory).width(history.getWidth() / 2).bottom()
				.padLeft(95);
		history.row().padTop(15);
		history.add(historyContent).width(history.getWidth() - 20).colspan(2)
				.padLeft(10).left();

		// add to content
		content.top();
		content.add(history).width(history.getWidth()).padTop(20);
		content.row().padTop(30);
		// content.add(contentSearchAdvance)
		// .width(contentSearchAdvance.getWidth());

		//
		listSearch.setPosition(getX(), AbstractGameScreen.keyboard.getHeight()
				* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight());
		// add to table
		addActor(bg);
		add(groupTop).width(groupTop.getWidth()).height(groupTop.getHeight());
		row().padTop(5);
		add(content).width(getWidth());
		addActor(listSearch);
		listSearch.setVisible(false);

		return this;
	}

	@Override
	public void back() {
		tfSearch.getOnscreenKeyboard().show(false);
		AbstractGameScreen.keyboard.clear();
		hide();
		if (_viewController.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
			_viewController.getView(StringSystem.VIEW_SEARCHRESULT).hide();
		}
		_viewController.getView(StringSystem.VIEW_HOME).show();
		((HomeView) _viewController.getView(StringSystem.VIEW_HOME))
				.setTouchable(Touchable.enabled);

	}

	public void registerKeyBoard() {
		tfSearch.getOnscreenKeyboard().show(true);
		AbstractGameScreen.keyboard.registerTextField(tfSearch,
				KeyboardConfig.NORMAL, KeyboardConfig.SINGLE_LINE);
		getStage().setKeyboardFocus(tfSearch);
		AbstractGameScreen.keyboard.setDoneListener(new OnDoneListener() {
			@Override
			public void done() {
				if (!tfSearch.getText().equals("")) {
					if (!checkHistory(tfSearch.getText())) {
						AppPreference.instance.saveNewHistory(tfSearch
								.getText());
					} else {
						AppPreference.instance.frontHistory(tfSearch.getText());
					}
					_viewController.getView(StringSystem.VIEW_HOME).hide();
					if (!_viewController
							.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
						ViewSearchResult viewSearchResult = new ViewSearchResult();
						viewSearchResult.build(getStage(), _viewController);
						viewSearchResult
								.buildName(StringSystem.VIEW_SEARCHRESULT)
								.buildBound(
										new Rectangle(0, 0,
												Constants.WIDTH_SCREEN,
												Constants.HEIGHT_SCREEN))
								.buildComponent();
						_viewController.addView(viewSearchResult);
					}
					((Actor) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT)).toFront();
					_viewController.getView(StringSystem.VIEW_SEARCHRESULT)
							.show();

					((ViewSearchResult) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT))
							.setKeySearch(tfSearch.getText());
					getStage().setKeyboardFocus(null);
				}
			}
		});
		AbstractGameScreen.keyboard.setHideListener(new OnHideListener() {

			@Override
			public void hide() {
				listSearch.setHeight(Constants.HEIGHT_SCREEN
						- Constants.HEIGHT_ACTIONBAR);
				listSearch.setY(0);
			}
		});
	}

	private void loadHistory() {
		float maxwidth = 0;
		float maxheight = 0;
		int currentRow = 0;
		ArrayList<Table> row = new ArrayList<Table>();
		row.add(new Table());
		row.get(0).setWidth(historyContent.getWidth());
		row.get(0).left();
		for (int i = AppPreference.instance.size_listHistory - 1; i >= 0; i--) {
			final ImageLabel historyItem = new ImageLabel(
					AppPreference.instance.loadHistory(i), new LabelStyle(
							Assets.instance.fontFactory.getLight15(),
							Color.BLACK));
			if (maxwidth > historyContent.getWidth() - historyItem.getWidth()
					- 35) {
				maxwidth = 0;
				maxheight += historyItem.getHeight();
				currentRow++;
				row.add(new Table());
				row.get(currentRow).setWidth(historyContent.getWidth());
				row.get(currentRow).left();
			}
			historyItem.setListener(new ItemListener() {
				@Override
				public void onItemClick() {
					AppPreference.instance.frontHistory(historyItem.lb
							.getText().toString());
					tfSearch.setText(historyItem.lb.getText().toString());
					btnDel.setVisible(true);
					content.setVisible(false);
					_viewController.getView(StringSystem.VIEW_HOME).hide();
					if (!_viewController
							.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
						ViewSearchResult viewSearchResult = new ViewSearchResult();
						viewSearchResult.build(getStage(), _viewController);
						viewSearchResult
								.buildName(StringSystem.VIEW_SEARCHRESULT)
								.buildBound(
										new Rectangle(0, 0,
												Constants.WIDTH_SCREEN,
												Constants.HEIGHT_SCREEN))
								.buildComponent();
						_viewController.addView(viewSearchResult);
					}
					((Actor) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT)).toFront();
					_viewController.getView(StringSystem.VIEW_SEARCHRESULT)
							.show();

					((ViewSearchResult) _viewController
							.getView(StringSystem.VIEW_SEARCHRESULT))
							.setKeySearch(tfSearch.getText());
					tfSearch.getOnscreenKeyboard().show(false);
					getStage().setKeyboardFocus(null);
				}
			});
			row.get(currentRow).left();
			row.get(currentRow).add(historyItem).width(historyItem.getWidth())
					.height(historyItem.getHeight()).padLeft(5);
			maxwidth += historyItem.getWidth();
		}

		for (int i = 0; i <= currentRow; i++) {
			historyContent.add(row.get(i)).fillX().padTop(10).row();
		}
		if (AppPreference.instance.size_listHistory > 0) {
			btnClearHistory.setVisible(true);
		} else {
			btnClearHistory.setVisible(false);
		}
	}

	public void setKeySearch(String key) {
		Request.getInstance().search(key, new GetSearchContent());
	}

	@Override
	public void update() {
		super.update();
		currentTextField = tfSearch.getText();
		if (currentTextField.length() > 0
				&& !currentTextField.equals(beforeTextField)) {
			setKeySearch(currentTextField);
			beforeTextField = currentTextField;
		}
		if (isLoad) {
			listSearch.removeAll();
			JsonValue listsearch = (new JsonReader()).parse(response);
			JsonValue arrayFoodList = listsearch.get(ExtParamsKey.FOOD_LIST);
			JsonValue arrayFoodCategory = listsearch
					.get(ExtParamsKey.CATEGORY_LIST);

			listSearch.addTitle("Đồ ăn", 20, 20, 5, Color.BLACK);
			Image line1 = new Image(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, 1, 1, 1, 1));
			line1.setColor(new Color(100 / 255f, 100 / 255f, 100 / 255f, 0.8f));
			listSearch.addLine(line1, listSearch.getWidth(), 2, 0, 0, 20);
			try {
				for (int i = 0; i < arrayFoodList.size; i++) {
					JsonValue foodInfo = arrayFoodList.get(i);
					ListItemSearch item = new ListItemSearch(
							foodInfo.getString(ExtParamsKey.TITLE), listSearch);
					final int id = foodInfo.getInt(ExtParamsKey.ID);
					String url = foodInfo.getString(ExtParamsKey.URL_IMAGE);
					ImageDownloader.getInstance().download(url, item.icon);
					item.setListener(new ItemListener() {
						@Override
						public void onItemClick() {
							AbstractGameScreen.keyboard.show(false);
							FoodDetailView foodDetailView = new FoodDetailView(
									id, getName());
							foodDetailView.build(getStage(), _viewController);
							foodDetailView.buildInfo(
									StringSystem.VIEW_FOOD_DETAIL,
									new Rectangle(0, 0, Constants.WIDTH_SCREEN,
											Constants.HEIGHT_SCREEN))
									.buildComponent();
							_viewController.addView(foodDetailView);
							_viewController.getView(
									StringSystem.VIEW_FOOD_DETAIL).show();

						}
					});
					listSearch.addItem(item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			listSearch.addTitle("Danh mục", 20, 40, 5, Color.BLACK);
			Image line2 = new Image(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, 1, 1, 1, 1));
			line2.setColor(new Color(100 / 255f, 100 / 255f, 100 / 255f, 0.8f));
			listSearch.addLine(line2, listSearch.getWidth(), 2, 0, 0, 20);
			for (int i = 0; i < arrayFoodCategory.size; i++) {
				JsonValue foodInfo = arrayFoodCategory.get(i);
				ListItemSearch item = new ListItemSearch(
						foodInfo.getString(ExtParamsKey.TITLE), listSearch);
				final int id = foodInfo.getInt(ExtParamsKey.ID);
				String url = foodInfo.getString(ExtParamsKey.URL_IMAGE);
				ImageDownloader.getInstance().download(url, item.icon);
				item.setListener(new ItemListener() {
					@Override
					public void onItemClick() {
						_viewController.getView(StringSystem.VIEW_HOME).hide();
						if (!_viewController
								.isContainView(StringSystem.VIEW_SEARCHRESULT)) {
							ViewSearchResult viewSearchResult = new ViewSearchResult();
							viewSearchResult.build(getStage(), _viewController);
							viewSearchResult
									.buildName(StringSystem.VIEW_SEARCHRESULT)
									.buildBound(
											new Rectangle(0, 0,
													Constants.WIDTH_SCREEN,
													Constants.HEIGHT_SCREEN))
									.buildComponent();
							_viewController.addView(viewSearchResult);
						}
						((Actor) _viewController
								.getView(StringSystem.VIEW_SEARCHRESULT))
								.toFront();
						_viewController.getView(StringSystem.VIEW_SEARCHRESULT)
								.show();

						((ViewSearchResult) _viewController
								.getView(StringSystem.VIEW_SEARCHRESULT))
								.getFoodList(id);
						tfSearch.getOnscreenKeyboard().show(false);
						getStage().setKeyboardFocus(null);
					}
				});
				listSearch.addItem(item);
			}
			response = "";
			isLoad = false;
		}
	}

	private boolean checkHistory(String history) {
		boolean check = false;
		for (int i = 0; i < AppPreference.instance.size_listHistory; i++) {
			if (AppPreference.instance.loadHistory(i).equals(history)) {
				check = true;
				break;
			}
		}
		return check;
	}

	@Override
	public void show() {
		super.show();
		btnDel.setVisible(false);
		listSearch.removeAll();
		this.clearActions();
		this.toFront();
		setPosition(0, Constants.HEIGHT_SCREEN);
		this.addAction(Actions.moveTo(0, 0, 0.4f, Interpolation.exp10Out));
		this.setVisible(true);
		historyContent.clearChildren();
		loadHistory();
		content.setVisible(true);
		listSearch.setVisible(false);
	}

	@Override
	public void hide() {
		super.hide();
		this.clearActions();
		this.addAction(Actions.sequence(Actions.moveTo(0,
				Constants.HEIGHT_SCREEN, 0.5f, Interpolation.exp10Out), Actions
				.visible(false)));
	}

	public class GetSearchContent implements HttpResponseListener {

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			response = httpResponse.getResultAsString();
			isLoad = true;
			System.out.println(response);
		}

		@Override
		public void failed(Throwable t) {

		}

		@Override
		public void cancelled() {

		}
	}

	class ImageButton extends Table {
		Image	bg;
		Image	icon;
		Vector2	touchPoint;

		public ImageButton(Image icon, float width, float height) {
			setSize(width, height);
			touchPoint = new Vector2();
			this.icon = icon;
			bg = new Image(new NinePatch(Assets.instance.uiP.transparent));
			bg.getColor().a = 0;
			bg.setSize(width, height);
			bg.setPosition(getX(), getY());
			icon.setSize(35, 35);
			icon.setPosition(getX() + width / 2 - icon.getWidth() / 2, getY()
					+ getHeight() / 2 - icon.getHeight() / 2);
			addActor(bg);
			addActor(icon);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bg.getColor().a = 1;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						listener.onItemClick();
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bg.getColor().a = 0;
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

	class ImageLabel extends Table {
		Image	bg;
		Label	lb;
		Vector2	touchPoint;

		public ImageLabel(String title, LabelStyle style) {
			touchPoint = new Vector2();
			setSize(style.font.getBounds(title).width + 20,
					style.font.getBounds(title).height + 20);
			bg = new Image(new NinePatch(Assets.instance.uiP.textfield, 4, 4,
					4, 4));
			bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
			bg.setSize(this.getWidth(), this.getHeight());
			bg.setPosition(getX(), getY());
			lb = new Label(title, style);
			lb.setPosition(getX() + getWidth() / 2 - lb.getWidth() / 2, getY()
					+ getHeight() / 2 - lb.getHeight() / 2);
			addActor(bg);
			addActor(lb);
		}

		public ImageLabel(String title, LabelStyle style, float width,
				float height) {
			touchPoint = new Vector2();
			setSize(width, height);
			bg = new Image(new NinePatch(Assets.instance.uiP.textfield, 4, 4,
					4, 4));
			bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
			bg.setSize(this.getWidth(), this.getHeight());
			bg.setPosition(getX(), getY());
			lb = new Label(title, style);
			lb.setPosition(getX() + getWidth() / 2 - lb.getWidth() / 2, getY()
					+ getHeight() / 2 - lb.getHeight() / 2);
			addActor(bg);
			addActor(lb);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bg.setColor(200 / 255f, 200 / 255f, 200 / 255f, 1);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
					if (touchPoint.epsilonEquals(x, y, 20)) {
						listener.onItemClick();
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bg.setColor(new Color(230 / 255f, 230 / 255f,
								230 / 255f, 1));
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

	class ListItemSearch extends Item {

		public Image	icon;
		private Label	lbName;
		private Image	imgFocus;
		private int		id;

		public ListItemSearch(String name, ListView parent) {
			icon = new Image(Assets.instance.uiP.iconSmallSearch);
			lbName = new Label(name, new LabelStyle(
					Assets.instance.fontFactory.getLight15(), Color.BLACK));
			setSize(parent.getWidth(), 42);
			icon.setPosition(5, (this.getHeight() - icon.getHeight()) / 2);
			lbName.setPosition(icon.getX() + icon.getWidth() + 5,
					(this.getHeight() - lbName.getHeight()) / 2);
			imgFocus = new Image(new NinePatch(Assets.instance.uiP.transparent));
			imgFocus.setSize(getWidth(), getHeight());
			imgFocus.getColor().a = 0;

			this.addActor(icon);
			this.addActor(lbName);
			this.addActor(imgFocus);
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		Vector2	touchPoint	= new Vector2();

		@Override
		public void setListener(final ItemListener listener) {
			this.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					return super.touchDown(event, x, y, pointer, button);
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (touchPoint.epsilonEquals(x, y, 5)) {
						imgFocus.addAction(Actions.sequence(Actions.alpha(.2f),
								Actions.alpha(0f, .05f),
								Actions.run(new Runnable() {
									@Override
									public void run() {
										listener.onItemClick();
									}
								})));

					}
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}
}
