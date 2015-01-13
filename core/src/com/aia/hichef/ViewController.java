package com.aia.hichef;

import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.networks.FacebookConnector;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.imp.ActionBarView;
import com.aia.hichef.views.imp.HomeView;
import com.aia.hichef.views.imp.MainMenuView;
import com.aia.hichef.views.imp.SearchViewV2;
import com.aia.hichef.views.imp.SubMenuView;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class ViewController implements IViewController {
	public Stage				stage;
	public Array<IViews>		views;

	public IViews				currentView;
	public FacebookConnector	facebookConnector;
	private GameCore			_gameParent;

	public ViewController(GameCore _gameParent) {
		super();
		this._gameParent = _gameParent;
	}

	@Override
	public void update(float delta) {
		for (IViews view : views) {
			view.update();
			if (view.getViewState() == ViewState.DISPOSE) {
				removeView(view.getName());
			}
		}
	}

	public void build(Stage stage) {
		this.stage = stage;
		views = new Array<IViews>();

		ActionBarView actionBarView = new ActionBarView();
		actionBarView.build(stage, this);
		actionBarView
				.buildName(StringSystem.VIEW_ACTION_BAR)
				.buildBound(
						new Rectangle(0, Constants.HEIGHT_SCREEN
								- Constants.HEIGHT_ACTIONBAR,
								Constants.WIDTH_SCREEN,
								Constants.HEIGHT_ACTIONBAR)).buildComponent()
				.setZIndex(500);

		MainMenuView mainMenuView = new MainMenuView();
		mainMenuView.build(stage, this);
		mainMenuView.buildComponent(
				StringSystem.VIEW_MAIN_MENU,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR))
				.setZIndex(600);

		HomeView homeView = new HomeView();
		homeView.build(stage, this);
		homeView.buildName(StringSystem.VIEW_HOME)
				.buildBound(
						new Rectangle(0, 0, Constants.WIDTH_SCREEN,
								Constants.HEIGHT_SCREEN
										- Constants.HEIGHT_ACTIONBAR))
				.buildComponent();

		SearchViewV2 searchView = new SearchViewV2();
		searchView.build(stage, this);
		searchView
				.buildName(StringSystem.VIEW_SEARCH)
				.buildBound(
						new Rectangle(0, 0, Constants.WIDTH_SCREEN,
								Constants.HEIGHT_SCREEN)).buildComponent();

		SubMenuView subMenuView = new SubMenuView();
		subMenuView.build(stage, this);
		subMenuView
				.buileName(StringSystem.VIEW_SUBMENU)
				.buildBound(
						new Rectangle(0, 0, Constants.WIDTH_SCREEN,
								Constants.HEIGHT_SCREEN
										- Constants.HEIGHT_ACTIONBAR))
				.buildComponent();

		addView(actionBarView);
		addView(mainMenuView);
		addView(searchView);
		addView(subMenuView);
		addView(homeView);

		homeView.show();
		actionBarView.setVisible(true);
	}

	@Override
	public boolean isContainView(String name) {
		if (avaiable()) {
			for (IViews view : views) {
				if (view.getName().equalsIgnoreCase(name))
					return true;
			}
		}
		return false;
	}

	@Override
	public void addView(IViews view) {
		if (!avaiable())
			return;
		views.add(view);
		// sortView();
	}

	@Override
	public void removeView(String name) {
		if (!avaiable())
			return;
		IViews view = getView(name);
		if (view == null)
			return;
		view.destroyComponent();
		views.removeValue(view, false);
		stage.getActors().removeValue((Actor) view, true);

		IViews lastView = getView(TraceView.instance.getLastView());
		if (lastView != null) {
			lastView.setViewState(ViewState.SHOW);
			((Actor) lastView).setTouchable(Touchable.enabled);
		}
	}

	@Override
	public void hideView(String name) {
		if (!avaiable())
			return;
		IViews view = getView(name);
		if (view != null)
			view.hide(AnimationType.NONE, null);
	}

	// return the first view has name equal "name" in this container of views
	@Override
	public IViews getView(String name) {
		for (IViews view : views) {
			if (view.getName().equalsIgnoreCase(name)) {
				return view;
			}
		}
		return null;
	}

	@Override
	public Array<IViews> getViews() {
		if (avaiable())
			return views;
		return null;
	}

	public boolean avaiable() {
		return views != null && stage != null;
	}

	@Override
	public void backView() {
	}

	@Override
	public Stage getStage() {
		return stage;
	}

	public void setFacebookConnector(FacebookConnector facebookConnector) {
		this.facebookConnector = facebookConnector;
	}

	public FacebookConnector getFacebookConnector() {
		return facebookConnector;
	}

	@Override
	public void setGameParent(GameCore gameParent) {
		this._gameParent = gameParent;
	}

	@Override
	public GameCore getGameParent() {
		return _gameParent;
	}

	@Override
	public void sortView() {
	}

	@Override
	public IViews getCurrentView() {
		return currentView;
	}

	@Override
	public void setCurrentView(IViews view) {
		this.currentView = view;
		TraceView.instance.addViewToTrace(view.getName());
	}
}
