package com.aia.hichef.components;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.ui.n.MyScrollPanel;
import com.aia.hichef.views.IViewController;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ListView extends MyScrollPanel {
	private Table			table;
	private ArrayList<Item>	listItem;
	private ArrayList<Item>	listLine;
	private ArrayList<Item>	listTitle;
	public IViewController	_viewController;

	public ListView(IViewController _viewController, Table table) {
		super(table);
		table.setClip(true);
		table.top();
		this.table = table;
		listItem = new ArrayList<Item>();
		listLine = new ArrayList<Item>();
		listTitle = new ArrayList<Item>();
		NinePatchDrawable n = new NinePatchDrawable(new NinePatch(
				Assets.instance.uiP.transparent));
		getStyle().vScrollKnob = n;
		this._viewController = _viewController;
	}

	public IViewController getViewController() {
		return _viewController;
	}

	public void setScrollKnob(NinePatchDrawable n) {
		getStyle().vScrollKnob = n;
	}

	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		table.setCullingArea(new Rectangle(x, y, width, height));
	}

	public void setBackground(Drawable background) {
		table.setBackground(background);
	}

	public Item getItem(int index) {
		return listItem.get(index);
	}

	public void addItem(Item item) {
		item.setIndex(listItem.size());
		listItem.add(item);
		table.add(item);
		table.row();
	}

	public void addLine() {
		LineItem line = new LineItem();
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addLine(float width, float height, float padLeft, float padTop,
			float padBottom) {
		LineItem line = new LineItem(width, height, padLeft, padTop, padBottom);
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addLine(float width, float height) {
		LineItem line = new LineItem(width, height);
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addLine(Image image) {
		LineItem line = new LineItem(image);
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addLine(Image image, float width, float height) {
		LineItem line = new LineItem(image, width, height);
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addLine(Image image, float width, float height, float padLeft,
			float padTop, float padBottom) {
		LineItem line = new LineItem(image, width, height, padLeft, padTop,
				padBottom);
		line.setIndex(listLine.size());
		listLine.add(line);
		table.add(line);
		table.row();
	}

	public void addTitle(Item title) {
		title.setIndex(listTitle.size());
		listTitle.add(title);
		table.add(title).row();
	}

	public void addTitle(String title) {
		TitleItem titleitem = new TitleItem(title);
		titleitem.setIndex(listTitle.size());
		listTitle.add(titleitem);
		table.add(titleitem).row();
	}

	public void addTitle(String title, int align, Color color) {
		TitleItem titleitem = new TitleItem(title, align, color);
		titleitem.setIndex(listTitle.size());
		listTitle.add(titleitem);
		table.add(titleitem).row();
	}

	public void addTitle(String title, float padLeft, float padTop,
			float padBottom, Color color) {
		TitleItem titleitem = new TitleItem(title, padLeft, padTop, padBottom,
				color);
		titleitem.setIndex(listTitle.size());
		listTitle.add(titleitem);
		table.add(titleitem);
		table.row();
	}

	public void removeItem(int index) {
		if (index >= getSize() || index < 0) {
			return;
		}
		table.removeActor(listItem.get(index));
		listItem.remove(index);
		for (int i = index; i < listItem.size(); i++) {
			listItem.get(i).setIndex(i);
		}
	}

	public void removeItem(Item item) {
		table.removeActor(item);
		listItem.remove(item.getIndex());
		for (int i = item.getIndex(); i < listItem.size(); i++) {
			listItem.get(i).setIndex(i);
		}
	}

	public void removeLine(int index) {
		if (index >= listLine.size()) {
			return;
		}
		table.removeActor(listLine.get(index));
		listLine.remove(index);
	}

	public void removeLine(Item item) {
		table.removeActor(item);
		listLine.remove(item.getIndex());
		for (int i = item.getIndex(); i < listLine.size(); i++) {
			listLine.get(i).setIndex(i);
		}
	}

	public void removeTitle(int index) {
		if (index >= listTitle.size()) {
			return;
		}
		table.removeActor(listTitle.get(index));
		listTitle.remove(index);
		for (int i = index; i < listTitle.size(); i++) {
			listTitle.get(i).setIndex(i);
		}
	}

	public void removeTitle(Item item) {
		table.removeActor(item);
		listTitle.remove(item.getIndex());
		for (int i = item.getIndex(); i < listTitle.size(); i++) {
			listTitle.get(i).setIndex(i);
		}
	}

	public void removeAll() {
		table.clear();
		listItem.clear();
		listLine.clear();
		listTitle.clear();

	}

	public int getSize() {
		return listItem.size();
	}

	// class LineItem
	class LineItem extends Item {
		public LineItem() {
			LineItem.this.setWidth(ListView.this.getWidth());
			LineItem.this.setHeight(1);
			Image bg = new Image(new NinePatch(
					Assets.instance.uiP.ninepatch_gray));
			bg.setSize(ListView.this.getWidth(), 1);
			bg.setPosition(LineItem.this.getX(), LineItem.this.getY()
					+ LineItem.this.getHeight() / 2 - 1);
			LineItem.this.addActor(bg);
		}

		public LineItem(float width, float height) {
			LineItem.this.setWidth(ListView.this.getWidth());
			LineItem.this.setHeight(1);
			Image bg = new Image(new NinePatch(
					Assets.instance.uiP.ninepatch_gray));
			if (height > 30) {
				height = 30;
			}
			bg.setSize(width, height);
			bg.setPosition(LineItem.this.getX() + LineItem.this.getWidth() / 2
					- bg.getWidth() / 2, +LineItem.this.getHeight() / 2
					- height / 2);
			LineItem.this.addActor(bg);
		}

		public LineItem(float width, float height, float padLeft, float padTop,
				float padBottom) {
			LineItem.this.setWidth(ListView.this.getWidth());
			LineItem.this.setHeight(height + padTop + padBottom);
			Image bg = new Image(new NinePatch(
					Assets.instance.uiP.ninepatch_gray));
			bg.setSize(width, height);
			bg.setPosition(padLeft, LineItem.this.getY() + padBottom);
			LineItem.this.addActor(bg);
		}

		public LineItem(Image image) {
			LineItem.this.setWidth(ListView.this.getWidth());
			LineItem.this.setHeight(30);
			image.setSize(ListView.this.getWidth(), 2);
			image.setPosition(LineItem.this.getX(), LineItem.this.getY()
					+ LineItem.this.getHeight() / 2 - 1);
			LineItem.this.addActor(image);
		}

		public LineItem(Image image, float width, float height) {
			LineItem.this.setWidth(width);
			LineItem.this.setHeight(30);
			if (height > 30) {
				height = 30;
			}
			image.setSize(width, height);
			image.setPosition(LineItem.this.getX(), LineItem.this.getY()
					+ LineItem.this.getHeight() / 2 - image.getHeight() / 2);
			LineItem.this.addActor(image);
		}

		public LineItem(Image image, float width, float height, float padLeft,
				float padTop, float padBottom) {
			LineItem.this.setWidth(width);
			LineItem.this.setHeight(height + padTop + padBottom);
			image.setSize(width, height);
			image.setPosition(padLeft, LineItem.this.getY() + padBottom);
			LineItem.this.addActor(image);
		}
	}

	// class Title
	class TitleItem extends Item {
		public TitleItem(String title) {
			TitleItem.this.setWidth(ListView.this.getWidth());
			TitleItem.this.setWidth(30);
			LabelStyle style = new LabelStyle();
			style.font = Assets.instance.fontFactory.getRegular20();
			style.fontColor = Color.WHITE;
			Label l = new Label(title, style);
			l.setPosition(
					TitleItem.this.getX() + TitleItem.this.getWidth() / 2
							- l.getWidth() / 2,
					TitleItem.this.getY() + TitleItem.this.getHeight() / 2
							- l.getHeight() / 2);
			TitleItem.this.addActor(l);
		}

		public TitleItem(String title, int align, Color color) {
			TitleItem.this.setWidth(ListView.this.getWidth());
			TitleItem.this.setWidth(30);
			LabelStyle style = new LabelStyle();
			style.font = Assets.instance.fontFactory.getRegular20();
			style.fontColor = color;
			Label l = new Label(title, style);
			switch (align) {
				case Align.center:
					l.setPosition(
							TitleItem.this.getX() + TitleItem.this.getWidth()
									/ 2 - l.getWidth() / 2,
							TitleItem.this.getY() + TitleItem.this.getHeight()
									/ 2 - l.getHeight() / 2);
					break;
				case Align.left:
					l.setPosition(TitleItem.this.getX() + 20,
							TitleItem.this.getY() + TitleItem.this.getHeight()
									/ 2 - l.getHeight() / 2);
					break;
				case Align.right:
					l.setPosition(
							TitleItem.this.getX() + TitleItem.this.getWidth()
									- l.getWidth() - 20,
							TitleItem.this.getY() + TitleItem.this.getHeight()
									/ 2 - l.getHeight() / 2);
					break;
				default:
					break;
			}
			TitleItem.this.addActor(l);
		}

		public TitleItem(String title, float padLeft, float padTop,
				float padBottom, Color color) {
			TitleItem.this.setWidth(ListView.this.getWidth());
			TitleItem.this.setHeight(30 + padTop + padBottom);
			LabelStyle style = new LabelStyle();
			style.font = Assets.instance.fontFactory.getRegular20();
			style.fontColor = color;
			Label l = new Label(title, style);
			l.setPosition(TitleItem.this.getX() + padLeft,
					TitleItem.this.getY() + padBottom);
			TitleItem.this.addActor(l);
		}
	}
}
