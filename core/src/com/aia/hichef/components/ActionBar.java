package com.aia.hichef.components;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.imp.SearchViewV2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ActionBar extends Table {

	public IViewController	_viewController;

	// ////////Item Action Bar//////////////
	private ActionBarItem	mainMenu;
	private Image			iconMenu;
	private Image			icon;
	private Label			title;
	private ActionBarItem	itemSearch;
	private ActionBarItem	itemSubMenu;
	private ActionBarItem	itemBack;

	public ActionBar(IViewController _viewController, Rectangle bound) {
		this._viewController = _viewController;
		setBounds(bound.x, bound.y, bound.width, bound.height);
		initItem();
	}

	private void initItem() {
		mainMenu = new ActionBarItem(new ItemListener() {
			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.HIDE) {
					((Actor) view).toFront();
					view.show(AnimationType.NONE, null);
				} else {
					view.hide(AnimationType.NONE, null);
				}
			}
		});

		iconMenu = new Image(Assets.instance.uiP.iconMenu);
		icon = new Image(Assets.instance.uiP.icon);
		title = new Label(StringSystem.APP_NAME, new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), Color.WHITE));

		mainMenu.addToItem(iconMenu);
		mainMenu.addToItem(icon);
		mainMenu.addToItem(title);
		mainMenu.setWidth(iconMenu.getWidth() + icon.getWidth()
				+ title.getWidth());

		itemSearch = new ActionBarItem(new ItemListener() {

			@Override
			public void onItemClick() {
				// SearchView searhView = (SearchView) _viewController
				// .getView(StringSystem.VIEW_SEARCH);
				// searhView.tfSearch.getOnscreenKeyboard().show(true);
				// getStage().setKeyboardFocus(searhView.tfSearch);
				// searhView.show(null);
				SearchViewV2 searhView = (SearchViewV2) _viewController
						.getView(StringSystem.VIEW_SEARCH);
				searhView.registerKeyBoard();
				searhView.show();
			}
		});

		Image iconSearch = new Image(Assets.instance.uiP.iconSearch);
		itemSearch.addToItem(iconSearch);
		itemSearch.setWidth(iconSearch.getWidth());

		itemSubMenu = new ActionBarItem(new ItemListener() {

			@Override
			public void onItemClick() {

			}
		});

		Image iconSubMenu = new Image(Assets.instance.uiP.iconSubMenu);
		itemSubMenu.addToItem(iconSubMenu);
		itemSubMenu.setWidth(iconSubMenu.getWidth());

		itemBack = new ActionBarItem(new ItemListener() {

			@Override
			public void onItemClick() {

			}
		});
		Image iconBack = new Image(Assets.instance.uiP.iconBack);
		itemBack.addToItem(iconBack);
		itemBack.setWidth(iconBack.getWidth());
		itemBack.setVisible(false);

		itemSubMenu.setX(Constants.WIDTH_SCREEN - itemSubMenu.getWidth());
		itemSearch.setX(itemSubMenu.getX() - itemSearch.getWidth());

		addItem(mainMenu);
		addItem(itemBack);
		addItem(itemSearch);
		addItem(itemSubMenu);
	}

	public void setTitle(String title) {
		this.title.setText(title);
		mainMenu.setWidth(iconMenu.getWidth() + icon.getWidth()
				+ this.title.getStyle().font.getBounds(title).width);
	}

	public void addItem(ActionBarItem item) {
		this.addActor(item);
	}

}
