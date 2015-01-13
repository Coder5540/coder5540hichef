package com.aia.hichef.screens;

import java.util.ArrayList;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.components.GalleryViewHorizontal;
import com.aia.hichef.components.GalleryViewVertical;
import com.aia.hichef.components.PageScrollPaneHorizontal;
import com.aia.hichef.components.Toast;
import com.aia.hichef.enums.Constants;
import com.aia.hichef.listener.OnPickTimeListener;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screenhelper.GameCore;
import com.aia.hichef.ui.IPickTime;
import com.aia.hichef.ui.PickTime;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public class TestStage extends AbstractGameScreen {
	MyListView	myList	= new MyListView();
	IPickTime	pickTime;
	LabelStyle style_normal = new LabelStyle(
			Assets.instance.fontFactory.getMedium20(), new Color(
					255 / 255f, 0 / 255f, 0 / 255f, 1));
	public TestStage(GameCore game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		// myList.buildComponent();
		// stage.addActor(myList);

		// buildPickTime();

		buildTest();
		// buildMyView();
		// buildTestLabel();
		AbstractGameScreen.toast.builToast();
	}

	void buildTestLabel() {
		BitmapFont font = Assets.instance.fontFactory.getMedium20();
		font.setMarkupEnabled(true);
		LabelStyle style = new LabelStyle(font, Color.WHITE);
		style.font.setMarkupEnabled(true);
		Label label = new Label("[RED]Test [BLUE]Col[GREEN]or", style);
		label.setPosition(100, 100);
		stage.addActor(label);

	}

	public void buildPickTime() {
		pickTime = new PickTime();
		pickTime.build(stage);
		ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> months = new ArrayList<String>();
		ArrayList<String> years = new ArrayList<String>();

		dates.add("28");
		dates.add("29");
		dates.add("30");
		for (int i = 0; i < 30; i++) {
			dates.add("" + (i + 1));
		}
		dates.add("1");
		dates.add("2");
		dates.add("3");

		months.add("Oct");
		months.add("Nov");
		months.add("Dec");
		months.add("Jan");
		months.add("Feb");
		months.add("Mar");
		months.add("Apr");
		months.add("May");
		months.add("Jun");
		months.add("Jul");
		months.add("Aug");
		months.add("Sep");
		months.add("Oct");
		months.add("Nov");
		months.add("Dec");
		months.add("Jan");
		months.add("Feb");
		months.add("Mar");

		for (int i = 0; i < 150; i++) {
			years.add("" + (1900 + i));
		}
		pickTime.setFirstValues(dates).setSecondValues(months)
				.setThirdValues(years).validate().setDefaultDay(8)
				.setDefaultMonth(2).setDefaultYear(92);
		pickTime.show(null);

		pickTime.setListener(new OnPickTimeListener() {

			@Override
			public void onOk(String date, String month, String year) {
				Toast.makeText(stage, "On Ok : " + date + "/" + month + "/"
						+ year, Toast.LENGTH_SHORT);
			}

			@Override
			public void onCancel(String date, String month, String year) {
				Toast.makeText(stage, "On Cancel : " + date + "/" + month + "/"
						+ year, Toast.LENGTH_SHORT);
			}
		});
	}

	GalleryViewHorizontal	titleView, homeView, subViewHorizontal;
	GalleryViewVertical		subViewVertical;
	Array<String>			tabs				= new Array<String>();
	Array<Label>			labels				= new Array<Label>();
	public int				currentSelection	= 0;
	Color					colorSelect			= Color.BLACK;
	Color					colorUnselect		= Color.WHITE;

	void buildMyView() {
		LabelStyle style_normal = new LabelStyle(
				Assets.instance.fontFactory.getMedium20(), new Color(
						255 / 255f, 255 / 255f, 255 / 255f, 1));
		Array<String> tabs = new Array<String>();
		tabs.add("Nguyên Liệu");
		tabs.add("Cách Làm");
		tabs.add("Góp Ý");
		tabs.add("Xem Video");

		Table title = new Table();
		title.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		title.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR);
		title.setY(Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR);
		stage.addActor(title);

		Table content = new Table();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		content.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
				- Constants.HEIGHT_ACTIONBAR);
		stage.addActor(content);

		titleView = new GalleryViewHorizontal(title, 3);
		titleView.pages.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		for (int i = 0; i < tabs.size; i++) {
			final int index = i;
			Table table = titleView.newPage(
					new NinePatchDrawable(new NinePatch(
							Assets.instance.uiM.bg_ninepatch, new Color(
									255 / 255f, 0 / 255f, 0 / 255f, .4f))))
					.pad(20);
			Label label = new Label(tabs.get(i), style_normal);
			label.setAlignment(Align.center);
			label.setSize(table.getWidth(), table.getHeight());
			table.setTouchable(Touchable.enabled);
			table.addListener(new ActorGestureListener() {
				@Override
				public void tap(InputEvent event, float x, float y, int count,
						int button) {
					onTitleClick(index);
					super.tap(event, x, y, count, button);
				}
			});
			table.center().add(label).expand().fill();
			labels.add(label);
		}
		onHighlightSelect(0);
		homeView = new GalleryViewHorizontal(content, 2);
		content.setTouchable(Touchable.enabled);
		content.addListener(new ActorGestureListener() {

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
			}
		});

		for (int i = 0; i < tabs.size; i++) {
			final int index = i;
			Table table = homeView.newPage(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, Color.GRAY)));
			table.setTouchable(Touchable.enabled);
			table.addListener(new ActorGestureListener() {

				@Override
				public void touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("Touch Down Element " + index);
				}

				@Override
				public void tap(InputEvent event, float x, float y, int count,
						int button) {
					onContentClick(index);
					super.tap(event, x, y, count, button);
				}

			});
		}

	}

	void buildTest() {
	

		tabs.add("Nguyên Liệu");
		tabs.add("Cách Làm");
		tabs.add("Góp Ý");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");
		tabs.add("Xem Video");

		Table title = new Table();
		title.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		title.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR);
		title.setY(Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR);
		stage.addActor(title);

		Table content = new Table();
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch)));
		content.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
				- Constants.HEIGHT_ACTIONBAR);
		stage.addActor(content);

		titleView = new GalleryViewHorizontal(title, 3);
		titleView.pages.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				if (event.getListenerActor() != null
						&& event.getListenerActor().getName() != null)
					System.out.println(event.getListenerActor().getName());
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		for (int i = 0; i < tabs.size; i++) {
			final int index = i;
			Table table = titleView.newPage(
					new NinePatchDrawable(new NinePatch(
							Assets.instance.uiM.bg_ninepatch, new Color(
									255 / 255f, 0 / 255f, 0 / 255f, .4f))))
					.pad(20);
			Label label = new Label(tabs.get(i), style_normal);
			label.setAlignment(Align.center);
			table.setTouchable(Touchable.enabled);
			table.addListener(new ActorGestureListener() {
				@Override
				public void tap(InputEvent event, float x, float y, int count,
						int button) {
					onTitleClick(index);
					super.tap(event, x, y, count, button);
				}

			});
			table.center().add(label).expand().fill();
		}

		homeView = new GalleryViewHorizontal(content, 2);
		Table subViertical = homeView.newPage(new NinePatchDrawable(
				new NinePatch(Assets.instance.uiM.bg_ninepatch, Color.GRAY)));
		homeView.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.GRAY)));
		Table subHorizontal = homeView.newPage(new NinePatchDrawable(
				new NinePatch(Assets.instance.uiM.bg_ninepatch, Color.GRAY)));
		homeView.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.GRAY)));
		homeView.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.GRAY)));

		subViewHorizontal = new GalleryViewHorizontal(subHorizontal, 1);
		subHorizontal.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				if (event.getListenerActor() != null
						&& event.getListenerActor().getName() != null)
					System.out.println(event.getListenerActor().getName());
				return super.touchDown(event, x, y, pointer, button);
			}

		});
		subViewHorizontal.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.RED)));
		subViewHorizontal.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.RED)));
		subViewHorizontal.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.RED)));
		subViewHorizontal.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.RED)));
		subViewHorizontal.newPage(new NinePatchDrawable(new NinePatch(
				Assets.instance.uiM.bg_ninepatch, Color.RED)));

		subViertical.setHeight(content.getHeight());
		subViewVertical = new GalleryViewVertical(subViertical, 2);
		subViertical.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (event.getListenerActor() != null
						&& event.getListenerActor().getName() != null)
					System.out.println(event.getListenerActor().getName());
				event.stop();
				return super.touchDown(event, x, y, pointer, button);
			}

		});
		subViewVertical.newPage(
				new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, Color.CYAN))).add(
				new Label("1", style_normal));

		subViewVertical.newPage(
				new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, Color.CYAN))).add(
				new Label("2", style_normal));
		subViewVertical.newPage(
				new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, Color.CYAN))).add(
				new Label("3", style_normal));
		subViewVertical.newPage(
				new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, Color.CYAN))).add(
				new Label("4", style_normal));
		subViewVertical.newPage(
				new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, Color.CYAN))).add(
				new Label("5", style_normal));
	}

	public void onHighlightSelect(int index) {
		for (int i = 0; i < labels.size; i++) {
			if (i == index)
				labels.get(i).setColor(colorSelect);
			else
				labels.get(i).setColor(colorUnselect);
		}
	}

	void onTitleClick(int index) {
		homeView.pages.focusOnPage(index);
		onHighlightSelect(index);
	}

	void onContentClick(int index) {
		titleView.pages.focusOnPage(index);
		onHighlightSelect(index);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.NUM_1:
				// myList.scroll.focusOnPage(1);
				break;
			case Keys.NUM_2:
				// myList.scroll.focusOnPage(2);
				pickTime.setDefaultDay(2);
				break;
			case Keys.NUM_3:
				// myList.scroll.focusOnPage(3);
				pickTime.setDefaultDay(3);
				break;
			case Keys.NUM_4:
				// myList.scroll.focusOnPage(4);
				pickTime.setDefaultDay(4);
				break;
			case Keys.NUM_5:
				// myList.scroll.focusOnPage(5);
				pickTime.setDefaultDay(5);
				break;
			case Keys.NUM_6:
				// myList.scroll.focusOnPage(6);
				pickTime.setDefaultDay(6);
				break;
			case Keys.NUM_7:
				// myList.scroll.focusOnPage(6);
				pickTime.setDefaultDay(15);
				pickTime.setDefaultMonth(5);
				pickTime.setDefaultYear(100);
				break;

			default:
				break;
		}
		return super.keyDown(keycode);
	}

	public class MyListView extends Group {
		public Array<String>			titles;
		public PageScrollPaneHorizontal	scroll;
		public PageScrollPaneHorizontal	scrollTitle;
		Bar								bar;
		Image							imgBar;

		boolean							wasPanDragFlingTitle	= false;
		boolean							wasPanDragFling			= false;

		@Override
		public void act(float delta) {
			super.act(delta);
			if (wasPanDragFlingTitle && !scrollTitle.isPanning()
					&& !scrollTitle.isDragging() && !scrollTitle.isFlinging()) {
				wasPanDragFlingTitle = false;
				scrollTitle.scrollToPage();
				scroll.setScrollX(scrollTitle.getScrollX() * 3);
				scroll.scrollToPage();
			} else {
				if (scrollTitle.isPanning() || scrollTitle.isDragging()
						|| scrollTitle.isFlinging()) {
					wasPanDragFlingTitle = true;
				}
			}

			if (wasPanDragFling && !scroll.isPanning() && !scroll.isDragging()
					&& !scroll.isFlinging()) {
				wasPanDragFling = false;
				if (scroll.getScrollX() > 0) {
					System.out.println("========= Scroll Left ==========="
							+ scroll.getScrollX() + " _ " + scroll.getX());
					bar.moveRight();

				}
				if (scroll.getScrollX() < 0) {
					System.out.println("========= Scroll Right ==========="
							+ scroll.getScrollX() + " _ " + scroll.getX());
					bar.moveLeft();
				}

				scroll.scrollToPage();

			} else {
				if (scroll.isPanning() || scroll.isDragging()
						|| scroll.isFlinging()) {
					wasPanDragFling = true;
				}
			}

		}

		void buildComponent() {

			LabelStyle style_normal = new LabelStyle(
					Assets.instance.fontFactory.getMedium20(), new Color(
							77 / 255f, 77 / 255f, 77 / 255f, 1));
			Array<String> titles = new Array<String>();
			titles.add("Góp Ý 1");
			titles.add("Góp Ý 2");
			titles.add("Góp Ý 3");
			titles.add("Góp Ý 4");
			titles.add("Góp Ý 5");
			titles.add("Góp Ý 6");

			Table containerTitle = new Table();
			containerTitle.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(0 / 255f,
							225 / 255f, 0 / 255f, 1f))));
			containerTitle.setSize(Constants.WIDTH_SCREEN,
					Constants.HEIGHT_ACTIONBAR);
			containerTitle.setPosition(0, Constants.HEIGHT_SCREEN
					- containerTitle.getHeight());

			int pageSpacing = 4;
			scrollTitle = new PageScrollPaneHorizontal(3,
					Constants.WIDTH_SCREEN);
			scrollTitle.setFlingTime(0.1f);
			scrollTitle.setSmoothScrolling(true);
			scrollTitle.setPageSpacing(pageSpacing);
			scrollTitle.setScrollingDisabled(false, true);

			for (int l = 0; l < titles.size; l++) {
				Table levels = new Table();
				levels.setBackground(new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, new Color(0 / 255f,
								0 / 255f, 0 / 255f, 1))));
				Label label = new Label(titles.get(l), style_normal);
				label.setAlignment(Align.center);
				levels.add(label).expand().fill()
						.width(scrollTitle.elementWidth).height(100);
				scrollTitle.addPage(levels);
			}

			Table container = new Table();
			container.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(100 / 255f,
							100 / 255f, 100 / 255f, 1f))));
			container.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
					- Constants.HEIGHT_ACTIONBAR - 10);
			scroll = new PageScrollPaneHorizontal(1, Constants.WIDTH_SCREEN);
			scroll.setFlingTime(0.1f);
			scroll.setSmoothScrolling(true);
			scroll.setPageSpacing(5);
			for (int l = 0; l < titles.size; l++) {
				Table levels = new Table().pad(10);
				levels.setBackground(new NinePatchDrawable(new NinePatch(
						Assets.instance.uiM.bg_ninepatch, new Color(1f,
								0 / 255f, 0 / 255f, 1))));
				levels.defaults().pad(20);
				Label label = new Label("Page : " + (l + 1), style_normal);
				label.setAlignment(Align.center);
				levels.add(label).expand().fill().width(scroll.elementWidth);
				scroll.addPage(levels);
			}

			containerTitle.add(scrollTitle).expand().fill();
			container.add(scroll).expand().fill();

			Table barContent = new Table();
			barContent.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch, new Color(0 / 255f,
							0 / 255f, 0 / 255f, 1f))));
			barContent.setSize(Constants.WIDTH_SCREEN, 10);
			barContent.setPosition(0,
					Constants.HEIGHT_SCREEN - containerTitle.getHeight() - 10);

			bar = new Bar(barContent, 3);
			addActor(containerTitle);
			addActor(container);
			addActor(bar.getContent());
			barContent.toFront();
			bar.getContent().toFront();
		}

	}

	public int getPositionBar(float width) {
		return 1;
	}

	public class Bar {
		Table			bar;
		public Image	imgBar;
		public int		numberInWidth;
		public float	tabWidth;

		public Bar(Table bar, int numberInWidth) {
			super();
			this.bar = bar;
			this.numberInWidth = numberInWidth;
			tabWidth = bar.getWidth() / numberInWidth;
			buildImgBar();
		}

		public void buildImgBar() {
			imgBar = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch,
					new Color(0 / 255f, 225 / 255f, 0 / 255f, 1f)));
			imgBar.setSize(bar.getWidth() / numberInWidth, bar.getHeight());
			imgBar.setX(bar.getWidth() / numberInWidth);
			bar.addActor(imgBar);
		}

		public Table getContent() {
			return bar;
		}

		public void moveLeft() {
			if (getIndex() > 1) {
				imgBar.addAction(Actions.moveBy(-tabWidth, 0, .1f));
			}
		}

		public void moveRight() {
			if (getIndex() == numberInWidth) {
				validePosition(0);
			}
			if (getIndex() < numberInWidth) {
				imgBar.addAction(Actions.moveBy(tabWidth, 0, .1f));
			}
		}

		public int getIndex() {
			return (int) ((imgBar.getX() + imgBar.getWidth() / 2) / tabWidth) + 1;
		}

		public void validePosition(int index) {
			if (index < 0 || index > numberInWidth) {
				throw new IndexOutOfBoundsException("Sai Index");
			}
			imgBar.setX(tabWidth * index);
		}

	}

	public class SpecificView {

		GalleryViewHorizontal	titleView, homeView, subViewHorizontal;
		GalleryViewVertical		subViewVertical;
		Array<String>			tabs				= new Array<String>();
		Array<Label>			labels				= new Array<Label>();
		public int				currentSelection	= 0;
		Color					colorSelect			= Color.BLACK;
		Color					colorUnselect		= Color.WHITE;

		void buildMyView() {
			LabelStyle style_normal = new LabelStyle(
					Assets.instance.fontFactory.getMedium20(), new Color(
							255 / 255f, 255 / 255f, 255 / 255f, 1));
			Array<String> tabs = new Array<String>();
			tabs.add("Nguyên Liệu");
			tabs.add("Cách Làm");
			tabs.add("Góp Ý");
			tabs.add("Xem Video");

			Table title = new Table();
			title.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch)));
			title.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_ACTIONBAR);
			title.setY(Constants.HEIGHT_SCREEN - Constants.HEIGHT_ACTIONBAR);
			stage.addActor(title);

			Table content = new Table();
			content.setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.uiM.bg_ninepatch)));
			content.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
					- Constants.HEIGHT_ACTIONBAR);
			stage.addActor(content);

			titleView = new GalleryViewHorizontal(title, 3);
			titleView.pages.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					event.stop();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
			for (int i = 0; i < tabs.size; i++) {
				final int index = i;
				Table table = titleView.newPage(
						new NinePatchDrawable(new NinePatch(
								Assets.instance.uiM.bg_ninepatch, new Color(
										255 / 255f, 0 / 255f, 0 / 255f, .4f))))
						.pad(20);
				Label label = new Label(tabs.get(i), style_normal);
				label.setAlignment(Align.center);
				label.setSize(table.getWidth(), table.getHeight());
				table.setTouchable(Touchable.enabled);
				table.addListener(new ActorGestureListener() {
					@Override
					public void tap(InputEvent event, float x, float y,
							int count, int button) {
						onTitleClick(index);
						super.tap(event, x, y, count, button);
					}

				});
				table.center().add(label).expand().fill();
				labels.add(label);
			}
			onHighlightSelect(0);

			homeView = new GalleryViewHorizontal(content, 2);
			for (int i = 0; i < tabs.size; i++) {
				final int index = i;
				Table table = homeView.newPage(new NinePatchDrawable(
						new NinePatch(Assets.instance.uiM.bg_ninepatch,
								Color.GRAY)));
				table.setTouchable(Touchable.enabled);
				table.addListener(new ActorGestureListener() {
					@Override
					public void tap(InputEvent event, float x, float y,
							int count, int button) {
						onContentClick(index);
						super.tap(event, x, y, count, button);
					}

				});
			}

		}

	}

}
