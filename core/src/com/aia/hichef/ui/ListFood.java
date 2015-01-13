package com.aia.hichef.ui;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.ListView;
import com.aia.hichef.poolsystem.ItemFood;
import com.aia.hichef.poolsystem.PoolSystem;
import com.aia.hichef.views.IViewController;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ListFood extends ListView {
	LabelStyle	style_normal;
	LabelStyle	style_bold;
	BitmapFont	font_normal;
	BitmapFont	font_bold;

	public ListFood(IViewController _iviewController, Table table) {
		super(_iviewController, table);
		// TODO Auto-generated constructor stub
		font_bold = Assets.instance.fontFactory.getBold20();
		font_normal = Assets.instance.fontFactory.getRegular20();
		style_normal = new LabelStyle(font_normal, new Color(77 / 255f,
				77 / 255f, 77 / 255f, 1));
		style_bold = new LabelStyle(font_bold, new Color(77 / 255f, 77 / 255f,
				77 / 255f, 1));
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(240 / 255f,
						240 / 255f, 240 / 255f, 1))));
		table.top();
	}

	public ItemFood getItem(int id, Image bg, String title, String short_des,
			int like, int view, float width, float height) {
		ItemFood itemFood = PoolSystem.getInstance().itemFoodPool.obtain();
		itemFood.create(id, bg, title, short_des, like, view, width, height);
		return itemFood;
	}

	// public class ItemFood extends Item implements Poolable {
	// private int id;
	// Label title;
	// Label content;
	// Image subbg;
	// Label stt_eye;
	// Label stt_heart;
	// Image icon_eye;
	// Image icon_heart;
	//
	// public ItemFood(int id, Image bg, String title, String short_des,
	// int like, int view, float width, float height) {
	// create(id, bg, title, short_des, like, view, width, height);
	// }
	//
	// public void create(int id, Image bg, String title, String short_des,
	// int like, int view, float width, float height) {
	// setWidth(width);
	// setHeight(height);
	// this.id = id;
	// bg.setPosition(getX(), getY());
	// bg.setSize(width, height);
	// subbg = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
	// new Color(241 / 255f, 241 / 255f, 241 / 255f, 0.85f)));
	// subbg.setSize(this.getWidth(), this.getHeight() / 4);
	// subbg.setPosition(getX(), getY());
	// icon_eye = new Image(Assets.instance.uiM.icon_heart_06);
	// icon_heart = new Image(Assets.instance.uiM.icon_heart_03);
	// icon_heart.setPosition(subbg.getX() + subbg.getWidth() - 5
	// - icon_heart.getWidth(), subbg.getY() + subbg.getHeight()
	// / 2 - icon_heart.getHeight() / 2);
	// stt_heart = new Label(getStrNumber(like), style_normal);
	// stt_heart.setPosition(icon_heart.getX() - 4 - stt_heart.getWidth(),
	// icon_heart.getY() + icon_heart.getHeight() / 2
	// - font_normal.getBounds(like + "").height);
	// icon_eye.setPosition(stt_heart.getX() - 20 - icon_eye.getWidth(),
	// subbg.getY() + subbg.getHeight() / 2 - icon_eye.getHeight()
	// / 2);
	// stt_eye = new Label(getStrNumber(view), style_normal);
	// stt_eye.setPosition(
	// icon_eye.getX() - 4 - stt_eye.getWidth(),
	// icon_eye.getY() + icon_eye.getHeight() / 2
	// - font_normal.getBounds(view + "").height);
	// this.title = new Label(getSubString(title,
	// stt_eye.getX() - subbg.getX() - 50), style_bold);
	// this.title.setPosition(
	// subbg.getX() + 5,
	// subbg.getY() + subbg.getHeight() - 5
	// - this.title.getHeight());
	// this.content = new Label(getSubString(short_des, stt_eye.getX()
	// - subbg.getX() - 50), style_normal);
	// this.content.setPosition(this.title.getX(), subbg.getY() + 5);
	// addActor(bg);
	// addActor(subbg);
	// addActor(this.title);
	// addActor(this.content);
	// addActor(icon_eye);
	// addActor(icon_heart);
	// addActor(stt_eye);
	// addActor(stt_heart);
	// }
	//
	// private String getStrNumber(int number) {
	// String str = "";
	// if (number >= 1000) {
	// number *= 10;
	// int number2 = number / 1000;
	// float number3 = number2 / 10f;
	// str = number3 + "k";
	// } else {
	// str = number + "";
	// }
	// return str;
	// }
	//
	// private String getSubString(String str, float width) {
	// String substr = str;
	// for (int i = 0; i < str.length(); i++) {
	// if (font_bold.getBounds(str.substring(0, i)).width > width
	// - font_bold.getBounds("...").width) {
	// substr = str.substring(0, i) + "...";
	// break;
	// }
	// }
	// return substr;
	// }
	//
	// public int getId() {
	// return id;
	// }
	//
	// @Override
	// public void reset() {
	// id = 0;
	// title = null;
	// content = null;
	// subbg = null;
	// stt_eye = null;
	// stt_heart = null;
	// icon_eye = null;
	// icon_heart = null;
	// }
	// }
}
