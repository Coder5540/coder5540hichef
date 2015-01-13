package com.aia.hichef.components;

import com.aia.hichef.listener.ItemListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Item extends Group {

	public int		index;

	public boolean	canClick	= true;

	public Item(final ItemListener listener) {
		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				touchPoint.set(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (touchPoint.epsilonEquals(x, y,50)) {
					listener.onItemClick();
				}
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				
				
				if (!touchPoint.epsilonEquals(x, y, 30)){
					touchPoint.set(0, 0);
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
	}

	public Item() {

	}

	Vector2	touchPoint	= new Vector2();

	public void setListener(final ItemListener listener) {
		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				touchPoint.set(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (touchPoint.epsilonEquals(x, y,10)) {
					listener.onItemClick();
				}
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if (!touchPoint.epsilonEquals(x, y, 10) && !touchPoint.isZero()){
					touchPoint.set(0, 0);
				}
				super.touchDragged(event, x, y, pointer);
			}
		});
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
