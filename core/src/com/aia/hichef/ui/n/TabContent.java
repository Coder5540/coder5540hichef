package com.aia.hichef.ui.n;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TabContent implements TabContentMove {
	public ListView content;
	Image loading;
	Image background;
	boolean isLoading = true;
	private Label error;
	private boolean isError = false;

	public TabContent(ListView view) {
		content = view;
		setTouchable(false);

		loading = new Image(Assets.instance.uiN.loading);
		background = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
				Color.WHITE));
		background.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2);
		loading.addAction(Actions.forever(Actions.rotateBy(40, 0.1f)));
		LabelStyle style = new LabelStyle(
				Assets.instance.fontFactory.getLight20(), Color.RED);
		error = new Label("Chạm để tải lại", style);
		background.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (isError) {
					isError = false;
					error.setVisible(false);
					onReload(false);
					showLoading();
				}
			}
		});
		loading.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (isError) {
					isError = false;
					error.setVisible(false);
					onReload(false);
					showLoading();
				}
			}
		});
		error.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (isError) {
					isError = false;
					error.setVisible(false);
					onReload(false);
					showLoading();
				}
			}
		});

		content.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (content.getScrollY() < 0 && content.getScrollY() > -10
						&& !isReload) {
					moveToTop();
				}
				if (content.getScrollY() <= -78) {
					isReload = true;
				}
				if (content.getScrollY() > content.getMaxY() + 5
						&& !isLoadingMore) {
					isLoadMore = true;
				}
				super.pan(event, x, y, deltaX, deltaY);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (isReload) {
					onReload(false);
					showLoading();
					isReload = false;
				}
				if (isLoadMore) {
					onReload(true);
					isLoadMore = false;
					isLoadingMore = true;
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		content.setOverscroll(false, true);
	}

	public void addLoading(ListView farther, float x) {
		background.setX(x);
		loading.setPosition(
				Constants.WIDTH_SCREEN / 2 - loading.getWidth() / 2,
				Constants.HEIGHT_SCREEN / 2 - loading.getHeight() / 2);
		loading.setX(loading.getX() + x);
		error.setPosition(
				loading.getX() + loading.getWidth() - error.getWidth() / 2,
				loading.getY() + loading.getHeight() + 5);
		farther.getParent().addActor(background);
		farther.getParent().addActor(loading);
		farther.getParent().addActor(error);
		error.setVisible(false);
		loading.toBack();
		error.toBack();
		background.toBack();
	}

	public void closeLoading() {
		loading.setVisible(false);
		error.setVisible(false);
		background.setVisible(false);
		isMoveTotop = false;
		moveToTop();
	}

	boolean isMoveTotop = false, isReload = false, isLoadMore = false;
	public boolean isLoadingMore = false;

	public void moveToTop() {
		loading.setY(Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR
				- Constants.HEIGHT_TABBAR - loading.getHeight() - 15);
		loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2);
		loading.clearActions();
		loading.addAction(Actions.forever(Actions.rotateBy(40, 0.1f)));
		loading.setVisible(true);
		isMoveTotop = true;
	}

	public void showLoading() {
		background.setVisible(true);
		loading.setScale(1);
		loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2);
		loading.setY(Constants.HEIGHT_SCREEN / 2 - loading.getHeight() / 2);
		loading.addAction(Actions.forever(Actions.rotateBy(40, 0.1f)));
		loading.setVisible(true);
		error.setVisible(false);
	}

	public void doError() {
		isError = true;
		loading.clearActions();
		loading.addAction(Actions.sequence(
				Actions.parallel(Actions.scaleTo(0.5f, 0.5f, 0.1f)),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						error.setVisible(true);
						error.setPosition(loading.getX() + loading.getWidth()
								/ 2 - error.getWidth() / 2, loading.getY()
								+ loading.getHeight() + 5);
					}
				})));
	}

	public void onReload(boolean isLoadMore) {

	};

	@Override
	public void setTouchable(boolean isPan) {
		content.isCanTouch = isPan;
	}

	@Override
	public boolean isTouchable() {
		return content.isCanTouch;
	}

}
