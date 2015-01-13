package com.aia.hichef.ui.n;


public class TabItem {
	public int index;

	public TabTitle title;
	public TabContent body;

	public TabItem(String name, TabContentMove body) {
		this.body = (TabContent) body;
		title = new TabTitle(name);
	}

	public void closeLoading() {
		body.closeLoading();
	}
}