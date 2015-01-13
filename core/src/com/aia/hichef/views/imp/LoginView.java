/**
 * 
 */
package com.aia.hichef.views.imp;

import java.util.Map;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.CommonProcess;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.GalleryViewHorizontal;
import com.aia.hichef.components.PointView;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.Direct;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.FocusListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.FacebookConnector.OnLoginListener;
import com.aia.hichef.networks.Request;
import com.aia.hichef.utils.AppPreference;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * @author Administrator
 * 
 */
public class LoginView extends View {
	public String			parentView;
	public Table			topBar;
	public Table			content;
	public float			BAR_HEIGHT	= 60;
	public Color			color_topbar;
	public Color			color_content;
	public Image			back, imgFood;
	Label					title, register, dki;
	LabelStyle				style;
	GalleryViewHorizontal	galleryViewHorizontal;
	public Table			tbloginFb;
	public Table			tbloginGg;
	public Table			tbRegister;

	public boolean			connect		= false;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public LoginView buildInfo(String parentView, String name, Rectangle bound) {
		this.parentView = parentView;
		this.name = name;
		setName(name);
		setBounds(bound.x, bound.y, bound.width, bound.height);

		color_topbar = new Color(200 / 255f, 0 / 255f, 0 / 255f, 1f);
		color_content = new Color(255 / 255f, 255 / 255f, 255 / 255f, 1f);

		content = new Table();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_content)));
		content.setSize(bound.getWidth(), bound.height - BAR_HEIGHT);
		content.setPosition(0, 0);

		topBar = new Table();
		topBar.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, color_topbar)));
		topBar.setSize(bound.getWidth(), BAR_HEIGHT);
		topBar.setPosition(0, bound.height - BAR_HEIGHT);

		addActor(content);
		addActor(topBar);
		return this;
	}

	public LoginView buildComponent() {
		buildTopBar();
		buildContent();
		setPosition(Constants.WIDTH_SCREEN + 2, 0);
		setVisible(false);
		setTouchable(Touchable.enabled);
		return this;
	}

	int			time			= 0;
	int			index			= 0;
	PointView	pointView;

	boolean		callUpdateInfo	= false;

	@Override
	public void update() {
		super.update();
		pointView.update();
		if (time < 100) {
			time++;
			if (time == 99) {
				index = CommonProcess.getNext(index, 0, 2);
				galleryViewHorizontal.pages.focusOnPage(index);
				time = 0;
				pointView.setIndex(index);
			}
		}

		if (AppPreference.instance._login && callUpdateInfo) {
			switchView();
			callUpdateInfo = false;
		}

	}

	boolean	switchView	= false;

	public void switchView() {
		if (!switchView) {
			UserProfile viewProfile = new UserProfile();
			viewProfile.build(getStage(), _viewController);
			viewProfile.buildInfo(
					StringSystem.VIEW_HOME,
					StringSystem.VIEW_USER_INFOR,
					new Rectangle(0, 0, Constants.WIDTH_SCREEN,
							Constants.HEIGHT_SCREEN)).buildComponent();
			_viewController.addView(viewProfile);
			viewProfile.show();
			switchView = true;
		}
	}

	@Override
	public void show() {
		setVisible(false);
		clearActions();
		toFront();
		Action action = Actions.sequence(
				Actions.moveTo(Constants.WIDTH_SCREEN + 2, 0),
				Actions.visible(true),
				Actions.moveTo(0, 0, .2f, Interpolation.linear),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						IViews view = _viewController
								.getView(StringSystem.VIEW_MAIN_MENU);
						if (view.getViewState() == ViewState.SHOW) {
							view.hide();
						}
					}
				}));
		addAction(action);
		setVisible(true);
		super.show();
	}

	@Override
	public void hide() {
		clearActions();
		Action action = Actions.sequence(Actions.moveTo(
				Constants.WIDTH_SCREEN + 2, getY(), .2f, Interpolation.linear),
				Actions.visible(false));
		addAction(action);
		super.hide();
	}

	@Override
	public void destroyComponent() {

		super.destroyComponent();
	}

	public void onBack() {
		back();
	}

	@Override
	public void back() {
		clearActions();
		Action action = Actions.sequence(Actions.moveTo(
				Constants.WIDTH_SCREEN + 2, getY(), .2f, Interpolation.linear),
				Actions.visible(false), Actions.run(new Runnable() {

					@Override
					public void run() {
						_viewController.getView(parentView).show();
						_viewController.removeView(getName());
					}
				}));
		addAction(action);
	}

	void buildTopBar() {
		topBar.left().align(Align.center);
		topBar.defaults().width(BAR_HEIGHT).height(BAR_HEIGHT);
		topBar.setTouchable(Touchable.enabled);
		topBar.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (x < 80) {
					back.setColor(back.getColor().r, back.getColor().g,
							back.getColor().b, 0.4f);
					return true;
				}
				return false;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 1f);
				onBack();
			}

		});
		back = new Image(Assets.instance.uiA.reg_back);
		back.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 0.4f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				back.setColor(back.getColor().r, back.getColor().g,
						back.getColor().b, 1f);
				onBack();
			}
		});
		topBar.add(back).width(30).height(30).padLeft(20);

		style = new LabelStyle(Assets.instance.fontFactory.getMedium20(),
				new Color(255 / 255f, 255 / 255f, 255 / 255f, 1));
		title = new Label("Đăng Nhập", style);
		title.setAlignment(Align.center);
		topBar.add(title).expandX().fillX().padRight(40);
	}

	public void buildContent() {

		Table foods = new Table();
		foods.setWidth(Constants.WIDTH_SCREEN);
		foods.setTouchable(Touchable.disabled);
		imgFood = new Image(new Texture(Gdx.files.internal("Img/login1.png")));
		Image imgFood2 = new Image(new Texture(
				Gdx.files.internal("Img/login2.png")));
		Image imgFood3 = new Image(new Texture(
				Gdx.files.internal("Img/login3.png")));

		galleryViewHorizontal = new GalleryViewHorizontal(foods, 1);
		galleryViewHorizontal.pages.setTouchable(Touchable.disabled);
		Table login1 = galleryViewHorizontal.newPage();
		login1.add(imgFood).center().expand().width(content.getWidth() - 40)
				.pad(20);
		Table login2 = galleryViewHorizontal.newPage();
		login2.add(imgFood2).center().expand().width(content.getWidth() - 40)
				.pad(20);
		Table login3 = galleryViewHorizontal.newPage();
		login3.add(imgFood3).center().expand().width(content.getWidth() - 40)
				.pad(20);
		galleryViewHorizontal.pages.focusOnPage(0);

		pointView = new PointView(foods, 3, Direct.LEFT, new FocusPage());
		pointView.build();

		tbloginFb = new Table();
		NinePatch ninePatch = new NinePatch(
				Assets.instance.uiM.bg_icon_caterogy, 6, 6, 6, 6);
		ninePatch.setColor(new Color(66 / 255f, 101 / 255f, 156 / 255f, 1f));
		tbloginFb.setBackground(new NinePatchDrawable(ninePatch));
		tbloginFb.setSize(360, 50);
		Label labelLoginFace = new Label("Đăng Nhập Bằng Facebook ", style);
		labelLoginFace.setTouchable(Touchable.disabled);
		tbloginFb.add(labelLoginFace).center().expand();

		tbloginGg = new Table();
		NinePatch ninePatchGg = new NinePatch(
				Assets.instance.uiM.bg_icon_caterogy, 6, 6, 6, 6);
		ninePatchGg.setColor(Color.RED);
		tbloginGg.setBackground(new NinePatchDrawable(ninePatchGg));
		tbloginGg.setSize(360, 50);
		Label labelLoginGg = new Label("Đăng Nhập Bằng Google + ", style);
		labelLoginGg.setTouchable(Touchable.disabled);
		tbloginGg.add(labelLoginGg).center().expand();

		LabelStyle styleLight = new LabelStyle(
				Assets.instance.fontFactory.getLight20(), Color.BLACK);
		register = new Label("Bạn chưa có tài khoản ?", styleLight);
		register.setTouchable(Touchable.disabled);
		LabelStyle styleBold = new LabelStyle(
				Assets.instance.fontFactory.getBold20(), Color.RED);
		dki = new Label(" ĐĂNG KÍ ", styleBold);

		Table tbDescription = new Table();
		tbDescription.setSize(Constants.WIDTH_SCREEN, 60);
		Label des = new Label("Để sử dụng chức năng này bạn cần đăng nhập ! ",
				styleLight);
		des.setWidth(tbDescription.getWidth() - 80);
		des.setX(40);
		des.setWrap(true);
		des.setAlignment(Align.left);
		tbDescription.add(des).padLeft(40);

		tbRegister = new Table();
		NinePatch ninePatchRg = new NinePatch(Assets.instance.uiM.bg_ninepatch);
		tbRegister.setBackground(new NinePatchDrawable(ninePatchRg));
		tbRegister.setSize(360, 60);
		tbRegister.add(register).center().expand();
		tbRegister.add(dki).center().width(80);

		content.add(foods).center().expand();
		content.row();
		content.add(tbDescription).height(60).expandX().left().padBottom(40);
		content.row();
		content.add(tbloginFb).center().height(tbloginFb.getHeight() + 20)
				.width(tbloginFb.getWidth()).pad(10);
		content.row();
		content.add(tbRegister).center().height(tbRegister.getHeight() + 20)
				.width(tbRegister.getWidth()).pad(10);
		buildListener();
	}

	public void buildListener() {
		dki.addListener(new ClickListener() {

		});
		tbloginFb.setTouchable(Touchable.enabled);
		tbloginFb.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!connect) {
					tbloginFb.setScale(0.9f, 0.9f);
					tbloginFb.setColor(tbloginFb.getColor().r,
							tbloginFb.getColor().g, tbloginFb.getColor().b, .6f);
				}
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				tbloginFb.setScale(1f, 1f);
				tbloginFb.setColor(tbloginFb.getColor().r,
						tbloginFb.getColor().g, tbloginFb.getColor().b, 1f);
				if (!connect) {
					_viewController.getFacebookConnector()
							.login(_loginListener);
					event.stop();
					connect = true;
				}
			}
		});

		tbloginGg.addListener(new ClickListener() {
		});
	}

	OnLoginListener			_loginListener	= new OnLoginListener() {

												@Override
												public void onError() {
													Toast.makeText(getStage(),
															"Login Error",
															Toast.LENGTH_LONG);
													connect = false;

												}

												@Override
												public void onComplete(
														Map<String, String> userInfo) {
													AppPreference.instance._facebookID = userInfo
															.get("id");
													AppPreference.instance._title = userInfo
															.get("name");
													AppPreference.instance._avatar = userInfo
															.get("avatar");
													AppPreference.instance._email = userInfo
															.get(StringSystem._EMAIL);
													AppPreference.instance._login = true;
													AppPreference.instance
															.saveUserInformation();
													Request.getInstance()
															.userFaceBookLogin(
																	AppPreference.instance._facebookID,
																	AppPreference.instance._title,
																	AppPreference.instance._avatar,
																	AppPreference.instance._gender,
																	AppPreference.instance._email,
																	AppPreference.instance._phone,
																	AppPreference.instance._deviceID,
																	AppPreference.instance._deviceName,
																	AppPreference.instance._version,
																	listener);

												}
											};

	HttpResponseListener	listener		= new HttpResponseListener() {

												@Override
												public void handleHttpResponse(
														HttpResponse httpResponse) {
													String responseData = httpResponse
															.getResultAsString();
													JsonValue value = (new JsonReader())
															.parse(responseData);
													String result = value
															.getString(ExtParamsKey.RESULT);
													if (result
															.equalsIgnoreCase("failed")) {
														System.out
																.println("Request Comfirm Login Failed");
													} else {
														System.out
																.println("Request Comfirm Login succes");
														AppPreference.instance._userID = value
																.getInt(ExtParamsKey.ID);
														AppPreference.instance._sessionID = value
																.getInt(ExtParamsKey.SESSION_ID);
													}
													_viewController
															.getGameParent()
															.saveDeviceInfo();
													callUpdateInfo = true;
												}

												@Override
												public void failed(Throwable t) {
													System.out
															.println("+++++++++++++++++++++++++++++++ Failed +++++++++++++");
												}

												@Override
												public void cancelled() {

												}
											};

	public class FocusPage implements FocusListener {

		@Override
		public void touchIndex(int index) {
			galleryViewHorizontal.pages.focusOnPage(index);
		}

	}

}
