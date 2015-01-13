package com.aia.hichef.ui.n;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class TabView extends Table {
	public static int DEFAULT_POS_X = 50;
	public static float TIME_MOVE = 0.4f;
	private ArrayList<TabItem> listTab;
	private Flag flag;

	public MyScrollPanel panelContent;
	public MyScrollPanel panelTitle;
	Table tableContent, tableTitle;
	int indexSelect = 1, indexContent = 1;

	private float sizeXAfterSelect = 0, sizeXBefore = 0;
	float startSelectTittleX;

	Texture bg_ninepatch;

	private boolean isScrollH = false, isScrollV = false, isCheckTypeScroll;
	private float startX, startY;

	public TabView(float x, float y, float width, float height) {
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, new Color(Color.WHITE))));
		listTab = new ArrayList<TabItem>();
		flag = new Flag();

		flag.setHeight(0.8f * flag.getHeight());
		addActor(flag);
		setPosition(x, y);
		setSize(width, height);
		tableContent = new Table();
		tableTitle = new Table();
		panelContent = new MyScrollPanel(tableContent) {
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (isCheckTypeScroll) {
					if (Math.abs(x - startX) > 15) {
						panelContent.isCanTouch = true;
						for (TabItem item : listTab) {
							item.body.setTouchable(false);
						}
						isCheckTypeScroll = false;
					}
					if (Math.abs(y - startY) > 15) {
						panelContent.isCanTouch = false;
						for (TabItem item : listTab) {
							item.body.setTouchable(true);
						}
						isCheckTypeScroll = false;
					}
				}
				super.pan(event, x, y, deltaX, deltaY);
			}
		};
		panelContent.addListener(new ActorGestureListener() {
			public boolean handle(Event event) {
				if (super.handle(event)) {
					if (((InputEvent) event).getType() == InputEvent.Type.touchDown) {
						isContentMoving = true;
						isTitleWrong = false;
						panelContent.isSetStart = false;
						panelTitle.isSetStart = false;
						try {
							startSelectTittleX = listTab.get(indexContent).title
									.getX() - panelTitle.getScrollX();
						} catch (Exception e) {

						}

					}
					if (((InputEvent) event).getType() == InputEvent.Type.touchUp) {
						isContentMoved = true;
						isContentMoving = false;
						panelContent.isCanTouch = true;
						panelContent.disableMove();
						for (TabItem item : listTab) {
							item.body.setTouchable(false);
						}
						isCheckTypeScroll = false;
						if (Math.abs(panelContent.getVxFling()) > 1200) {
							// System.out.println("vflingx="
							// + panelContent.getVxFling());
							moveTo(indexContent
									- (int) (panelContent.getVxFling() / Math
											.abs(panelContent.getVxFling())),
									false);
						}
					}
					return true;
				}
				return false;
			}

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				startX = x;
				startY = y;
				isCheckTypeScroll = true;
				super.touchDown(event, x, y, pointer, button);
			}

		});
		panelContent.setBounds(0, 0, getWidth(), getHeight()
				- TabTitle.TITLE_TAB_HEIGHT);
		tableContent.setBounds(0, 0, getWidth(), getHeight()
				- TabTitle.TITLE_TAB_HEIGHT);
		tableContent.top();
		panelTitle = new MyScrollPanel(tableTitle);
		panelTitle.setBounds(0, getHeight() - TabTitle.TITLE_TAB_HEIGHT,
				getWidth(), TabTitle.TITLE_TAB_HEIGHT);
		panelTitle.setY(getY() + getHeight() - TabTitle.TITLE_TAB_HEIGHT);
		tableTitle.left();
		panelTitle.addListener(new ActorGestureListener() {
			public boolean handle(Event event) {
				if (super.handle(event)) {
					if (((InputEvent) event).getType() == InputEvent.Type.touchDown) {
						isTitleMoving = true;
						isTitleWrong = true;
						panelContent.isSetStart = false;
						panelTitle.isSetStart = false;
					}
					if (((InputEvent) event).getType() == InputEvent.Type.touchUp) {
						isTitleMoved = true;
						isTitleMoving = false;
					}
					return true;
				}
				return false;
			}
		});
		panelTitle.setScrollingDisabled(false, true);
		panelContent.setScrollingDisabled(false, true);
		panelContent.isFreeMove = false;
		flag.setY(panelTitle.getY());
		flag.setX(0);
		flag.setWidth(0);

		addActor(panelContent);
		addActor(panelTitle);
	}

	boolean isContentMoved = false, isContentMoving = false,
			isTitleMoved = false, isTitleMoving, isTitleWrong = false;

	@Override
	public void draw(Batch batch, float parentAlpha) {
		update();
		super.draw(batch, parentAlpha);
	}

	public void touchDown() {
		isContentMoving = true;
		isTitleWrong = false;
		startSelectTittleX = listTab.get(indexContent).title.getX()
				- panelTitle.getScrollX();
	}

	public void touchUp() {
		isContentMoved = true;
		isContentMoving = false;
	}

	public void scroll(float deltaX, float deltaY) {
		panelContent.scrollX(deltaX);
		panelContent.scrollY(deltaY);
	}

	boolean isMoveTarget = true;
	int target = 1;

	public void update() {
		if (!isMoveTarget) {
			moveTo(target, isTelepot);
			isMoveTarget = true;
		}
		if (isContentMoved && !isTitleWrong) {
//			indexContent++;
			if ((int) panelContent.getScrollX() % (int) getWidth() > getWidth() / 2) {
				indexContent = (int) panelContent.getScrollX()
						/ (int) getWidth() + 1;
			} else {
				indexContent = (int) panelContent.getScrollX()
						/ (int) getWidth();
			}
			flag.setSize(listTab.get(indexContent).title.getWidth(),
					flag.getHeight());
			moveToContent(false);
			isContentMoved = false;
			indexSelect = indexContent;
			moveTitleTo(indexSelect, false);
		}
		float delta = (panelContent.getScrollX() / getWidth() - indexContent)
				* getWidth();
		if (isContentMoving && !isTitleWrong) {
			panelTitle.scrollTo(-DEFAULT_POS_X + getDis(0, indexSelect) + delta
					* listTab.get(indexSelect).title.getWidth() / getWidth(),
					panelTitle.getY(), panelTitle.getWidth(),
					panelTitle.getHeight());
		}
		calcuDistanse(indexSelect);
		if (isTitleWrong) {
			flag.setX(listTab.get(indexSelect).title.getX()
					- panelTitle.getScrollX());
		} else {
			if (!isMovingLeft() && indexSelect < listTab.size() - 1) {
				calcuDistanse(indexSelect + 1);
			}
			float localDelta;
			if (Math.abs(delta) > getWidth())
				localDelta = getWidth();
			else
				localDelta = Math.abs(delta);
			if (!isMovingLeft() && indexContent < listTab.size() - 1) {
				float deltaSize = listTab.get(indexSelect + 1).title.getWidth()
						- listTab.get(indexSelect).title.getWidth();

				flag.setSize(listTab.get(indexSelect).title.getWidth()
						+ deltaSize * localDelta / getWidth(), flag.getHeight());
			} else if (indexContent > 0) {
				float deltaSize = listTab.get(indexSelect - 1).title.getWidth()
						- listTab.get(indexSelect).title.getWidth();
				flag.setSize(listTab.get(indexSelect).title.getWidth()
						+ deltaSize * localDelta / getWidth(), flag.getHeight());
			}
			if (isMiddle()) {
				flag.setX(DEFAULT_POS_X);

			} else if (isLeftest()) {
				if (isMovingLeft() && indexSelect > 0) {
					flag.setX(getDis(0, indexSelect) + delta
							* listTab.get(indexSelect - 1).title.getWidth()
							/ getWidth());
				} else {
					flag.setX(getDis(0, indexSelect) + delta
							* listTab.get(indexSelect).title.getWidth()
							/ getWidth());
				}
			} else {
				if (isRightest()) {
					if (isMovingLeft()) {
						flag.setX(getWidth() - getOffsetRight(indexSelect)
								+ delta
								* listTab.get(indexSelect - 1).title.getWidth()
								/ getWidth());
					} else if (indexSelect < listTab.size() - 1) {
						flag.setX(startSelectTittleX
								+ delta
								* Math.min(listTab.get(indexSelect).title
										.getWidth(), getWidth()
										- getOffsetRight(indexSelect + 1)
										- startSelectTittleX) / getWidth());
					}
				}
			}
		}
	}

	public boolean isMovingLeft() {
		float delta = (panelContent.getScrollX() / getWidth() - indexContent)
				* getWidth();
		if (delta > 0) {
			return false;
		}
		return true;
	}

	public void moveToContent(boolean isFast) {
		if (isFast) {
			panelContent.setStartX(-indexContent * getWidth());
		} else
			panelContent.scrollTo(indexContent * getWidth(),
					panelContent.getY(), panelContent.getWidth(),
					panelContent.getHeight());
	}

	private void moveTo(int index, boolean isTelepot) {
		indexContent = index;
		indexSelect = index;
		isContentMoved = true;
		isContentMoving = false;
		moveToContent(isTelepot);
		moveTitleTo(indexSelect, isTelepot);
		isTitleWrong = false;
	}

	boolean isTelepot;

	public void scrollTo(int target, boolean isTelepot) {
		this.target = target;
		if (!isTelepot) {
			panelContent.isSetStart = false;
			panelTitle.isSetStart = false;
		}
		isMoveTarget = false;
		this.isTelepot = isTelepot;
	}

	public void addTab(TabItem item) {
		listTab.add(item);
		item.index = listTab.size() - 1;
		tableTitle.add(item.title).width(item.title.getWidth())
				.height(item.title.getHeight());
		addActor(item.body.content);
		tableContent.add(item.body.content).width(getWidth()).top();

		item.body.addLoading(item.body.content, item.index * getWidth());
		final int in = item.index;
		item.title.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// DialogCustom dialog = new DialogCustom(" ");
				// dialog.button("chào", new Runnable() {
				// public void run() {
				// Toast.makeText(getStage(),
				// "Hi, my name is Sun, i ll help you to day",
				// Toast.LENGTH_SHORT);
				// }
				// });
				// dialog.text("Welcome to Hichef");
				// dialog.button("Cancel");
				// dialog.show(getStage());
				if (!TabTitle.isMoved) {
					moveTo(in, false);
				}
			}
		});
		if (item.index >= indexSelect) {
			if (item.index == indexSelect) {
				flag.setY(listTab.get(indexSelect).title.getY()
						+ panelTitle.getY());
				flag.setX(listTab.get(indexSelect).title.getX());
				flag.setWidth(listTab.get(indexSelect).title.getWidth());
			}
			moveTitleTo(indexSelect, true);
			moveToContent(true);
			resetPosition();
		}
		flag.toFront();
		for (TabItem item2 : listTab) {
			// item2.body.clearListeners();
		}
	}

	boolean isRight = false, isLeft = false;

	public void moveTitleTo(int target, boolean isTelepot) {
		calcuDistanse(target);
		if (target >= 0 && target < listTab.size()) {
			if (isMiddle()) {
				if (isTelepot) {
					panelTitle.setStartX(DEFAULT_POS_X - getDis(0, target));
				} else
					panelTitle.scrollTo(-DEFAULT_POS_X + getDis(0, target),
							panelTitle.getY(), panelTitle.getWidth(),
							panelTitle.getHeight());
				isLeft = false;
				isRight = false;
			} else if (isLeftest()) {
				if (isTelepot) {
					panelTitle.setStartX(0);
				} else
					panelTitle.scrollTo(0, panelTitle.getY(),
							panelTitle.getWidth(), panelTitle.getHeight());
				isLeft = true;
			} else if (isRightest()) {
				if (isTelepot) {
					panelTitle.setStartX(-getOffsetRight(0)
							+ panelTitle.getWidth());
				} else
					panelTitle.scrollTo(
							getOffsetRight(0) - panelTitle.getWidth(),
							panelTitle.getY(), panelTitle.getWidth(),
							panelTitle.getHeight());
				isRight = true;
			}
			indexSelect = target;
		}
	}

	public void moveFlagTo(int target) {
		calcuDistanse(target);
		if (target >= 0 && target < listTab.size()) {
			if (isMiddle()) {
				flag.addAction(Actions.sizeTo(
						listTab.get(target).title.getWidth(), flag.getHeight(),
						TIME_MOVE, Interpolation.exp5));
				flag.addAction(Actions.moveTo(DEFAULT_POS_X, flag.getY(),
						TIME_MOVE, Interpolation.exp5));

				isLeft = false;
				isRight = false;
			} else if (isLeftest()) {
				panelTitle.scrollTo(0, panelTitle.getY(),
						panelTitle.getWidth(), panelTitle.getHeight());
				flag.addAction(Actions.moveTo(getDis(target, 0)
						+ TabTitle.MARGIN_X, flag.getY(), TIME_MOVE,
						Interpolation.exp5));
				isLeft = true;
			} else if (isRightest()) {

				for (TabItem item : listTab) {
					if (!isRight)
						panelTitle.scrollTo(
								getOffsetRight(0) - panelTitle.getWidth(),
								panelTitle.getY(), panelTitle.getWidth(),
								panelTitle.getHeight());
				}
				if (!isRight)
					flag.addAction(Actions.moveTo(getWidth()
							- getOffsetRight(target), flag.getY(), TIME_MOVE,
							Interpolation.exp5));
				else {
					flag.addAction(Actions.moveTo(
							listTab.get(target).title.getX(), flag.getY(),
							TIME_MOVE, Interpolation.exp5));
				}
				isRight = true;
			}
			indexSelect = target;
		}
	}

	float getDis(int base, int target) {
		return listTab.get(target).title.getX()
				- listTab.get(base).title.getX();
	}

	private void calcuDistanse(int indexSelect) {
		sizeXAfterSelect = 0;
		sizeXBefore = 0;
		for (int i = 0; i < listTab.size(); i++) {
			if (i >= indexSelect)
				sizeXAfterSelect += listTab.get(i).title.getWidth();
			else if (i < indexSelect) {
				sizeXBefore += listTab.get(i).title.getWidth();
			}
		}
	}

	public float getOffsetLeft(int indexSelect) {
		float offset = 0;
		for (int i = 0; i < listTab.size(); i++) {
			if (i < indexSelect) {
				offset += listTab.get(i).title.getWidth();
			}
		}
		return offset;
	}

	public float getOffsetRight(int indexSelect) {
		float offset = 0;
		for (int i = 0; i < listTab.size(); i++) {
			if (i >= indexSelect) {
				offset += listTab.get(i).title.getWidth();
			}
		}
		return offset;
	}

	public void resetPosition() {

		for (int i = 0; i < listTab.size(); i++) {
			listTab.get(i).title.setY(getHeight()
					- listTab.get(i).title.getHeight());
		}
		calcuDistanse(indexSelect);
		if (indexSelect > 0) {
			float offset = 0;
			if (isMiddle()) {
				panelTitle.scrollTo(DEFAULT_POS_X, panelTitle.getY(),
						panelTitle.getWidth(), panelTitle.getHeight());
			} else {
				if (isLeftest()) {
					panelTitle.scrollTo(0, panelTitle.getY(),
							panelTitle.getWidth(), panelTitle.getHeight());
				} else if (isRightest()) {
					panelTitle.scrollTo(
							getOffsetRight(0) - panelTitle.getWidth(),
							panelTitle.getY(), panelTitle.getWidth(),
							panelTitle.getHeight());
				}

			}
		}

	}

	private boolean isMiddle() {
		if (sizeXAfterSelect > getWidth() - DEFAULT_POS_X
				&& sizeXBefore > DEFAULT_POS_X)
			return true;
		return false;
	}

	private boolean isLeftest() {
		if (sizeXBefore < DEFAULT_POS_X)
			return true;
		return false;
	}

	private boolean isRightest() {
		if (sizeXAfterSelect < getWidth() - DEFAULT_POS_X)
			return true;
		return false;
	}

	public class Flag extends Group {

		private Image img;

		public Flag() {
			img = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					new Color(Color.RED)));
			img.setHeight(8);
			getColor().a = 0.8f;
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);

			img.setPosition(getX(), getY());
			img.draw(batch, parentAlpha);
			img.setRotation(getRotation());
			img.setColor(getColor());
			img.setScale(getScaleX(), getScaleY());
		}

		@Override
		public float getWidth() {
			return img.getWidth();
		}

		@Override
		public float getHeight() {
			return img.getHeight();
		}

		@Override
		public void setSize(float width, float height) {
			img.setSize(width, height);
		}

		@Override
		public void setWidth(float width) {
			img.setWidth(width);
		}

		@Override
		public void setHeight(float height) {
			img.setHeight(height);
		}
	}

	public void removeAllTab() {
		for (TabItem item : listTab) {
			item.title.remove();
			item.body.content.remove();
		}
		listTab.removeAll(listTab);
	}
}
