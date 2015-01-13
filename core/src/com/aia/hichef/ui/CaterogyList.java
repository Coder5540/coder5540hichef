package com.aia.hichef.ui;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.views.IViewController;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class CaterogyList extends ListView {
	LabelStyle	style_normal;
	LabelStyle	style_big;

	public CaterogyList(IViewController viewController, Table table, float width, float height) {
		super(viewController, table);
		style_normal = new LabelStyle(Assets.instance.fontFactory.getRegular15(), new Color(77 / 255f, 77 / 255f, 77 / 255f, 1));
		style_big = new LabelStyle(Assets.instance.fontFactory.getRegular20(), new Color(77 / 255f, 77 / 255f, 77 / 255f, 1));
		setBounds(0, 0, width, height);
		setY(getY() - 20);
		setBackground(new NinePatchDrawable(new NinePatch(Assets.instance.uiM.bg_ninepatch, new Color(240 / 255f, 240 / 255f, 240 / 255f, 1))));
		table.top();
		clearListeners();
	}

	void creatCaterogy() {
		for (int i = 0; i < 10; i++) {
			ItemCaterogy item = getItem((i + 1), 1, new Image(Assets.instance.uiM.icono_recetas), "MÓN ĂN " + (i + 1), "Đây là món thứ " + (i + 1)
					+ "\n hihihaha", getWidth(), 110);
			final int index = i;
			item.setListener(new ItemListener() {

				@Override
				public void onItemClick() {
					System.out.println("click to item " + (index + 1));
				}
			});
			this.addItem(item);
			// this.addLine(0);
		}
	}

	public ItemCaterogy getItem(int stt, int id, Image image, String title, String description, float width, float height) {
		ItemCaterogy item = new ItemCaterogy();
		item.setSize(width, height);
		image.setPosition(item.getX() + 15, item.getY() + item.getHeight() / 2 - image.getHeight() / 2);
		item.addActor(image);
		Label lb_title = new Label(title, style_big);
		Label lb_description = new Label(description, style_normal);
		Label lb_stt = new Label(stt + ".", style_big);
		lb_stt.setPosition(image.getX() + image.getWidth() + 20, image.getY() + image.getHeight() - 2 - lb_stt.getHeight());
		lb_title.setPosition(lb_stt.getX() + lb_stt.getWidth() + 5, image.getY() + image.getHeight() - 2 - lb_title.getHeight());
		lb_description.setPosition(lb_stt.getX(), image.getY() + 2);
		item.addActor(lb_stt);
		item.addActor(lb_title);
		item.addActor(lb_description);
		return item;
	}

	public class ItemCaterogy extends Item {
		final Image	bg2;
		Image		bg1;
		public int	id;

		public ItemCaterogy() {
			bg2 = new Image(new NinePatch(Assets.instance.uiM.bg_icon_caterogy2, 5, 5, 5, 5));
			bg2.getColor().a = 0;
			bg1 = new Image(new NinePatch(new TextureRegion(Assets.instance.uiM.bg_icon_caterogy), 5, 5, 5, 5));
			addActor(bg1);
			addActor(bg2);
		}

		public void setSize(float width, float height) {
			ItemCaterogy.this.setWidth(width);
			ItemCaterogy.this.setHeight(height);
			bg2.setSize(width - 20, height - 8);
			bg1.setSize(width - 20, height - 8);
			bg1.setPosition(getX() + 10, getY() + 4);
			bg2.setPosition(getX() + 10, getY() + 4);
		}

		@Override
		public void setListener(final ItemListener listener) {
			addListener(new ActorGestureListener() {

				// @Override
				// public void touchDown(InputEvent event, float x, float y, int
				// pointer, int button) {
				// // bg2.getColor().a = 1;
				// }

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					// bg2.getColor().a = 0;
					listener.onItemClick();
				}

				// @Override
				// public void pan(InputEvent event, float x, float y, float
				// deltaX, float deltaY) {
				// bg2.getColor().a = 0;
				// super.pan(event, x, y, deltaX, deltaY);
				// }

				@Override
				public void tap(InputEvent event, float x, float y, int count, int button) {
					bg2.addAction(Actions.sequence(Actions.alpha(1f), Actions.alpha(0f, .1f)));
					listener.onItemClick();
					super.tap(event, x, y, count, button);
				}
			});

			// addListener(new ClickListener() {
			//
			// @Override
			// public boolean touchDown(InputEvent event, float x, float y, int
			// pointer, int button) {
			// bg2.getColor().a = 1;
			// System.out.println("down");
			// return super.touchDown(event, x, y, pointer, button);
			// }
			//
			// @Override
			// public void touchUp(InputEvent event, float x, float y, int
			// pointer, int button) {
			// bg2.getColor().a = 0;
			// listener.onItemClick();
			// System.out.println("up");
			// super.touchUp(event, x, y, pointer, button);
			// }
			//
			// });
		}

	}

}
