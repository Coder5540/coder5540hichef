package com.aia.hichef.views.imp;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.Constant;
import com.aia.hichef.networks.ExtParamsKey;
//import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.networks.Request;
import com.aia.hichef.poolsystem.ItemFood;
import com.aia.hichef.poolsystem.PoolSystem;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.ui.ListFood;
import com.aia.hichef.ui.n.ImageOn;
import com.aia.hichef.ui.n.ListCategory;
import com.aia.hichef.ui.n.ListCategory.ItemCategory;
import com.aia.hichef.ui.n.TabContent;
import com.aia.hichef.ui.n.TabItem;
import com.aia.hichef.ui.n.TabView;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class HomeView extends View {

	public TabView					tabView;
	DataCategory					dataCategory;

	public ListCategory				listCategory;
	public ListFood					listNewFood;
	public ListFood					listLikest;
	public ListFood					listViewest;
	public ListFood					listRecomend;

	public ArrayList<DataFood>		listData		= new ArrayList<DataFood>();

	public ArrayList<RequestFood>	listRequestFood	= new ArrayList<RequestFood>();

	boolean							isLoadCate2		= false;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public HomeView buildName(String name) {
		super.setName(name);
		this.name = name;
		return this;
	}

	public HomeView buildBound(Rectangle bound) {
		super.setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public HomeView buildComponent() {
		tabView = new TabView(getX(), getY(), getWidth(), getHeight());
		listCategory = new ListCategory(_viewController, new Table());
		listNewFood = new ListFood(_viewController, new Table());
		listLikest = new ListFood(_viewController, new Table());
		listViewest = new ListFood(_viewController, new Table());
		listRecomend = new ListFood(_viewController, new Table());

		listCategory.setBounds(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
						- Constants.HEIGHT_TABBAR);
		listNewFood.setBounds(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
						- Constants.HEIGHT_TABBAR);
		listLikest.setBounds(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
						- Constants.HEIGHT_TABBAR);
		listViewest.setBounds(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
						- Constants.HEIGHT_TABBAR);
		listRecomend.setBounds(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
						- Constants.HEIGHT_TABBAR);

		listLikest.setVisible(true);
		listViewest.setVisible(true);
		listRecomend.setVisible(true);
		listNewFood.setVisible(true);

		dataCategory = new DataCategory();
		dataCategory.listData = listCategory;
		dataCategory.container = new TabContent(dataCategory.listData) {
			@Override
			public void onReload(boolean isLoadMore) {
				if (isLoadMore) {
				} else {
					listCategory.removeAll();
					ItemCategory item = listCategory.getItem(0, "Xếp hạng",
							Constants.WIDTH_SCREEN, 70);
					item.setListener(new ItemListener() {
						@Override
						public void onItemClick() {
							reset();
							chooseHot();
						}
					});
					listCategory.addItem(item);
					Request.getInstance().getCategoryList(0,
							new GetListCategoryListener(dataCategory));
				}
			}
		};
		ItemCategory item = listCategory.getItem(0, "Xếp hạng",
				Constants.WIDTH_SCREEN, 70);
		item.setListener(new ItemListener() {
			@Override
			public void onItemClick() {
				reset();
				chooseHot();
			}
		});

		listCategory.addItem(item);

		Request.getInstance().getCategoryList(0,
				new GetListCategoryListener(dataCategory));
		tabView.addTab(new TabItem("Danh mục", dataCategory.container));
		chooseHot();
		addActor(tabView);
		setViewState(ViewState.HIDE);
		FullLoading.instance.hide();
		return this;
	}

	boolean	isExit		= false;
	float	timeExit	= 0;

	@Override
	public void back() {
		if (AbstractGameScreen.keyboard.isShowing()) {
			AbstractGameScreen.keyboard.hide();
			return;
		}

		if (_viewController.getView(StringSystem.VIEW_MAIN_MENU).getViewState() == ViewState.SHOW) {
			_viewController.getView(StringSystem.VIEW_MAIN_MENU).hide();
			return;
		}

		if (_viewController.getView(StringSystem.VIEW_SEARCH).getViewState() == ViewState.SHOW) {
			_viewController.getView(StringSystem.VIEW_SEARCH).hide();
			return;
		}

		if (!isExit) {
			isExit = true;
			Toast.makeText(getStage(), "Nhấn thêm lần nữa để thoát!",
					Toast.LENGTH_SHORT);
		} else {
			System.exit(0);
		}
	}

	public void chooseHot() {
		getDownLoader().clear();
		getDownLoader().dispose();
		listRequestFood.removeAll(listRequestFood);
		listLikest.removeAll();
		listNewFood.removeAll();
		listRecomend.removeAll();
		listViewest.removeAll();
		listData.removeAll(listData);

		final DataFood data = new DataFood();
		data.listFood = listRecomend;
		data.container = new TabContent(listRecomend) {
			@Override
			public void onReload(boolean isLoadMore) {
				if (isLoadMore) {
					listRequestFood.add(new RequestFood(data,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_RECOMENDER));
					if (data.listFood.getSize() % 5 != 1)
						data.listFood.addItem(listCategory.getItemLoading());
				} else {
					data.listFood.removeAll();
					listRequestFood.add(new RequestFood(data,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_RECOMENDER));
				}
			}
		};
		listData.add(data);
		tabView.addTab(new TabItem("Đề xuất", data.container));
		listRequestFood.add(new RequestFood(data, Constant.FILTER_TYPE_BEST,
				Constant.FILTER_CATE_RECOMENDER));

		final DataFood data2 = new DataFood();
		data2.listFood = listNewFood;
		data2.container = new TabContent(listNewFood) {
			@Override
			public void onReload(boolean isLoadMore) {
				if (isLoadMore) {
					listRequestFood.add(new RequestFood(data2,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_NEWEST));
					data2.listFood.addItem(listCategory.getItemLoading());
				} else {
					data2.listFood.removeAll();
					listRequestFood.add(new RequestFood(data2,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_NEWEST));
				}
			}
		};
		listData.add(data2);
		tabView.addTab(new TabItem("Mới nhất", data2.container));
		listRequestFood.add(new RequestFood(data2, Constant.FILTER_TYPE_BEST,
				Constant.FILTER_CATE_NEWEST));

		final DataFood data3 = new DataFood();
		data3.listFood = listLikest;
		data3.container = new TabContent(listLikest) {
			@Override
			public void onReload(boolean isLoadMore) {
				if (isLoadMore) {
					listRequestFood.add(new RequestFood(data3,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_LIKEST));
					data3.listFood.addItem(listCategory.getItemLoading());
				} else {
					data3.listFood.removeAll();
					listRequestFood.add(new RequestFood(data3,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_LIKEST));
				}
			}
		};
		listData.add(data3);
		tabView.addTab(new TabItem("Thích nhiều nhất", data3.container));
		listRequestFood.add(new RequestFood(data3, Constant.FILTER_TYPE_BEST,
				Constant.FILTER_CATE_LIKEST));

		final DataFood data4 = new DataFood();
		data4.listFood = listViewest;
		data4.container = new TabContent(listViewest) {
			@Override
			public void onReload(boolean isLoadMore) {
				if (isLoadMore) {
					listRequestFood.add(new RequestFood(data4,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_VIEWEST));
					data4.listFood.addItem(listCategory.getItemLoading());
				} else {
					data4.listFood.removeAll();
					listRequestFood.add(new RequestFood(data4,
							Constant.FILTER_TYPE_BEST,
							Constant.FILTER_CATE_VIEWEST));
				}
			}
		};
		listData.add(data4);
		tabView.addTab(new TabItem("Xem nhiều nhất", data4.container));
		listRequestFood.add(new RequestFood(data4, Constant.FILTER_TYPE_BEST,
				Constant.FILTER_CATE_VIEWEST));
		addActor(tabView);
		setViewState(ViewState.HIDE);
		tabView.scrollTo(1, startApp);
		startApp = false;
	}

	boolean	startApp	= true;

	public void reset() {
		tabView.remove();
		tabView = new TabView(getX(), getY(), getWidth(), getHeight());
		tabView.addTab(new TabItem("Loại", new TabContent(listCategory)));
		addActor(tabView);
		setViewState(ViewState.HIDE);
	}

	int count = 0;
	
	@Override
	public void update() {
		super.update();

		processResponseData();
		if (isExit) {
			timeExit += Gdx.graphics.getDeltaTime();
			if (timeExit >= 2) {
				timeExit = 0;
				isExit = false;
			}
		}
	}

	private void processResponseData() {
		if (listRequestFood.size() > 0) {
			if (!listRequestFood.get(0).isDownload) {
				listRequestFood.get(0).download();
			}
			if (listRequestFood.get(0).data.isDownload
					|| listRequestFood.get(0).data.isError) {
				listRequestFood.remove(0);
			}
		}

		for (DataFood data : listData) {
			if (data.isDownload) {
				JsonValue json = (new JsonReader()).parse(data.response);
				JsonValue jsonArray = json.get("content");
				data.container.isLoadingMore = false;
				data.container.content.invalidate();
				if (data.listFood.getSize() % 5 == 1)
					data.listFood.removeItem(data.listFood.getSize() - 1);
				if (jsonArray.size == 0) {
					data.listFood.addItem(listCategory.getItem(-1,
							"Chưa có dữ liệu!", Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN
									- Constants.HEIGHT_ACTIONBAR
									- Constants.HEIGHT_TABBAR));
				}
				for (int i = 0; i < jsonArray.size; i++) {
					JsonValue newfoodInfo = jsonArray.get(i);
					final int id = newfoodInfo.getInt(ExtParamsKey.ID);
					final String title = newfoodInfo
							.getString(ExtParamsKey.TITLE);
					String short_des = newfoodInfo
							.getString(ExtParamsKey.SHORT_DES);
					int views = newfoodInfo.getInt(ExtParamsKey.VIEWS);
					int likes = newfoodInfo.getInt(ExtParamsKey.LIKES);
					String url = newfoodInfo.getString(ExtParamsKey.URL_IMAGE);
					float width = Constants.WIDTH_SCREEN;
					float height = 280;
					ImageOn img = new ImageOn(url);
					img.setDrawable(new NinePatchDrawable(new NinePatch(
							Assets.instance.uiM.bg_ninepatch)));
					img.setSize(width, height);
					getDownLoader().download(url, img);
					ItemFood itemNewFood = PoolSystem.getInstance().itemFoodPool
							.obtain();
					itemNewFood.create(id, img, title, short_des, likes, views,
							width, height);
					itemNewFood.setListener(new ItemListener() {
						@Override
						public void onItemClick() {
							onDetail(id);
						}
					});
					data.listFood.addItem(itemNewFood);

				}

				data.listFood.setVisible(true);
				FullLoading.instance.hide();
				data.isDownload = false;
			}
		}
		if (dataCategory.isDownload) {
			JsonValue json = (new JsonReader()).parse(dataCategory.response);
			int idCate = json.getInt("category_id");
			if (idCate == 0) {
				JsonValue jsonArray = json.get("content");
				for (int i = 0; i < jsonArray.size; i++) {
					JsonValue categoryInfo = jsonArray.get(i);
					final int id = categoryInfo.getInt(ExtParamsKey.ID);
					final String title = categoryInfo
							.getString(ExtParamsKey.TITLE);
					final ItemCategory item = listCategory.getItem(id, title,
							Constants.WIDTH_SCREEN, 50);
					item.setListener(new ItemListener() {
						@Override
						public void onItemClick() {
							isLoadCate2 = true;
							getDownLoader().clear();
							getDownLoader().dispose();
							listRequestFood.removeAll(listRequestFood);

							Request.getInstance().getCategoryList(item.getId(),
									new GetListCategoryListener(dataCategory));
						}
					});
					listCategory.addItem(item);
				}
			} else {
				reset();
				JsonValue jsonArray = json.get("content");
				listData.removeAll(listData);
				for (int i = 0; i < jsonArray.size; i++) {
					JsonValue categoryInfo = jsonArray.get(i);
					final int id = categoryInfo.getInt(ExtParamsKey.ID);
					final String title = categoryInfo
							.getString(ExtParamsKey.TITLE);

					final DataFood data = new DataFood();
					data.isDownload = false;
					data.listFood = new ListFood(_viewController, new Table());
					data.container = new TabContent(data.listFood) {
						@Override
						public void onReload(boolean isLoadMore) {
							if (isLoadMore) {
								listRequestFood.add(new RequestFood(data, id));
								data.listFood.addItem(listCategory
										.getItemLoading());
							} else {
								data.listFood.removeAll();
								listRequestFood.add(new RequestFood(data, id));
							}
						}
					};
					tabView.addTab(new TabItem(title, data.container));
					listData.add(data);
					listRequestFood.add(new RequestFood(data, id));

				}
				tabView.scrollTo(1, false);
			}
			dataCategory.isDownload = false;
		}
	}

	@Override
	public void show() {
		((Actor) _viewController.getView(StringSystem.VIEW_ACTION_BAR))
				.toFront();
		setVisible(true);
		super.show();
		TraceView.instance.reset();
	}

	@Override
	public void hide() {
		setVisible(false);
		setViewState(ViewState.HIDE);
		getDownLoader().pause();
	}

	private class GetListFoodListener implements HttpResponseListener {
		DataFood	data;

		public GetListFoodListener(DataFood data) {
			this.data = data;
		}

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			data.response = httpResponse.getResultAsString();
			data.isDownload = true;
			data.container.closeLoading();
		}

		@Override
		public void failed(Throwable t) {
			data.container.doError();
			data.isError = true;
		}

		@Override
		public void cancelled() {
			data.isError = true;
		}

	}

	private class GetListCategoryListener implements HttpResponseListener {
		DataCategory	data;

		public GetListCategoryListener(DataCategory data) {
			this.data = data;
		}

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			data.response = httpResponse.getResultAsString();
			data.isDownload = true;
			data.container.closeLoading();
			System.out.println("Sucess " + data.response);
		}

		@Override
		public void failed(Throwable t) {
			System.out.println("Error");
			data.container.doError();
			data.isError = true;
		}

		@Override
		public void cancelled() {
			System.out.println("Cancel");
			data.isError = true;
		}

	}

	public void onDetail(int id) {
		FoodDetailView foodDetailView = new FoodDetailView(id,
				StringSystem.VIEW_HOME);
		foodDetailView.build(getStage(), _viewController);
		foodDetailView.buildInfo(
				StringSystem.VIEW_FOOD_DETAIL,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN)).buildComponent();
		_viewController.addView(foodDetailView);
		foodDetailView.show();
	}

	public class DataFood {
		boolean		isError		= false;
		ListFood	listFood;
		String		response	= "";
		TabContent	container;
		boolean		isDownload	= false;

		public DataFood() {
		}
	}

	public class DataCategory {
		ListCategory	listData;
		String			response	= "";
		TabContent		container;
		boolean			isDownload	= false;
		boolean			isError		= false;

		public DataCategory() {
		}
	}

	public class RequestFood {
		DataFood				data;
		int						filter_type;
		int						filter_cate;
		int						start;
		int						length;
		HttpResponseListener	listener;

		boolean					isDownload	= false;

		public RequestFood(int filter_type, int filter_cate, int start,
				int length, HttpResponseListener listener, DataFood data) {
			this.data = data;
			this.filter_cate = filter_cate;
			this.filter_type = filter_type;
			this.start = start;
			this.length = length;
			this.listener = listener;
		}

		public RequestFood(DataFood data, int id) {
			this.data = data;
			this.filter_type = Constant.FILTER_TYPE_MEAL;
			this.filter_cate = id;
			this.start = data.listFood.getSize();
			this.length = 5;
			this.listener = new GetListFoodListener(data);
		}

		public RequestFood(DataFood data, int filter_type, int id) {
			this.data = data;
			this.filter_type = filter_type;
			this.filter_cate = id;
			this.start = data.listFood.getSize();
			this.length = 5;
			this.listener = new GetListFoodListener(data);
		}

		public void download() {
			Request.getInstance().getFoodList(filter_type, filter_cate, start,
					length, listener);
			isDownload = true;
		}
	}
}
