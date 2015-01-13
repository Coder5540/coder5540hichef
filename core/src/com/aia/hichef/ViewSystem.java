//package com.aia.hichef;
//
//import java.util.Comparator;
//
//import com.aia.hichef.attribute.StringSystem;
//import com.aia.hichef.enums.AnimationType;
//import com.aia.hichef.enums.Constants;
//import com.aia.hichef.enums.ViewState;
//import com.aia.hichef.log.Debug;
//import com.aia.hichef.networks.FacebookConnector;
//import com.aia.hichef.screenhelper.GameCore;
//import com.aia.hichef.screens.TraceView;
//import com.aia.hichef.views.IViewController;
//import com.aia.hichef.views.IViews;
//import com.aia.hichef.views.imp.ActionBarView;
//import com.aia.hichef.views.imp.HomeView;
//import com.aia.hichef.views.imp.MainMenuView;
//import com.aia.hichef.views.imp.SearchViewV2;
//import com.aia.hichef.views.imp.SubMenuView;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.utils.Array;
//
//public class ViewSystem implements IViewController {
//
//	public Stage				stage;
//	public Array<IView>			views;
//
//	public IView				currentView;
//	public static TraceView		traceView;
//	public FacebookConnector	facebookConnector;
//	private GameCore			_gameParent;
//
//	public ViewSystem(GameCore _gameParent) {
//		super();
//		this._gameParent = _gameParent;
//		traceView = new TraceView();
//	}
//
//	@Override
//	public void update(float delta) {
//		for (IView view : views) {
//			view.update();
//			if (view.getViewState() == ViewState.DISPOSE) {
//				removeView(view.getName());
//			}
//		}
//	}
//
//	public void build(Stage stage) {
//		this.stage = stage;
//		views = new Array<IView>();
//
//		ActionBarView actionBarView = new ActionBarView();
//		actionBarView.build(stage, this);
//		actionBarView.buildName(StringSystem.VIEW_ACTION_BAR)
//				.buildBound(new Rectangle(0, Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR, Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR))
//				.buildComponent().setZIndex(500);
//
//		MainMenuView mainMenuView = new MainMenuView();
//		mainMenuView.build(stage, this);
//		mainMenuView.buildComponent(StringSystem.VIEW_MAIN_MENU,
//				new Rectangle(0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR)).setZIndex(600);
//
//		HomeView homeView = new HomeView();
//		homeView.build(stage, this);
//		homeView.buildName(StringSystem.VIEW_HOME)
//				.buildBound(new Rectangle(0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR)).buildComponent();
//
//		SearchViewV2 searchView = new SearchViewV2();
//		searchView.build(stage, this);
//		searchView.buildName(StringSystem.VIEW_SEARCH).buildBound(new Rectangle(0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN)).buildComponent();
//
//		SubMenuView subMenuView = new SubMenuView();
//		subMenuView.build(stage, this);
//		subMenuView.buileName(StringSystem.VIEW_SUBMENU)
//				.buildBound(new Rectangle(0, 0, Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR)).buildComponent();
//
//		addView(actionBarView);
//		addView(mainMenuView);
//		addView(searchView);
//		addView(homeView);
//		addView(subMenuView);
//
//		focusView(StringSystem.VIEW_HOME);
//		actionBarView.setVisible(true);
//	}
//
//	@Override
//	public boolean isContainView(String name) {
//		if (avaiable()) {
//			for (IView view : views) {
//				if (view.getName().equalsIgnoreCase(name))
//					return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public void focusView(String name) {
//
//		if (!isContainView(name)) {
//			Debug.console("Doesnt contain view " + name);
//			return;
//		}
//		Debug.console("Focus view " + name);
//
//		IView view = getView(name);
//		if (view != null) {
//			if (currentView != null) {
//				this.currentView.hide(AnimationType.FADE);
//				((Actor) currentView).setTouchable(Touchable.disabled);
//			}
//			// ((Actor) getView(StringSystem.VIEW_ACTION_BAR)).toFront();
//			view.show(null);
//			((Actor) view).setTouchable(Touchable.childrenOnly);
//			((Actor) view).toFront();
//			this.currentView = view;
//			traceView.addViewToTrace(name);
//		}
//	}
//
//	@Override
//	public void focusView(String name, AnimationType animationType) {
//
//		if (!isContainView(name)) {
//			Debug.console("Doesnt contain view " + name);
//			return;
//		}
//		Debug.console("Focus view " + name);
//
//		IView view = getView(name);
//		if (view != null) {
//			if (currentView != null) {
//				this.currentView.hide(AnimationType.FADE);
//				((Actor) currentView).setTouchable(Touchable.disabled);
//			}
//			// ((Actor) getView(StringSystem.VIEW_ACTION_BAR)).toFront();
//			view.show(animationType);
//			((Actor) view).setTouchable(Touchable.childrenOnly);
//			((Actor) view).toFront();
//			this.currentView = view;
//			traceView.addViewToTrace(name);
//		}
//	}
//
//	@Override
//	public void addView(IView view) {
//		if (!avaiable())
//			return;
//		views.add(view);
//		// sortView();
//	}
//
//	@Override
//	public void removeView(String name) {
//		if (!avaiable())
//			return;
//		IView view = getView(name);
//		if (view == null)
//			return;
//		view.destroyComponent();
//		views.removeValue(view, false);
//	}
//
//	@Override
//	public void hideView(String name) {
//		if (!avaiable())
//			return;
//		IView view = getView(name);
//		if (view != null)
//			view.hide(null);
//	}
//
//	@Override
//	public IView getView(String name) {
//		for (IView view : views) {
//			if (view.getName().equalsIgnoreCase(name)) {
//				return view;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Array<IView> getViews() {
//		if (avaiable())
//			return views;
//		return null;
//	}
//
//	@Override
//	public void sortView() {
//		views.sort(new MyComparator());
//	}
//
//	public boolean avaiable() {
//		return views != null && stage != null;
//	}
//
//	public class MyComparator implements Comparator<IView> {
//		@Override
//		public int compare(IView view1, IView view2) {
//			return (view2.getZXndex() - view1.getZXndex()) > 0 ? 1 : -1;
//		}
//	}
//
//	@Override
//	public IViews getCurrentView() {
//		return currentView;
//	}
//
//
//	@Override
//	public Stage getStage() {
//		return stage;
//	}
//
//	@Override
//	public void backView() {
//		String previous = traceView.getLastView();
//		if (!previous.equalsIgnoreCase("")) {
//			focusView(previous);
//		}
//		// currentView.back();
//	}
//
//	public void setFacebookConnector(FacebookConnector facebookConnector) {
//		this.facebookConnector = facebookConnector;
//	}
//
//	public FacebookConnector getFacebookConnector() {
//		return facebookConnector;
//	}
//
//	@Override
//	public void setGameParent(GameCore gameParent) {
//		this._gameParent = gameParent;
//	}
//
//	@Override
//	public GameCore getGameParent() {
//		return _gameParent;
//	}
//
//}
