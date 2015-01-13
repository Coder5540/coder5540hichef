//package com.aia.hichef.views;
//
//import com.aia.hichef.enums.AnimationType;
//import com.aia.hichef.enums.ViewState;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//
//public interface IView {
//
//	/*
//	 * this method will be called to initial a view and adding it into a stage.
//	 * in the constructor of a view, we don't need to initial it
//	 */
//	public void build(  Stage stage, IViewController viewController);
//
//	/* this method is call when we need to update all actor in this view */
//	public void update();
//
//	public void show(AnimationType showType);
//
//	public void hide(AnimationType hideType);
//
//	public int getZXndex();
//
//	public void setZIndex(int zIndex);
//
//	public void setViewState(ViewState state);
//
//	public ViewState getViewState();
//
//	public void setName(String name);
//
//	public String getName();
//
//	public void setSize(float width, float height);
//
//	public void setPosition(float x, float y);
//
//	public Rectangle getBound();
//
//	/*
//	 * this method will be called if we don't need to use it anymore but we
//	 * still want to hold it on our stage. all the component including in this
//	 * view will be remove
//	 */
//	public void destroyComponent();
//
//	public IViewController getViewController();
//
//	public void back();
//
//}
