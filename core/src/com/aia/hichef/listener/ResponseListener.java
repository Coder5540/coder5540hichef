package com.aia.hichef.listener;

public interface ResponseListener {

	public void success();

	public void failed();

	public void response(String string);

}
