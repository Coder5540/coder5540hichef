package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.listener.ItemListener;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.IViews;
import com.aia.hichef.views.View;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class SubMenuView extends View {
	Image	bg;
	Table	content;

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public SubMenuView buileName(String name) {
		super.setName(name);
		this.name = name;
		return this;
	}

	public SubMenuView buildBound(Rectangle bound) {
		super.setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public SubMenuView buildComponent() {
		bg = new Image(new NinePatch(Assets.instance.uiP.transparent2));
		bg.setSize(getWidth(), getHeight());
		content = new Table();
		content.top().padTop(10).padBottom(10);
		content.setHeight(20);
		content.setTransform(true);
		content.setScale(0);
		addActor(bg);
		addActor(content);
		bg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
				IViews view = _viewController
						.getView(StringSystem.VIEW_SEARCHRESULT);
				if (view != null)
					view.show();
				super.clicked(event, x, y);
			}
		});
		return this;
	}

	public void setWidthContent(float width) {
		content.setWidth(width);
		content.setX(getX() + getWidth() - 10 - content.getWidth());
	}

	public void setBackground(Drawable bg) {
		content.setBackground(bg);
	}

	public void addButton(Image bg, String title, LabelStyle style,
			ItemListener listener) {
		LableButton btn = new LableButton(bg, title, style, content.getWidth(),
				40);
		content.add(btn).center();
		content.row();
		content.setHeight(content.getHeight() + btn.getHeight());
		content.setY(getY() + getHeight() - content.getHeight());
		content.setOrigin(content.getWidth(), getHeight());
		btn.setListener(listener);
	}

	public void removeAll() {
		content.clear();
		content.setHeight(20);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void show() {
		content.clearActions();
		setVisible(true);
		content.addAction(Actions.scaleTo(1, 1, 0.5f, Interpolation.swingOut));
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
	}

	class LableButton extends Group {
		Vector2	touchPoint	= new Vector2();
		Image	bg;

		public LableButton(Image bg, String title, LabelStyle style,
				float width, float height) {
			setSize(width, height);
			this.bg = bg;
			bg.setSize(width, height);
			bg.getColor().a = 0;
			bg.setPosition(getX(), getY());
			Label lb = new Label(title, style);
			lb.setPosition(getX() + 10,
					getY() + getHeight() / 2 - lb.getHeight() / 2);
			lb.setAlignment(Align.center);
			addActor(bg);
			addActor(lb);
		}

		public void setListener(final ItemListener listener) {
			this.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 1;
					touchPoint.set(x, y);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bg.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 10)) {
						listener.onItemClick();
					}
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 10)
							&& !touchPoint.isZero()) {
						touchPoint.set(0, 0);
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}
	}

}
