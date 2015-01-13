package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.Constant;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.poolsystem.ItemFood;
import com.aia.hichef.ui.ListFood;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class ViewSearchResult extends View {

	private Group		groupTop;
	private Image		bgTop;
	private ImageButton	btnBack;
	private ImageButton	btnSearch;
	private ImageButton	btnSubMenu;
	private String		keySearch;
	private Label		lbtextSearch;
	private Group		textSearch;

	public ListFood		listFood;
	private ImageLabel	btnFilterMaterial;
	private ImageLabel	btnFilerCategory;
	// private Table filterContent;
	// private Label lbfilterMeterical;
	// private Table filterMatericalCOntent;
	// private Table filterCategoryContent;
	private String		response;
	private boolean		isLoad;

	public ViewSearchResult() {
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public ViewSearchResult buildName(String name) {
		super.setName(name);
		return this;
	}

	public ViewSearchResult buildBound(Rectangle bound) {
		super.setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public ViewSearchResult buildComponent() {
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(Color.WHITE))));
		// group top
		groupTop = new Group();
		groupTop.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR);
		bgTop = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f)));
		bgTop.setSize(groupTop.getWidth(), groupTop.getHeight());
		// btn Back
		btnBack = new ImageButton(new Image(Assets.instance.uiA.reg_back), 50,
				bgTop.getHeight());
		btnBack.setListener(new ItemListener() {
			@Override
			public void onItemClick() {
				hide();
				_viewController.getView(StringSystem.VIEW_SEARCH).show();
				((SearchViewV2) _viewController
						.getView(StringSystem.VIEW_SEARCH)).tfSearch
						.setText("");
				((SearchViewV2) _viewController
						.getView(StringSystem.VIEW_SEARCH)).registerKeyBoard();
				((SearchViewV2) _viewController
						.getView(StringSystem.VIEW_SEARCH)).listSearch
						.setVisible(false);
				getStage().setKeyboardFocus(
						((SearchViewV2) _viewController
								.getView(StringSystem.VIEW_SEARCH)).tfSearch);
				_viewController.removeView(StringSystem.VIEW_FILTERCATEGORY);
				_viewController.removeView(StringSystem.VIEW_FILTERMATERIAL);
			}
		});

		// button Search
		btnSearch = new ImageButton(new Image(Assets.instance.uiP.iconSearch),
				50, bgTop.getHeight());
		btnSearch.setListener(new ItemListener() {
			@Override
			public void onItemClick() {
				searchAdvance();
			}
		});

		// textSearch
		textSearch = new Group();
		textSearch.setSize(2 * getWidth() / 3, 50);
		lbtextSearch = new Label("", new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						Color.WHITE)));
		lbtextSearch.setSize(
				textSearch.getWidth() - 20,
				Assets.instance.fontFactory.getMedium20().getBounds(
						lbtextSearch.getText()).height);
		lbtextSearch.setAlignment(Align.left);
		Image line = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				new Color(230 / 255f, 230 / 255f, 230 / 255f, 1f)));
		line.setSize(textSearch.getWidth() - 10, 2);

		lbtextSearch.setPosition(
				textSearch.getX() + textSearch.getWidth() / 2
						- lbtextSearch.getWidth() / 2,
				textSearch.getY() + textSearch.getHeight() / 2
						- lbtextSearch.getHeight() / 2);
		line.setPosition(
				textSearch.getX() + textSearch.getWidth() / 2 - line.getWidth()
						/ 2, lbtextSearch.getY() - line.getHeight() - 10);
		textSearch.addActor(lbtextSearch);
		textSearch.addActor(line);

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
				// LabelStyle style = new LabelStyle(Assets.instance.fontFactory
				// .getMedium20(), new Color(Color.BLACK));
				// NinePatch np = new
				// NinePatch(Assets.instance.uiM.bg_ninepatch,
				// 4, 4, 4, 4);
				// np.setColor(new Color(240 / 255f, 240 / 255f, 240 / 255f,
				// 1));
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU))
				// .setWidthContent(130);
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU)).removeAll();
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU))
				// .setBackground(new NinePatchDrawable(np));
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU))
				// .addButton(
				// new Image(new NinePatch(
				// Assets.instance.uiM.bg_ninepatch,
				// new Color(150 / 255f, 150 / 255f,
				// 150 / 255f, 1))), "Like",
				// style, new ItemListener() {
				//
				// @Override
				// public void onItemClick() {
				// Debug.console("like");
				// }
				// });
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU))
				// .addButton(
				// new Image(new NinePatch(
				// Assets.instance.uiM.bg_ninepatch,
				// new Color(150 / 255f, 150 / 255f,
				// 150 / 255f, 1))), "View",
				// style, new ItemListener() {
				//
				// @Override
				// public void onItemClick() {
				// Debug.console("view");
				// }
				// });
				// ((SubMenuView) _viewController
				// .getView(StringSystem.VIEW_SUBMENU))
				// .addButton(
				// new Image(new NinePatch(
				// Assets.instance.uiM.bg_ninepatch,
				// new Color(150 / 255f, 150 / 255f,
				// 150 / 255f, 1))), "Recommend",
				// style, new ItemListener() {
				//
				// @Override
				// public void onItemClick() {
				// Debug.console("recommend");
				// }
				// });
				// _viewController.focusView(StringSystem.VIEW_SUBMENU);
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
		textSearch.setPosition(
				btnSearch.getX() + btnSearch.getWidth(),
				groupTop.getY() + groupTop.getHeight() / 2
						- textSearch.getHeight() / 2);
		btnSubMenu.setPosition(groupTop.getX() + groupTop.getWidth()
				- btnSubMenu.getWidth(), groupTop.getY() + groupTop.getHeight()
				/ 2 - btnSubMenu.getHeight() / 2);
		bgTop.setPosition(groupTop.getX(), groupTop.getY());

		groupTop.addActor(bgTop);
		groupTop.addActor(btnBack);
		groupTop.addActor(textSearch);
		groupTop.addActor(btnSearch);
		groupTop.addActor(btnSubMenu);

		btnFilterMaterial = new ImageLabel("Lọc theo nguyên liệu",
				new LabelStyle(Assets.instance.fontFactory.getMedium20(),
						new Color(Color.BLACK)), getWidth() / 2 - 20, 50);

		btnFilerCategory = new ImageLabel("Lọc theo danh mục", new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						Color.BLACK)), getWidth() / 2 - 10, 50);

		btnFilterMaterial.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				if (!_viewController
						.isContainView(StringSystem.VIEW_FILTERMATERIAL)) {
					FilterMaterialView filterMaterial = new FilterMaterialView();
					filterMaterial.build(_stage, _viewController);
					filterMaterial.buildBound(
							new Rectangle(0, 0, Constants.WIDTH_SCREEN,
									Constants.HEIGHT_SCREEN)).buildComponent();
					_viewController.addView(filterMaterial);
				}
				_viewController.getView(StringSystem.VIEW_FILTERMATERIAL)
						.show();
			}
		});

		btnFilerCategory.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				if (!_viewController
						.isContainView(StringSystem.VIEW_FILTERCATEGORY)) {
					FilterCategoryView filterCategory = new FilterCategoryView();
					filterCategory.build(_stage, _viewController);
					filterCategory.buildBound(
							new Rectangle(0, 0, Constants.WIDTH_SCREEN,
									Constants.HEIGHT_SCREEN)).buildComponent();
					_viewController.addView(filterCategory);
				}
				_viewController.getView(StringSystem.VIEW_FILTERCATEGORY)
						.show();
			}
		});

		listFood = new ListFood(_viewController, new Table());
		listFood.setSize(getWidth(), getHeight());

		// add
		top();
		add(groupTop).width(groupTop.getWidth()).height(groupTop.getHeight())
				.colspan(2);
		row().padTop(5);
		add(btnFilterMaterial).width(btnFilterMaterial.getWidth()).height(60)
				.center();
		add(btnFilerCategory).width(btnFilerCategory.getWidth()).height(60)
				.center();
		row().padTop(10);
		add(listFood).colspan(2);
		return this;
	}

	public void setKeySearch(String key) {
		this.keySearch = key;
		lbtextSearch.setText("Từ khóa: " + key);
		Request.getInstance().getFoodSearchFull(key,
				new GetListFoodSearchFull());
	}

	public void searchAdvance() {
		String include = "";
		String exclude = "";
		int categoryId = -1;
		if (_viewController.isContainView(StringSystem.VIEW_FILTERMATERIAL)) {
			FilterMaterialView filterMateView = (FilterMaterialView) _viewController
					.getView(StringSystem.VIEW_FILTERMATERIAL);
			include = filterMateView.getInclude();
			exclude = filterMateView.getExclude();
		}
		if (_viewController.isContainView(StringSystem.VIEW_FILTERCATEGORY)) {
			FilterCategoryView filterCateView = (FilterCategoryView) _viewController
					.getView(StringSystem.VIEW_FILTERCATEGORY);
			categoryId = filterCateView.getIdChoiced();
		}

		Request.getInstance().searchAdvance(keySearch, -1, categoryId, include,
				exclude, new GetListFoodSearchFull());
	}

	public void getFoodList(int id) {
		Request.getInstance().getFoodList(Constant.FILTER_TYPE_MEAL, id,
				new GetListFoodSearchFull());
	}

	@Override
	public void update() {
		super.update();
		if (isLoad) {
			listFood.removeAll();
			JsonValue json = (new JsonReader()).parse(response);
			String result = json.getString(ExtParamsKey.RESULT);
			if (!result.equalsIgnoreCase("failed")) {
				JsonValue jsonArray = json.get(ExtParamsKey.CONTENT);
				if (jsonArray.size > 0) {
					for (int i = 0; i < jsonArray.size; i++) {
						JsonValue foodInfo = jsonArray.get(i);
						final int id = foodInfo.getInt(ExtParamsKey.ID);
						final String title = foodInfo
								.getString(ExtParamsKey.TITLE);
						String short_des = foodInfo
								.getString(ExtParamsKey.SHORT_DES);
						int views = foodInfo.getInt(ExtParamsKey.VIEWS);
						int likes = foodInfo.getInt(ExtParamsKey.LIKES);
						String url = foodInfo.getString(ExtParamsKey.URL_IMAGE);
						float width = 480;
						float height = 280;
						Image img = new Image(new NinePatch(
								Assets.instance.uiM.bg_ninepatch));
						img.setSize(width, height);
						ImageDownloader.getInstance().download(url, img);
						
						
						ItemFood itemNewFood = listFood.getItem(id, img, title,
								short_des, likes, views, width, height);
						itemNewFood.setListener(new ItemListener() {
							@Override
							public void onItemClick() {
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
								((Actor) _viewController
										.getView(StringSystem.VIEW_FOOD_DETAIL))
										.toFront();
								_viewController.getView(
										StringSystem.VIEW_FOOD_DETAIL).show();
								;

							}
						});
						listFood.addItem(itemNewFood);
					}
					listFood.setVisible(true);
				} else {
					Toast.makeText(_stage, "Không tìm thấy", Toast.LENGTH_SHORT);
					listFood.setVisible(false);
				}
			} else {
				listFood.setVisible(false);
				String mess = json.getString(ExtParamsKey.MESSAGE);
				Toast.makeText(_stage, mess, Toast.LENGTH_SHORT);
			}
			isLoad = false;
		}
	}

	@Override
	public void show() {
		super.show();
		this.setVisible(true);
	}

	@Override
	public void hide() {
		super.hide();
		this.setVisible(false);
	}

	@Override
	public void back() {
		hide();
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

	private class GetListFoodSearchFull implements HttpResponseListener {

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			response = httpResponse.getResultAsString();
			isLoad = true;
		}

		@Override
		public void failed(Throwable t) {

		}

		@Override
		public void cancelled() {

		}

	}

}
