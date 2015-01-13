package com.aia.hichef.ui;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.ImageDownloader;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.imp.LoginView;
import com.aia.hichef.views.imp.UploadFoodView;
import com.aia.hichef.views.imp.UserProfile;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuList extends ListView {
	ItemMenu	avaItem;
	String		userName	= "";
	Image		iconava;
	boolean		isLogin		= false;
	Label		lbName;

	public MenuList(IViewController _viewController, Table table, float width,
			float height) {
		super(_viewController, table);
		setBounds(0, 0, width, height);

		isLogin = AppPreference.instance._login;
		iconava = new Image(Assets.instance.uiP.tapFace);
		iconava.setSize(100, 100);
		if (isLogin) {
			System.out.println(" Da login ");
			userName = AppPreference.instance._title;
			String url = AppPreference.instance._avatar;
			ImageDownloader.getInstance().download(url, iconava);
		} else {
			System.out.println(" chua login  ");
			userName = "User Name";
		}

		LabelStyle style = new LabelStyle();
		style.font = Assets.instance.fontFactory.getLight20();
		style.fontColor = Color.RED;

		// create item avatar
		creatAvatarItem(style);
		// create item function
		creatFuntion(style);
		// create item function player
		creatFuntionPlayer(style);
		setTransform(true);
		this.debug();
	}	

	private void creatAvatarItem(LabelStyle style) {
		avaItem = new ItemMenu();
		avaItem.setSize(getWidth(), 130);

		iconava.setPosition(10, avaItem.getHeight() / 2 - iconava.getHeight()
				/ 2);
		avaItem.addActor(iconava);
		lbName = new Label(userName, style);
		lbName.setPosition(iconava.getX() + iconava.getWidth() + 20,
				avaItem.getY() + avaItem.getHeight() / 2 - lbName.getHeight()
						/ 2);
		avaItem.addActor(lbName);
		avaItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				avaItem.addAction(Actions.sequence(Actions.run(new Runnable() {

					@Override
					public void run() {
						IViews view = _viewController
								.getView(StringSystem.VIEW_MAIN_MENU);
						if (view.getViewState() == ViewState.SHOW) {
							view.hide();
						}
					}
				}), Actions.run(new Runnable() {

					@Override
					public void run() {
						onShowAvatarView();
					}
				})));

			}
		});
		this.addItem(avaItem);
		this.addLine();
	}

	private void creatFuntion(LabelStyle style) {
		ItemMenu cnlItem = getItem(
				new Image(Assets.instance.uiP.icono_recetas),
				"Chọn nguyên liệu", this.getWidth(), 64, style);
		cnlItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.SHOW) {
					view.hide(AnimationType.NONE, null);
				}
			}
		});
		this.addItem(cnlItem);

		ItemMenu cmItem = getItem(new Image(
				Assets.instance.uiP.icono_actualidad), "Chọn món",
				this.getWidth(), 64, style);
		cmItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.SHOW) {
					view.hide(AnimationType.NONE, null);
				}
			}
		});
		this.addItem(cmItem);

		ItemMenu addItem = getItem(new Image(
				Assets.instance.uiP.icono_actualidad), "Thêm món",
				this.getWidth(), 64, style);
		addItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.SHOW) {
					view.hide(AnimationType.NONE, null);
				}
				onAddReciepe();
			}
		});
		this.addItem(addItem);
		this.addLine();
	}

	private void onAddReciepe() {
//		 UploadViewAnimation uploadReciepe = new UploadViewAnimation();
//		 uploadReciepe.build(getStage(), getViewController());
//		 uploadReciepe.buildInfo(
//		 getName(),
//		 StringSystem.VIEW_UPLOAD_RECIEPE,
//		 new Rectangle(0, 0, Constants.WIDTH_SCREEN,
//		 Constants.HEIGHT_SCREEN)).buildComponent();
//		 _viewController.addView(uploadReciepe);
//		 uploadReciepe.show();
		UploadFoodView uploadReciepe = new UploadFoodView();
		uploadReciepe.build(getStage(), getViewController());
		uploadReciepe.buildInfo(
				getName(),
				StringSystem.VIEW_UPLOAD_RECIEPE,
				new Rectangle(0, 0, Constants.WIDTH_SCREEN,
						Constants.HEIGHT_SCREEN)).buildComponent();
		_viewController.addView(uploadReciepe);
		uploadReciepe.show();

	}

	private void creatFuntionPlayer(LabelStyle style) {
		ItemMenu infoItem = getItem(new Image(Assets.instance.uiP.icono_info),
				"Thông tin", getWidth(), 64, style);
		infoItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.SHOW) {
					view.hide(AnimationType.NONE, null);
				}
			}
		});
		this.addItem(infoItem);

		ItemMenu settingItem = getItem(new Image(
				Assets.instance.uiP.icono_settings), "Cài đặt", getWidth(), 64,
				style);
		settingItem.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				IViews view = _viewController
						.getView(StringSystem.VIEW_MAIN_MENU);
				if (view.getViewState() == ViewState.SHOW) {
					view.hide(AnimationType.NONE, null);
				}
			}
		});
		this.addItem(settingItem);
	}

	public void onShowAvatarView() {
		if (!isLogin) {
			LoginView loginView = new LoginView();
			loginView.build(getStage(), _viewController);
			loginView.buildInfo(
					StringSystem.VIEW_HOME,
					StringSystem.VIEW_LOGIN,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(loginView);
			loginView.show();
		} else {
			UserProfile viewProfile = new UserProfile();
			viewProfile.build(getStage(), _viewController);
			viewProfile.buildInfo(
					StringSystem.VIEW_HOME,
					StringSystem.VIEW_USER_INFOR,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(viewProfile);
			viewProfile.show();
		}
	}

	private ItemMenu getItem(Image image, String title, float width,
			float height, LabelStyle style) {
		ItemMenu item = new ItemMenu();
		item.setSize(width, height);
		image.setPosition(item.getX() + 30, item.getY() + item.getHeight() / 2
				- image.getHeight() / 2);
		item.addActor(image);
		Label l = new Label(title, style);
		l.setPosition(image.getX() + image.getWidth() + 20, image.getY()
				+ image.getHeight() / 2 - l.getHeight() / 2);
		item.addActor(l);
		return item;
	}

	class ItemMenu extends Item {
		final Image	bg;

		public ItemMenu() {
			bg = new Image(new NinePatch(Assets.instance.uiP.transparent));
			bg.setSize(getWidth(), getHeight());
			bg.getColor().a = 0;
			addActor(bg);
		}

		@Override
		public void setSize(float width, float height) {
			super.setSize(width, height);
			bg.setSize(width, height);
		}

		@Override
		public void setListener(final ItemListener listener) {
			addListener(new ClickListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 1;
					return super.touchDown(event, x, y, pointer, button);
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 0;
					listener.onItemClick();
					super.touchUp(event, x, y, pointer, button);

				}

			});
		}
	}

	boolean	download	= false;

	public void refresh() {
		isLogin = AppPreference.instance._login;
		if (isLogin) {
			System.out.println(" Da login ");
			userName = AppPreference.instance._title;
			String url = AppPreference.instance._avatar;
			lbName.setText(userName);
			if (!download) {
				ImageDownloader.getInstance().download(url, iconava);
				download = true;
			}
		} else {
			System.out.println(" chua login  ");
			userName = "User Name";
			lbName.setText(userName);
		}
	}

}
