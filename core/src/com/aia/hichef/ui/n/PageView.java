//package com.aia.hichef.ui.n;
//
//import com.aia.hichef.assets.Assets;
//import com.aia.hichef.enums.AnimationType;
//import com.aia.hichef.enums.ViewState;
//import com.aia.hichef.listener.ItemListener;
//import com.aia.hichef.networks.ExtParamsKey;
//import com.aia.hichef.networks.ImageDownloader;
//import com.aia.hichef.networks.Request;
//import com.aia.hichef.ui.CaterogyList;
//import com.aia.hichef.ui.CaterogyList.ItemCaterogy;
//import com.aia.hichef.views.IViewController;
//import com.aia.hichef.views.View;
//import com.badlogic.gdx.Net.HttpResponse;
//import com.badlogic.gdx.Net.HttpResponseListener;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.utils.JsonReader;
//import com.badlogic.gdx.utils.JsonValue;
//
//public class PageView extends View {
//
//	public TabView tabview;
//	public CaterogyList caterogy_chinh;
//	public CaterogyList caterogy_phu;
//	public CaterogyList caterogy_giavi;
//
//	@Override
//	public void build(Stage stage, IViewController viewController) {
//		super.build(stage, viewController);
//	}
//
//	public PageView buildName(String name) {
//		super.setName(name);
//		this.name = name;
//		return this;
//	}
//
//	public PageView buildBound(Rectangle bound) {
//		super.setBounds(bound.x, bound.y, bound.width, bound.height);
//		return this;
//	}
//
//	public PageView buildConponent() {
//		caterogy_chinh = new CaterogyList(new Table(), 480, 660);
//		caterogy_chinh.setVisible(false);
//		Request.getInstance()
//				.getMaterialCategory(1, new GetCategoryListener1());
//
//		caterogy_phu = new CaterogyList(new Table(), 480, 660);
//		caterogy_phu.setVisible(false);
//		Request.getInstance()
//				.getMaterialCategory(2, new GetCategoryListener2());
//
//		caterogy_giavi = new CaterogyList(new Table(), 480, 660);
//		caterogy_giavi.setVisible(false);
//		Request.getInstance()
//				.getMaterialCategory(3, new GetCategoryListener3());
//
//		tabview = new TabView(getX(), getY(), getWidth(), getHeight());
//		// tabview.addTab(new TabItem("DANH MUC", caterogy_chinh));
//		tabview.addTab(new TabItem("NGUYEN LIEU CHINH", caterogy_chinh));
//		tabview.addTab(new TabItem("NGUYEN LIEU PHU", caterogy_phu));
//		tabview.addTab(new TabItem("GIA VI", caterogy_giavi));
//		addActor(tabview);
//		// addActor(caterogy);
//		return this;
//	}
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//		super.update();
//	}
//
//	@Override
//	public void show(AnimationType showType) {
//		// TODO Auto-generated method stub
//		super.show(showType);
//	}
//
//	@Override
//	public void hide(AnimationType hideType) {
//		// TODO Auto-generated method stub
//		super.hide(hideType);
//	}
//
//	@Override
//	public void setViewState(ViewState state) {
//		// TODO Auto-generated method stub
//		super.setViewState(state);
//	}
//
//	@Override
//	public ViewState getViewState() {
//		// TODO Auto-generated method stub
//		return super.getViewState();
//	}
//
//	@Override
//	public Rectangle getBound() {
//		// TODO Auto-generated method stub
//		return super.getBound();
//	}
//
//	@Override
//	public void setName(String name) {
//		// TODO Auto-generated method stub
//		super.setName(name);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return super.getName();
//	}
//
//	@Override
//	public int getZXndex() {
//		// TODO Auto-generated method stub
//		return super.getZXndex();
//	}
//
//	@Override
//	public void setVisible(boolean visible) {
//		// TODO Auto-generated method stub
//		super.setVisible(visible);
//		addAction(Actions.alpha(visible ? 1f : 0f));
//	}
//
//	@Override
//	public void setZIndex(int index) {
//		// TODO Auto-generated method stub
//		super.setZIndex(index);
//	}
//
//	@Override
//	public void destroyComponent() {
//		// TODO Auto-generated method stub
//		super.destroyComponent();
//	}
//
//	private class GetCategoryListener1 implements HttpResponseListener {
//
//		@Override
//		public void handleHttpResponse(HttpResponse httpResponse) {
//			String response = httpResponse.getResultAsString();
//			JsonValue json = (new JsonReader()).parse(response);
//			// System.out.println(json);
//			JsonValue jsonArray = json.get("content");
//			for (int i = 0; i < jsonArray.size; i++) {
//				JsonValue caterogy = jsonArray.get(i);
//				int id = caterogy.getInt(ExtParamsKey.ID);
//				final String title = caterogy.getString(ExtParamsKey.TITLE);
//				String content = caterogy.getString(ExtParamsKey.CONTENT);
//				String url = caterogy.getString(ExtParamsKey.URL_IMAGE);
//				final JsonValue sub_caterogy = caterogy
//						.get(ExtParamsKey.SUB_CATEGORY);
//				System.out.println(sub_caterogy);
//				Image img = new Image(Assets.instance.uiM.icono_recetas);
//				ImageDownloader.getInstance().download(url, img);
//				ItemCaterogy item = PageView.this.caterogy_chinh.getItem(i + 1,
//						id, img, title, content,
//						PageView.this.caterogy_chinh.getWidth(), 110);
//				item.setListener(new ItemListener() {
//
//					@Override
//					public void onItemClick() {
//						System.out.println("click to " + title);
//						// View subViewCaterogy = new View();
//						// subViewCaterogy.build(_viewController.getStage(),
//						// _viewController);
//						// subViewCaterogy.setName("subViewCaterogy");
//						// subViewCaterogy.setBounds(0, 0, 480, 660);
//						// CaterogyList subCaterogy = new CaterogyList(
//						// new Table(), 480, 660);
//						// for (int i = 0; i < sub_caterogy.size; i++) {
//						// JsonValue subcaterogy = sub_caterogy.get(i);
//						// int id = subcaterogy.getInt(ExtParamsKey.ID);
//						// final String title = subcaterogy
//						// .getString(ExtParamsKey.TITLE);
//						// String content = subcaterogy
//						// .getString(ExtParamsKey.CONTENT);
//						// String url = subcaterogy
//						// .getString(ExtParamsKey.URL_IMAGE);
//						// Image img = new Image(
//						// Assets.instance.uiM.icono_recetas);
//						// ImageDownloader.getInstance().download(url, img);
//						// ItemCaterogy item = subCaterogy.getItem((i + 1),
//						// id, img, title, content,
//						// subCaterogy.getWidth(), 110);
//						// item.setListener(new ItemListener() {
//						//
//						// @Override
//						// public void onClick() {
//						// // TODO Auto-generated method stub
//						// System.out.println("click to " + title);
//						// // if (_viewController.getCurrentView()
//						// // .getName()
//						// // .equals("subViewCaterogy")) {
//						// // _viewController.focusView("testView");
//						// // }
//						// }
//						// });
//						// subCaterogy.addItem(item);
//						// }
//						// subViewCaterogy.addActor(subCaterogy);
//						// _viewController.addView(subViewCaterogy);
//						// _viewController.focusView("subViewCaterogy");
//					}
//				});
//				PageView.this.caterogy_chinh.addItem(item);
//			}
//			PageView.this.caterogy_chinh.setVisible(true);
//
//		}
//
//		@Override
//		public void failed(Throwable t) {
//		}
//
//		@Override
//		public void cancelled() {
//		}
//	}
//
//	private class GetCategoryListener2 implements HttpResponseListener {
//
//		@Override
//		public void handleHttpResponse(HttpResponse httpResponse) {
//			String response = httpResponse.getResultAsString();
//			JsonValue json = (new JsonReader()).parse(response);
//			// System.out.println(json);
//			JsonValue jsonArray = json.get("content");
//			for (int i = 0; i < jsonArray.size; i++) {
//				JsonValue caterogy = jsonArray.get(i);
//				int id = caterogy.getInt(ExtParamsKey.ID);
//				final String title = caterogy.getString(ExtParamsKey.TITLE);
//				String content = caterogy.getString(ExtParamsKey.CONTENT);
//				String url = caterogy.getString(ExtParamsKey.URL_IMAGE);
//				final JsonValue sub_caterogy = caterogy
//						.get(ExtParamsKey.SUB_CATEGORY);
//				System.out.println(sub_caterogy);
//				Image img = new Image(Assets.instance.uiM.icono_recetas);
//				ImageDownloader.getInstance().download(url, img);
//				ItemCaterogy item = PageView.this.caterogy_chinh.getItem(i + 1,
//						id, img, title, content,
//						PageView.this.caterogy_chinh.getWidth(), 110);
//				item.setListener(new ItemListener() {
//
//					@Override
//					public void onItemClick() {
//						System.out.println("click to " + title);
//						// View subViewCaterogy = new View();
//						// subViewCaterogy.build(_viewController.getStage(),
//						// _viewController);
//						// subViewCaterogy.setName("subViewCaterogy");
//						// subViewCaterogy.setBounds(0, 0, 480, 660);
//						// CaterogyList subCaterogy = new CaterogyList(
//						// new Table(), 480, 660);
//						// for (int i = 0; i < sub_caterogy.size; i++) {
//						// JsonValue subcaterogy = sub_caterogy.get(i);
//						// int id = subcaterogy.getInt(ExtParamsKey.ID);
//						// final String title = subcaterogy
//						// .getString(ExtParamsKey.TITLE);
//						// String content = subcaterogy
//						// .getString(ExtParamsKey.CONTENT);
//						// String url = subcaterogy
//						// .getString(ExtParamsKey.URL_IMAGE);
//						// Image img = new Image(
//						// Assets.instance.uiM.icono_recetas);
//						// ImageDownloader.getInstance().download(url, img);
//						// ItemCaterogy item = subCaterogy.getItem((i + 1),
//						// id, img, title, content,
//						// subCaterogy.getWidth(), 110);
//						// item.setListener(new ItemListener() {
//						//
//						// @Override
//						// public void onClick() {
//						// // TODO Auto-generated method stub
//						// System.out.println("click to " + title);
//						// // if (_viewController.getCurrentView()
//						// // .getName()
//						// // .equals("subViewCaterogy")) {
//						// // _viewController.focusView("testView");
//						// // }
//						// }
//						// });
//						// subCaterogy.addItem(item);
//						// }
//						// subViewCaterogy.addActor(subCaterogy);
//						// _viewController.addView(subViewCaterogy);
//						// _viewController.focusView("subViewCaterogy");
//					}
//				});
//				PageView.this.caterogy_phu.addItem(item);
//			}
//			PageView.this.caterogy_phu.setVisible(true);
//
//		}
//
//		@Override
//		public void failed(Throwable t) {
//		}
//
//		@Override
//		public void cancelled() {
//		}
//
//	}
//
//	private class GetCategoryListener3 implements HttpResponseListener {
//
//		@Override
//		public void handleHttpResponse(HttpResponse httpResponse) {
//			String response = httpResponse.getResultAsString();
//			JsonValue json = (new JsonReader()).parse(response);
//			// System.out.println(json);
//			JsonValue jsonArray = json.get("content");
//			for (int i = 0; i < jsonArray.size; i++) {
//				JsonValue caterogy = jsonArray.get(i);
//				int id = caterogy.getInt(ExtParamsKey.ID);
//				final String title = caterogy.getString(ExtParamsKey.TITLE);
//				String content = caterogy.getString(ExtParamsKey.CONTENT);
//				String url = caterogy.getString(ExtParamsKey.URL_IMAGE);
//				final JsonValue sub_caterogy = caterogy
//						.get(ExtParamsKey.SUB_CATEGORY);
//				System.out.println(sub_caterogy);
//				Image img = new Image(Assets.instance.uiM.icono_recetas);
//				ImageDownloader.getInstance().download(url, img);
//				ItemCaterogy item = PageView.this.caterogy_chinh.getItem(i + 1,
//						id, img, title, content,
//						PageView.this.caterogy_chinh.getWidth(), 110);
//				item.setListener(new ItemListener() {
//
//					@Override
//					public void onItemClick() {
//						System.out.println("click to " + title);
//						// View subViewCaterogy = new View();
//						// subViewCaterogy.build(_viewController.getStage(),
//						// _viewController);
//						// subViewCaterogy.setName("subViewCaterogy");
//						// subViewCaterogy.setBounds(0, 0, 480, 660);
//						// CaterogyList subCaterogy = new CaterogyList(
//						// new Table(), 480, 660);
//						// for (int i = 0; i < sub_caterogy.size; i++) {
//						// JsonValue subcaterogy = sub_caterogy.get(i);
//						// int id = subcaterogy.getInt(ExtParamsKey.ID);
//						// final String title = subcaterogy
//						// .getString(ExtParamsKey.TITLE);
//						// String content = subcaterogy
//						// .getString(ExtParamsKey.CONTENT);
//						// String url = subcaterogy
//						// .getString(ExtParamsKey.URL_IMAGE);
//						// Image img = new Image(
//						// Assets.instance.uiM.icono_recetas);
//						// ImageDownloader.getInstance().download(url, img);
//						// ItemCaterogy item = subCaterogy.getItem((i + 1),
//						// id, img, title, content,
//						// subCaterogy.getWidth(), 110);
//						// item.setListener(new ItemListener() {
//						//
//						// @Override
//						// public void onClick() {
//						// // TODO Auto-generated method stub
//						// System.out.println("click to " + title);
//						// // if (_viewController.getCurrentView()
//						// // .getName()
//						// // .equals("subViewCaterogy")) {
//						// // _viewController.focusView("testView");
//						// // }
//						// }
//						// });
//						// subCaterogy.addItem(item);
//						// }
//						// subViewCaterogy.addActor(subCaterogy);
//						// _viewController.addView(subViewCaterogy);
//						// _viewController.focusView("subViewCaterogy");
//					}
//				});
//				PageView.this.caterogy_giavi.addItem(item);
//			}
//			PageView.this.caterogy_giavi.setVisible(true);
//
//		}
//
//		@Override
//		public void failed(Throwable t) {
//		}
//
//		@Override
//		public void cancelled() {
//		}
//
//	}
//
//}
