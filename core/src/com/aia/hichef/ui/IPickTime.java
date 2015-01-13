package com.aia.hichef.ui;

import java.util.ArrayList;

import com.aia.hichef.enums.AnimationType;
import com.aia.hichef.listener.OnPickTimeListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface IPickTime {

	public IPickTime build(Stage stage);

	public void show(AnimationType type);

	public void hide(AnimationType type);

	public String getDate();

	public String getYear();

	public String getMonth();

	public String getTime(String fomat);

	public IPickTime setFirstValues(ArrayList<String> listDays);

	public IPickTime setSecondValues(ArrayList<String> listMonths);

	public IPickTime setThirdValues(ArrayList<String> listYears);

	public IPickTime setDefaultDay(int index);

	public IPickTime setDefaultMonth(int index);

	public IPickTime setDefaultYear(int index);

	public IPickTime setTitle(String title);

	public IPickTime setListener(OnPickTimeListener _listener);

	public IPickTime validate();

	public OnPickTimeListener getListener();

	public boolean visible();

	public void reset();

	public void dispose();
}
