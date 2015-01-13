package com.aia.hichef.utils;

public class UserInfo {

	private static UserInfo	INSTANCE;

	public static UserInfo getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserInfo();
		}
		return INSTANCE;
	}

	public int getUserId() {
		return AppPreference.instance._userID;
	}

	public boolean userLogged() {
		if (getUserId() > 0) {
			System.out.println("USER DA DANG NHAP");
			return true;
		} else {
			System.out.println("USER CHUA DANG NHAP");
			return false;
		}
	}

	public String getAvatarUrl() {
		return AppPreference.instance._avatar;
	}

	public String getName() {
		return AppPreference.instance._title;
	}

}
