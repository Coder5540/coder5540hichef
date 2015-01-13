package com.aia.hichef.views;

import utils.download.DownloadManager;
import utils.download.ImgDownloader;

import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.OnCompleteListener;
import com.aia.hichef.screens.TraceView;
import com.aia.hichef.utils.Log;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Administrator
 * 
 */
public class View extends Table implements IViews {
	public Stage			_stage;
	public IViewController	_viewController;
	public ViewState		state;
	public String			name;
	public ImgDownloader	downloader;

	public View() {
		super();
		setUp();
	}

	public View(Skin skin) {
		super(skin);
		setUp();
	}

	private void setUp() {
		state = ViewState.INITIAL;
		name = "";
		setClip(true);
		setCullingArea(new Rectangle(0, 0, Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN));
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		this._stage = stage;
		_viewController = viewController;
		stage.addActor(this);
	}

	@Override
	public void show(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {
		// if (newViewAnimation == AnimationType.NONE
		// && oldViewAnimationType == AnimationType.NONE) {
		// if (newViewCompleteListener != null)
		// newViewCompleteListener.onComplete();
		// if (oldViewCompleteListener != null)
		// oldViewCompleteListener.onComplete();
		// }
		show();
	}

	@Override
	public void show(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {
		// if (newViewAnimation == AnimationType.NONE) {
		// if (newViewCompleteListener != null)
		// newViewCompleteListener.onComplete();
		// }
		show();
	}

	@Override
	public void hide(AnimationType newViewAnimation,
			AnimationType oldViewAnimationType,
			OnCompleteListener newViewCompleteListener,
			OnCompleteListener oldViewCompleteListener) {
		// if (newViewAnimation == AnimationType.NONE
		// && oldViewAnimationType == AnimationType.NONE) {
		// if (newViewCompleteListener != null)
		// newViewCompleteListener.onComplete();
		// if (oldViewCompleteListener != null)
		// oldViewCompleteListener.onComplete();
		// }
		hide();
	}

	@Override
	public void hide(AnimationType newViewAnimation,
			OnCompleteListener newViewCompleteListener) {
		// if (newViewAnimation == AnimationType.NONE) {
		// if (newViewCompleteListener != null)
		// newViewCompleteListener.onComplete();
		// }
		hide();
	}

	@Override
	public void show() {
		_viewController.setCurrentView(this);
		toFront();
		setTouchable(Touchable.enabled);
		setViewState(ViewState.SHOW);
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
		setTouchable(Touchable.disabled);
		TraceView.instance.removeView(this.getName());
	}

	@Override
	public void update() {

	}

	@Override
	public void act(float delta) {
		if (getViewState() != ViewState.PAUSE) {
			super.act(delta);
		} else {
			setTouchable(Touchable.disabled);
		}
	}

	@Override
	public void setViewState(ViewState state) {
		this.state = state;
	}

	@Override
	public ViewState getViewState() {
		return state;
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void destroyComponent() {
		// =============== destroy all of your object here ==========
		this.clear();
		_stage.getActors().removeValue(this, false);
	}

	@Override
	public IViewController getViewController() {
		return _viewController;
	}

	@Override
	public boolean onLeftSide() {
		return false;
	}

	@Override
	public boolean onRightSide() {
		return false;
	}

	
	
	@Override
	public ImgDownloader getDownLoader() {
		if(downloader == null){
			downloader = DownloadManager.getInstance().getDownLoader(getName());
			downloader.setGameCore(getViewController().getGameParent());
		}
		return downloader;
	}

	@Override
	public void back() {
		// TraceSystem.backView();
		DownloadManager.getInstance().removeDownLoader(getName());
		hide();
	}
}
