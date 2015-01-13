package com.aia.hichef.listener;

public interface OnPickTimeListener {

	public void onOk(String date, String month, String year);
	public void onCancel(String date, String month, String year);

}
