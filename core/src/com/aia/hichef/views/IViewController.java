package com.aia.hichef.views;

import com.aia.hichef.networks.FacebookConnector;
import com.aia.hichef.screenhelper.GameCore;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public interface IViewController {

	public void update(float delta);

	/* Check whether stage contain a view by name or not. */
	public boolean isContainView(String name);

	/* add a new view to this stage */
	public void addView(IViews view);

	/* remove a view from stage, it's not available anymore */
	public void removeView(String name);

	/*
	 * just hide a specific view. we put the view in a state of pause and then
	 * is hide
	 */
	public void hideView(String name);

	public IViews getView(String name);

	/* return all the view in this stage */
	public Array<IViews> getViews();

	/* this method sorted view by their index to draw to stage */
	public void sortView();

	public FacebookConnector getFacebookConnector();

	public IViews getCurrentView();

	public void setCurrentView(IViews view);

	public Stage getStage();

	public void backView();

	public void setGameParent(GameCore gameParent);

	public GameCore getGameParent();

}
