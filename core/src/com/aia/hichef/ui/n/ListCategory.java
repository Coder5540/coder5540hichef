package com.aia.hichef.ui.n;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Item;
import com.aia.hichef.components.ListView;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.views.IViewController;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ListCategory extends ListView {
	LabelStyle style_bold;

	public ListCategory(IViewController _iviewController, Table table) {
		super(_iviewController, table);
		style_bold = new LabelStyle(Assets.instance.fontFactory.getLight20(),
				Color.BLACK);
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(255 / 255f,
						255 / 255f, 255 / 255f, 1))));
		table.bottom();
	}

	public ItemCategory getItem(int id, String title, float width, float height) {
		if (id == -1)
			return new ItemCategory(id, title, width, height, false);
		else
			return new ItemCategory(id, title, width, height, true);
	}

	public ItemCategory getItemLoading() {
		return new ItemCategory();
	}

	public class ItemCategory extends Item {
		private int id;
		Label title;
		Image subbg;
		Image clickbg;
		Image line;

		public ItemCategory(int id, String title, float width, float height,
				boolean isCenter) {
			setWidth(width);
			setHeight(height);
			this.id = id;
			// line = new Image(new Texture("line.png"));
			line = new Image(Assets.instance.uiN.line);
			line.setWidth(getWidth());
			// line.setHeight(2);
			line.setPosition(0, 0);
			subbg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					Color.WHITE));
			subbg.setSize(this.getWidth(), this.getHeight());
			subbg.setPosition(getX(), getY());
			clickbg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					new Color(222 / 255f, 219 / 255f, 222 / 255f, 1)));
			clickbg.setSize(this.getWidth(), this.getHeight());
			clickbg.setPosition(getX(), getY());
			// clickbg.getColor().a = 00.3f;
			this.title = new Label(getSubString(title, width - 10), style_bold);
			if (isCenter)
				this.title.setPosition(getX() + 50, getY() + this.getHeight()
						/ 2 - this.title.getHeight() / 2);
			else
				this.title.setPosition(getX() + this.getWidth() / 2
						- this.title.getWidth() / 2, getY() + this.getHeight()
						/ 2 - this.title.getHeight() / 2);
			// addActor(subbg);
			addActor(clickbg);
			addActor(line);
			clickbg.setVisible(false);
			this.title.setAlignment(Align.center);
			addActor(this.title);
			addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					clickbg.setVisible(true);
					return super.touchDown(event, x, y, pointer, button);
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					clickbg.setVisible(false);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}

		public ItemCategory() {
			subbg = new Image(Assets.instance.uiN.loading);
			// subbg.setSize(20, 20);
			setSize(Constants.WIDTH_SCREEN, subbg.getHeight());
			subbg.setOrigin(subbg.getWidth() / 2, subbg.getHeight() / 2);
			subbg.setPosition(getWidth() / 2 - subbg.getWidth() / 2,
					getHeight() / 2 - subbg.getHeight() / 2);
			subbg.addAction(Actions.forever(Actions.rotateBy(40, 0.1f)));
			addActor(subbg);
		}

		private String getSubString(String str, float width) {
			String substr = str;
			for (int i = 0; i < str.length(); i++) {
				if (Assets.instance.fontFactory.getBold15().getBounds(
						str.substring(0, i)).width > width
						- Assets.instance.fontFactory.getBold13().getBounds(
								"...").width) {
					substr = str.substring(0, i) + "...";
					break;
				}
			}
			return substr;
		}

		public int getId() {
			return id;
		}
	}

}
