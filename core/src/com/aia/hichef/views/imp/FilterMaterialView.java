package com.aia.hichef.views.imp;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.networks.ExtParamsKey;
import com.aia.hichef.networks.Request;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.hd.input.keyboard.KeyboardConfig;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnBackSpaceComma;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnDoneListener;
import com.gdx.hd.input.keyboard.VirtualKeyboard.OnHideListener;

public class FilterMaterialView extends View {

	private Group				topGroup;

	private ListView			listView;
	public JsonValue			respone;

	private ArrayList<Integer>	listInclude;
	private ArrayList<Integer>	listExclude;

	private TextField			tfInclude;
	private TextField			tfExclude;

	public FilterMaterialView buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public FilterMaterialView buildComponent() {
		setName(StringSystem.VIEW_FILTERMATERIAL);
		listInclude = new ArrayList<Integer>();
		listExclude = new ArrayList<Integer>();

		topGroup = new Group();
		topGroup.setBounds(0, getHeight() - Constants.HEIGHT_ACTIONBAR,
				getWidth(), Constants.HEIGHT_ACTIONBAR);
		Image bgTop = new Image(new NinePatch(new NinePatch(
				Assets.instance.uiP.ninepatch_white), new Color(218 / 255f,
				42 / 255f, 42 / 255f, 1)));
		bgTop.setSize(topGroup.getWidth(), topGroup.getHeight());

		Label title = new Label("Lọc Theo nguyên liệu", new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), Color.WHITE));
		title.setPosition(topGroup.getWidth() / 2 - title.getWidth() / 2,
				topGroup.getHeight() / 2 - title.getHeight() / 2);

		ImageButton btBack = new ImageButton(Assets.instance.uiA.reg_back, 40,
				topGroup.getHeight());
		btBack.setListener(new ItemListener() {

			@Override
			public void onItemClick() {
				hide();
				AbstractGameScreen.keyboard.show(false);
			}
		});
		topGroup.addActor(bgTop);
		topGroup.addActor(btBack);
		topGroup.addActor(title);

		// //////Text field/////////
		final float heightKeyboard = AbstractGameScreen.keyboard.getHeight()
				* Constants.HEIGHT_SCREEN / Gdx.graphics.getHeight();
		TextFieldStyle styleTf = new TextFieldStyle();
		styleTf.background = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.textfield, 4, 4, 4, 4));
		styleTf.font = Assets.instance.fontFactory.getRegular20();
		styleTf.cursor = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.bgActionBar));
		styleTf.fontColor = Color.BLACK;
		tfInclude = new TextField("", styleTf);
		Image bgtfInclude = new Image(new NinePatch(
				Assets.instance.uiP.textfield, 4, 4, 4, 4));
		tfInclude.setMessageText("Chứa nguyên liệu");
		bgtfInclude.setSize(Constants.WIDTH_SCREEN - 20, 45);
		tfInclude.setSize(Constants.WIDTH_SCREEN - 60, 45);
		bgtfInclude.setPosition(getWidth() / 2 - bgtfInclude.getWidth() / 2,
				topGroup.getY() - bgtfInclude.getHeight() - 5);
		tfInclude.setPosition(bgtfInclude.getX() + 5, topGroup.getY()
				- tfInclude.getHeight() - 5);

		ButtonStyle styleDel = new ButtonStyle();
		styleDel.up = new TextureRegionDrawable(Assets.instance.uiP.rgDel);
		// styleDel.down = new
		// TextureRegionDrawable(Assets.instance.uiP.rgDelDown);

		Button btDel = new Button(styleDel);
		btDel.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				tfInclude.setText("");
				listInclude.clear();
				String contentExclude = tfExclude.getText();
				AbstractGameScreen.keyboard.clear();
				if (!getStage().getKeyboardFocus().isAscendantOf(tfInclude)) {
					AbstractGameScreen.keyboard.setText(contentExclude);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		btDel.setSize(35, 35);
		btDel.setPosition(
				bgtfInclude.getX() + bgtfInclude.getWidth() - btDel.getWidth()
						- 4, bgtfInclude.getY() + bgtfInclude.getHeight() / 2
						- btDel.getHeight() / 2);

		tfExclude = new TextField("", styleTf);
		Image bgtfExclude = new Image(new NinePatch(
				Assets.instance.uiP.textfield, 4, 4, 4, 4));
		tfExclude.setMessageText("Không chứa nguyên liệu");
		bgtfExclude.setSize(Constants.WIDTH_SCREEN - 20, 45);
		tfExclude.setSize(Constants.WIDTH_SCREEN - 60, 45);
		bgtfExclude.setPosition(getWidth() / 2 - bgtfExclude.getWidth() / 2,
				tfInclude.getY() - bgtfExclude.getHeight() - 5);
		tfExclude.setPosition(bgtfInclude.getX() + 5, tfInclude.getY()
				- tfExclude.getHeight() - 5);

		Button btDel2 = new Button(styleDel);
		btDel2.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				tfExclude.setText("");
				listExclude.clear();
				String contentInclude = tfInclude.getText();
				AbstractGameScreen.keyboard.clear();
				if (!getStage().getKeyboardFocus().isAscendantOf(tfExclude)) {
					AbstractGameScreen.keyboard.setText(contentInclude);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
		btDel2.setSize(35, 35);
		btDel2.setPosition(
				bgtfExclude.getX() + bgtfExclude.getWidth() - btDel2.getWidth()
						- 4, bgtfExclude.getY() + bgtfExclude.getHeight() / 2
						- btDel2.getHeight() / 2);

		tfInclude.setOnscreenKeyboard(AbstractGameScreen.keyboard);
		tfInclude.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				registerKeyBoard(tfInclude);
				listView.setHeight(tfExclude.getY() - 5 - heightKeyboard);
				listView.setY(heightKeyboard);
				return true;
			}

			@Override
			public boolean keyTyped(InputEvent event, char character) {
				Request.getInstance().searchMaterial(
						getString(tfInclude.getText()), new GetMaterial());
				return true;
			}
		});

		tfExclude.setOnscreenKeyboard(AbstractGameScreen.keyboard);
		tfExclude.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				registerKeyBoard(tfExclude);
				listView.setHeight(tfExclude.getY() - 5 - heightKeyboard);
				listView.setY(heightKeyboard);
				return true;
			}

			@Override
			public boolean keyTyped(InputEvent event, char character) {
				Request.getInstance().searchMaterial(
						getString(tfExclude.getText()), new GetMaterial());
				return true;
			}
		});

		listView = new ListView(_viewController, new Table());

		listView.setBounds(0, heightKeyboard, getWidth(), tfExclude.getY() - 5
				- heightKeyboard);
		listView.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.ninepatch_white)));

		Image bg = new Image(new NinePatch(new NinePatch(
				Assets.instance.uiP.ninepatch_white), new Color(232 / 255f,
				232 / 255f, 232 / 255f, 1)));
		bg.setSize(getWidth(), getHeight());

		this.addActor(bg);
		this.addActor(topGroup);
		this.addActor(bgtfInclude);
		this.addActor(tfInclude);
		this.addActor(btDel);
		this.addActor(bgtfExclude);
		this.addActor(tfExclude);
		this.addActor(btDel2);
		this.addActor(listView);
		hide();
		return this;
	}

	private void registerKeyBoard(TextField tf) {
		AbstractGameScreen.keyboard.registerTextField(tf,
				KeyboardConfig.NORMAL, KeyboardConfig.SINGLE_LINE);
		AbstractGameScreen.keyboard.show(true);
		AbstractGameScreen.keyboard.setBackspaceComma(new OnBackSpaceComma() {
			@Override
			public void backComma() {
				removeStringItem();
			}
		});
		AbstractGameScreen.keyboard.setDoneListener(new OnDoneListener() {

			@Override
			public void done() {
				hide();
				((ViewSearchResult) _viewController
						.getView(StringSystem.VIEW_SEARCHRESULT))
						.searchAdvance();
			}
		});

		AbstractGameScreen.keyboard.setHideListener(new OnHideListener() {

			@Override
			public void hide() {
				// TODO Auto-generated method stub
				listView.setHeight(tfExclude.getY() - 5);
				listView.setY(0);
			}
		});
	}

	private String getString(String content) {
		if (content.contains(",")) {
			return content.substring(content.lastIndexOf(",") + 1).trim();
		} else {
			return content.trim();
		}
	}

	private void addString(String addContent) {
		if (AbstractGameScreen.keyboard.getText().contains(",")) {
			AbstractGameScreen.keyboard.setText(AbstractGameScreen.keyboard
					.getText().substring(
							0,
							AbstractGameScreen.keyboard.getText().lastIndexOf(
									",") + 1)
					+ addContent + " ,");
		} else {
			AbstractGameScreen.keyboard.setText(addContent + " ,");
		}
		String content = AbstractGameScreen.keyboard.getText();
		int count = content.replaceAll("[^,]", "").length();
		System.out.println(count);
	}

	private void removeStringItem() {
		String content = ((TextField) _stage.getKeyboardFocus()).getText();
		content = content.substring(0, content.length() - 2);
		if (content.contains(",")) {
			AbstractGameScreen.keyboard.setText(content.substring(0,
					content.lastIndexOf(",") + 1));
		} else {
			AbstractGameScreen.keyboard.clear();
		}
		if (_stage.getKeyboardFocus() == tfInclude) {
			if (listInclude.size() > 0) {
				listInclude.remove(listInclude.size() - 1);
			}
		} else {
			if (listExclude.size() > 0) {
				listExclude.remove(listExclude.size() - 1);
			}
		}
	}

	// @Override
	// public void show(AnimationType showType) {
	// super.show(showType);
	// toFront();
	// clearActions();
	// setViewState(ViewState.SHOW);
	// this.setVisible(true);
	// listView.removeAll();
	// setY(-getHeight());
	// _stage.setKeyboardFocus(tfInclude);
	// registerKeyBoard(tfInclude);
	// this.addAction(Actions.moveTo(0, 0, 0.5f, Interpolation.exp10Out));
	// }
	//
	// @Override
	// public void hide(AnimationType hideType) {
	// setViewState(ViewState.HIDE);
	// this.addAction(Actions.sequence(
	// Actions.moveTo(0, -getHeight(), 0.5f, Interpolation.exp10Out),
	// Actions.visible(false)));
	// super.hide(hideType);
	// }

	@Override
	public void show() {
		super.show();
		toFront();
		clearActions();
		this.setVisible(true);
		listView.removeAll();
		setY(-getHeight());
		_stage.setKeyboardFocus(tfInclude);
		registerKeyBoard(tfInclude);
		this.addAction(Actions.moveTo(0, 0, 0.5f, Interpolation.exp10Out));
	}

	public void hide() {
		super.hide();
		this.addAction(Actions.sequence(
				Actions.moveTo(0, -getHeight(), 0.5f, Interpolation.exp10Out),
				Actions.visible(false)));
	};

	@Override
	public void update() {
		super.update();
		if (respone != null) {
			listView.removeAll();
			JsonValue content = respone.get(ExtParamsKey.CONTENT);
			for (int i = 0; i < content.size; i++) {
				JsonValue material = content.get(i);
				final String title = material.getString(ExtParamsKey.TITLE);
				final int id = material.getInt(ExtParamsKey.ID);
				ItemMaterial newItemMateril = new ItemMaterial(listView, title,
						id);
				newItemMateril.setListener(new ItemListener() {
					@Override
					public void onItemClick() {
						if (_stage.getKeyboardFocus() == tfInclude) {
							addToInclude(id, title);
						} else {
							addToExclude(id, title);
						}
					}
				});
				listView.addItem(newItemMateril);
			}
			respone = null;
		}
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
		// if (AbstractGameScreen.keyboard.isShowing()) {
		// AbstractGameScreen.keyboard.hide();
		// System.out.println("back keyboard");
		// } else {
		// System.out.println("back");
		// }
		System.out.println("chay vao ham back");
		super.back();
	}

	private void addToInclude(int id, String name) {
		if (!listInclude.contains((Object) id)) {
			listInclude.add(id);
			addString(name);
		}
	}

	private void addToExclude(int id, String name) {
		if (!listExclude.contains((Object) id)) {
			listExclude.add(id);
			addString(name);
		}
	}

	public String getInclude() {
		String result = "";
		for (int i = 0; i < listInclude.size(); i++) {
			if (i == 0) {
				result += listInclude.get(0);
			} else {
				result = result + "-" + listInclude.get(i);
			}
		}
		return result;
	}

	public String getExclude() {
		String result = "";
		for (int i = 0; i < listExclude.size(); i++) {
			if (i == 0) {
				result += listExclude.get(0);
			} else {
				result = result + "-" + listExclude.get(i);
			}
		}
		return result;
	}

	class ItemMaterial extends Item {

		public int		id;
		public String	title;
		private Image	imgFocus;
		private Vector2	touchPoint;

		public ItemMaterial(ListView parent, String name, int id) {
			this.setSize(parent.getWidth(), 50);
			this.id = id;
			touchPoint = new Vector2();
			imgFocus = new Image(Assets.instance.uiP.transparent);
			imgFocus.setSize(this.getWidth(), this.getHeight());
			imgFocus.getColor().a = 0;
			this.title = name;
			Label lbName = new Label(name, new LabelStyle(
					Assets.instance.fontFactory.getLight20(), Color.BLACK));
			lbName.setPosition(5, this.getHeight() / 2 - lbName.getHeight() / 2);
			this.addActor(imgFocus);
			this.addActor(lbName);
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

	class ImageButton extends Table {
		Image	bg;
		Image	icon;
		Vector2	touchPoint;

		public ImageButton(TextureRegion rg, float width, float height) {
			setSize(width, height);
			touchPoint = new Vector2();
			this.icon = new Image(rg);
			bg = new Image(new NinePatch(Assets.instance.uiP.transparent));
			bg.getColor().a = 0;
			bg.setSize(width, height);
			bg.setPosition(getX(), getY());
			icon.setSize(35, 35);
			icon.setPosition(getX() + width / 2 - icon.getWidth() / 2, getY()
					+ getHeight() / 2 - icon.getHeight() / 2);
			addActor(bg);
			addActor(icon);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bg.getColor().a = 1;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						listener.onItemClick();
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bg.getColor().a = 0;
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

	class ImageLabel extends Table {
		Image	bg;
		Label	lb;
		Vector2	touchPoint;

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

	class GetMaterial implements HttpResponseListener {

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
