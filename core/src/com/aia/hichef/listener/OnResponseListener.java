package com.aia.hichef.listener;

public interface OnResponseListener {
	public void onOk();

	public void onOk(String name, String quality);

	public void onCancel();
}