package com.aia.hichef.screens;

import com.aia.hichef.assets.Assets;
import com.aia.hichef.screenhelper.AbstractGameScreen;
import com.aia.hichef.screenhelper.GameCore;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class TableTest extends AbstractGameScreen {
	public Table	root;

	public TableTest(GameCore game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		// buildTest1();
		buildTest2();
	}

	public void buildTest2() {
		root = new Table();
		root.setSize(400, 400);
		root.setPosition(40, 200);
		root.setOrigin(0, 400);
		stage.addActor(root);
		root.debug();
		
		root.defaults().height(60);
		Image image = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch));
		root.add(image).width(100).fillX().expand();
		root.defaults().height(120);
		Image image2 = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch));
		root.add(image2).width(100).fillX().expand();
		
		root.row();
		Image image3 = new Image(new NinePatch(Assets.instance.uiM.bg_ninepatch));
		root.add(image3).fillX().expand();
		root.row();
	}

	public void buildTest3() {
		root = new Table();
		root.setSize(400, 400);
		root.setPosition(40, 200);
		root.setOrigin(0, 400);

		stage.addActor(root);
		root.debug();
		root.top().left();
		LabelStyle style = new LabelStyle(Assets.instance.fontFactory.getMedium15(), Color.RED);
		Label text1 = new Label(
				"Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1  Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1 Nguyên Liệu 1",
				style);
		text1.setWrap(true);
		Table table = new Table();
		table.add(text1).expand().fill();
		ScrollPane scrollPane = new ScrollPane(table);
		root.add(scrollPane).expand().fill();
	}

	public void buildTest1() {
		Array<String> names = new Array<String>();
		Array<String> values = new Array<String>();

		for (int i = 0; i < 10; i++) {
			names.add(" Món Thứ " + (i + 1));
			values.add(" Nguyên Liệu Thứ " + (i + 1));
		}

		LabelStyle style = new LabelStyle(Assets.instance.fontFactory.getMedium15(), Color.RED);
		Label text1 = new Label("Nguyên Liệu 1", style);
		Label text2 = new Label("Nguyên Liệu 2", style);
		Label text3 = new Label("Nguyên Liệu 3", style);
		Label text4 = new Label("Nguyên Liệu 4", style);
		Label text5 = new Label("Nguyên Liệu 5", style);
		Label text6 = new Label("Nguyên Liệu 6", style);

		text1.setAlignment(Align.center);
		root.defaults().height(100);
		// root.add(text1).expandX().fillX().colspan(6);
		// root.row();
		// for (int i = 0; i < names.size; i++) {
		// Label name = new Label(names.get(i), style);
		// Label value = new Label(values.get(i), style);
		// name.setX(10);
		// value.setX(10);
		// for (int k = 1; k < i % 4; k++) {
		// root.add(name).expandX().fillX().height(40);
		// }
		// root.add(value).expandX().fillX().height(40);
		// root.row();
		// }

		root.add(text2).expandX().height(40);
		root.row();
		root.add(text3).expandX().height(40);
		root.add(text4).expandX().height(40);
		root.row();
		root.add(text5).expandX().height(40);
		root.row();
		root.add(text6).expandX().height(40);

		stage.addActor(root);
		root.debug();

		Image img = new Image(Assets.instance.uiM.bg_ninepatch);
		img.setSize(400, 30);
		img.setPosition(0, root.getHeight() - img.getHeight());
		root.addActor(img);
	}

	@Override
	public void resize(int width, int height) {

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

}
