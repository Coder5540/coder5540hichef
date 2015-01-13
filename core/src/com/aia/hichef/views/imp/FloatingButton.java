package com.aia.hichef.views.imp;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.Img;
import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.enums.ViewState;
import com.aia.hichef.views.IViewController;
import com.aia.hichef.views.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class FloatingButton extends View {
	public Img		button;
	public boolean	auto		= false;
	float			Speed		= 300;
	public Vector2	velocity	= new Vector2();

	boolean			showContent	= false;
	Table			table;

	public FloatingButton() {
		super();
	}

	@Override
	public void build(Stage stage, IViewController viewController) {
		super.build(stage, viewController);
	}

	public FloatingButton buildName(String name) {
		this.name = name;
		setName(name);
		return this;
	}

	public FloatingButton buildBound(Rectangle bound) {
		setBounds(bound.x, bound.y, bound.width, bound.height);
		return this;
	}

	public FloatingButton buildComponent() {
		button = new Img(Assets.instance.uiA.reg_floating_button_uncheck, 70, 70);
		button.setPosition(new Vector2(getWidth() / 2, 20));
		button.addListener(new ActorGestureListener() {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				auto = false;
				button.setPosition(button.getX() + deltaX, button.getY() + deltaY);
			}

			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				showContent(!showContent, null);
			}

			@Override
			public void fling(InputEvent event, float velocityX, float velocityY, int button) {
				auto = true;
				Speed = 300;
				velocity.set(-velocityX / 10, velocityY / 10);
			}
		});

		table = new Table();
		table.setSize(getWidth(), getHeight());

		Table subTable = new Table();
		subTable.setBackground(new NinePatchDrawable(new NinePatch(Assets.instance.uiM.bg_ninepatch)));
		for (int i = 0; i < 10; i++) {

			Img img = new Img(Assets.instance.uiM.bg_ninepatch);
			img.setColor(Color.GRAY);

			subTable.add(img).pad(10).width(100).height(100);
			if (i == 4)
				subTable.row();
		}
		subTable.setSize(table.getWidth(), table.getHeight());
		ScrollPane scroll = new ScrollPane(subTable);
		scroll.setBounds(380, 300, 200, 200);

		table.add(scroll);
		add(table).center().padLeft(getWidth() / 6).padRight(getWidth() / 6);
		addActor(button);
		showContent(false, null);

		addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				if ((table).hit(x, y, true) != null) {
					System.out.println("hit");
				} else {
					System.out.println("not hit");
				}
				if (getViewState() == ViewState.SHOW && !event.getListenerActor().getName().equalsIgnoreCase(getName())) {

					System.out.println("hide");
					hide();
				}
			}
		});

		return this;
	}

	@Override
	public void update() {
		super.update();
		if (auto)
			autoPosition();
	}

	void autoPosition() {
		if (button.getX() <= getX() + 5) {
			button.setX(getX() + 5);
			velocity.set(0, 0);
			return;
		}

		if (button.getX() + button.getWidth() >= getX() + getWidth() - 5) {
			button.setX(getX() + getWidth() - 5 - button.getWidth());
			velocity.set(0, 0);
			return;
		}

		if (button.getY() <= getY() + 5) {
			button.setY(getY() + 5);
			velocity.set(0, 0);
			return;
		}
		if (button.getY() + button.getHeight() >= getY() + getHeight() - 5) {
			button.setY(getY() + getHeight() - 5 - button.getHeight());
			velocity.set(0, 0);
			return;
		}
		float dx_l = button.getX() + button.getWidth() / 2 - getX();
		float dx_r = getX() + getWidth() - button.getX() - button.getWidth() / 2;

		float dy_d = button.getY() - getY();
		float dy_u = getY() + getHeight() - button.getY() - button.getHeight();

		float min = Math.min(Math.min(dx_r, dx_l), Math.min(dy_u, dy_d));

		Speed += 200 * Gdx.graphics.getDeltaTime();
		if (min == dx_l) {
			button.setX(button.getX() - Speed * Gdx.graphics.getDeltaTime());
			button.setY(button.getY() + velocity.y * Gdx.graphics.getDeltaTime());
			velocity.x = 0;
		}
		if (min == dx_r) {
			button.setX(button.getX() + Speed * Gdx.graphics.getDeltaTime());
			button.setY(button.getY() + velocity.y * Gdx.graphics.getDeltaTime());
			velocity.x = 0;

		}

		if (min == dy_u) {
			button.setY(button.getY() + Speed * Gdx.graphics.getDeltaTime());
			button.setX(button.getX() - velocity.x * Gdx.graphics.getDeltaTime());
			velocity.y = 0;
		}
		if (min == dy_d) {
			button.setY(button.getY() - Speed * Gdx.graphics.getDeltaTime());
			button.setX(button.getX() + velocity.x * Gdx.graphics.getDeltaTime());
			velocity.y = 0;
		}
	}

	@Override
	public void show( ) {
		super.show();
		setVisible(true);
		button.setColor(button.getColor().r, button.getColor().g, button.getColor().b, 0f);
	}

	@Override
	public void hide() {
		super.hide();
		setVisible(false);
		showContent(false, AnimationType.FADE);
		button.setColor(button.getColor().r, button.getColor().g, button.getColor().b, 0f);
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	public void showContent(boolean show, AnimationType aminationType) {
		buildContent(show);
	}

	public void buildContent(boolean show) {
		this.showContent = show;
		table.setVisible(show);
	}
}
