package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.Request;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class FilterCategoryView extends View {

	private Group content;

	private ListView listView;
	private ImageLabel btOk;
	public JsonValue respone;
	private int idChoiced = -1;

	public FilterCategoryView buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public FilterCategoryView buildComponent() {
		setName(StringSystem.VIEW_FILTERCATEGORY);
		Image bgTran = new Image(new NinePatch(Assets.instance.uiP.transparent));
		bgTran.setSize(getWidth(), getHeight());

		content = new Group();
		content.setBounds(50, 50, getWidth() - 100, getHeight() - 100);
		Image bgContent = new Image(new NinePatch(
				Assets.instance.uiP.ninepatch_white));
		Label title = new Label("Lọc theo danh mục", new LabelStyle(
				Assets.instance.fontFactory.getLight30(), Color.RED));
		Image spaceLine = new Image(new NinePatch(
				Assets.instance.uiP.ninepatch_gray));
		listView = new ListView(_viewController, new Table());

		LabelStyle lbStyle = new LabelStyle();
		lbStyle.font = Assets.instance.fontFactory.getLight20();
		lbStyle.fontColor = Color.BLACK;
		btOk = new ImageLabel("Bỏ qua", lbStyle, content.getWidth(), 60);
		btOk.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				hide();
			}
		});

		content.addActor(bgContent);
		content.addActor(spaceLine);
		content.addActor(title);
		content.addActor(listView);
		content.addActor(btOk);
		bgContent.setSize(content.getWidth(), content.getHeight());
		title.setPosition((content.getWidth() - title.getWidth()) / 2,
				content.getHeight() - 60 + (60 - title.getHeight()) / 2);
		spaceLine.setBounds(10, content.getHeight() - 60,
				content.getWidth() - 20, 4);
		listView.setBounds(0, 60, content.getWidth(),
				content.getHeight() - 63 - 60);

		this.addActor(bgTran);
		this.addActor(content);
		hide();
		return this;
	}

	public int getIdChoiced() {
		return idChoiced;
	}

	@Override
	public void update() {
		super.update();
		if (respone != null) {
			listView.removeAll();
			JsonValue content = respone.get(ExtParamsKey.CONTENT);
			for (int i = 0; i < content.size; i++) {
				JsonValue material = content.get(i);
				String title = material.getString(ExtParamsKey.TITLE);
				final int id = material.getInt(ExtParamsKey.ID);
				ItemFilterCategory newItem = new ItemFilterCategory(listView,
						title, id);
				newItem.setListener(new ItemListener() {

					@Override
					public void onItemClick() {
						idChoiced = id;
						hide();
					}
				});
				listView.addItem(newItem);
			}
			respone = null;
		}
	}

//	@Override
//	public void show(AnimationType showType) {
//		super.show(showType);
//		toFront();
//		setViewState(ViewState.SHOW);
//		setVisible(true);
//		content.setX(getWidth());
//		content.clearActions();
//		content.addAction(Actions.moveTo(50, content.getY(), 0.5f,
//				Interpolation.swingOut));
//		idChoiced = -1;
//		listView.removeAll();
//		Request.getInstance().getAllCategory(new GetAllCategory());
//		listView.setTouchable(Touchable.enabled);
//	}
//
//	@Override
//	public void hide(AnimationType hideType) {
//		super.hide(hideType);
//		setViewState(ViewState.HIDE);
//		listView.setTouchable(Touchable.disabled);
//		content.addAction(Actions.sequence(Actions.moveTo(-content.getWidth(),
//				content.getY(), 0.5f, Interpolation.swingIn), Actions
//				.run(new Runnable() {
//					@Override
//					public void run() {
//						setVisible(false);
//					}
//				})));
//	}
	
	@Override
	public void show() {
		super.show();
		toFront();
		setVisible(true);
		content.setX(getWidth());
		content.clearActions();
		content.addAction(Actions.moveTo(50, content.getY(), 0.5f,
				Interpolation.swingOut));
		idChoiced = -1;
		listView.removeAll();
		Request.getInstance().getAllCategory(new GetAllCategory());
		listView.setTouchable(Touchable.enabled);
	}

	@Override
	public void hide() {
		setViewState(ViewState.HIDE);
		listView.setTouchable(Touchable.disabled);
		content.addAction(Actions.sequence(Actions.moveTo(-content.getWidth(),
				content.getY(), 0.5f, Interpolation.swingIn), Actions
				.run(new Runnable() {
					@Override
					public void run() {
						setVisible(false);
					}
				})));
	}


	class ItemFilterCategory extends Item {

		public int id;
		private Image checkBox;
		private Image imgFocus;
		private Vector2 touchPoint;

		public ItemFilterCategory(ListView parent, String name, int id) {
			this.id = id;
			touchPoint = new Vector2();
			setSize(parent.getWidth(), 45);

			imgFocus = new Image(new NinePatch(new NinePatch(
					Assets.instance.uiP.ninepatch_white), new Color(218 / 255f,
					42 / 255f, 42 / 255f, 1)));
			imgFocus.setSize(this.getWidth(), this.getHeight());
			imgFocus.getColor().a = 0;

			LabelStyle style = new LabelStyle(
					Assets.instance.fontFactory.getLight15(), Color.BLACK);
			Label lbName = new Label(name, style);
			lbName.setPosition(15, this.getHeight() / 2 - lbName.getHeight()
					/ 2);

			checkBox = new Image(Assets.instance.uiP.checkboxUnChecked);
			checkBox.setSize(30, 30);
			checkBox.setPosition(this.getWidth() - 60, this.getHeight() / 2
					- checkBox.getHeight() / 2);

			this.addActor(imgFocus);
			this.addActor(lbName);
			this.addActor(checkBox);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					imgFocus.getColor().a = 1;
					touchPoint.set(x, y);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					imgFocus.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						checkBox.setDrawable(new TextureRegionDrawable(
								Assets.instance.uiP.checkboxChecked));
						listener.onItemClick();
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						imgFocus.getColor().a = 0;
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

	class ImageLabel extends Table {
		Image bg;
		Label lb;
		Vector2 touchPoint;

		public ImageLabel(String title, LabelStyle style) {
			touchPoint = new Vector2();
			setSize(style.font.getBounds(title).width + 20,
					style.font.getBounds(title).height + 20);
			bg = new Image(new NinePatch(Assets.instance.uiP.textfield, 4, 4,
					4, 4));
			bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
			bg.setSize(this.getWidth(), this.getHeight());
			bg.setPosition(getX(), getY());
			lb = new Label(title, style);
			lb.setPosition(getX() + getWidth() / 2 - lb.getWidth() / 2, getY()
					+ getHeight() / 2 - lb.getHeight() / 2);
			addActor(bg);
			addActor(lb);
		}

		public ImageLabel(String title, LabelStyle style, float width,
				float height) {
			touchPoint = new Vector2();
			setSize(width, height);
			bg = new Image(new NinePatch(Assets.instance.uiP.textfield, 4, 4,
					4, 4));
			bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
			bg.setSize(this.getWidth(), this.getHeight());
			bg.setPosition(getX(), getY());
			lb = new Label(title, style);
			lb.setPosition(getX() + getWidth() / 2 - lb.getWidth() / 2, getY()
					+ getHeight() / 2 - lb.getHeight() / 2);
			addActor(bg);
			addActor(lb);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bg.setColor(200 / 255f, 200 / 255f, 200 / 255f, 1);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.setColor(new Color(230 / 255f, 230 / 255f, 230 / 255f, 1));
					if (touchPoint.epsilonEquals(x, y, 20)) {
						listener.onItemClick();
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bg.setColor(new Color(230 / 255f, 230 / 255f,
								230 / 255f, 1));
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

	class GetAllCategory implements HttpResponseListener {

		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			respone = (new JsonReader())
					.parse(httpResponse.getResultAsString());
		}

		@Override
		public void failed(Throwable t) {

		}

		@Override
		public void cancelled() {

		}
	}

}
