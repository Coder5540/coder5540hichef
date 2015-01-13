package com.aia.hichef.utils;

import java.util.ArrayList;

import com.aia.hichef.attribute.StringSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreference {
	public static final String	TAG				= AppPreference.class.getName();
	public static AppPreference	instance		= new AppPreference();

	private final String		HISTORY			= "history";
	private final String		SIZE_HISTORY	= "size_list_history";

	public ArrayList<String>	historySearch	= new ArrayList<String>();
	public int					size_listHistory;
	private Preferences			preferences;

	// =============== User Info Variable ======================
	public String				_title;
	public String				_facebookID;
	public int					_userID;
	public String				_avatar;
	public int					_gender;
	public String				_birthday;
	public String				_email;
	public String				_phone;
	public String				_deviceID;
	public String				_deviceName;
	public boolean				_login;
	public int					_version;
	public int					_sessionID;
	public String				_about;

	private AppPreference() {
		preferences = Gdx.app.getPreferences(TAG);
		load();
	}

	public void load() {
		_title = preferences.getString(StringSystem._TITLE, "User");
		_facebookID = preferences.getString(StringSystem._FACEBOOK_ID, "");
		_userID = preferences.getInteger(StringSystem._USER_ID, -1);
		_avatar = preferences.getString(StringSystem._AVATAR, "");
		_birthday = preferences.getString(StringSystem._BIRTHDAY, "01-01-1990");
		_gender = preferences.getInteger(StringSystem._GENDER, 1);
		_email = preferences.getString(StringSystem._EMAIL, "");
		_phone = preferences.getString(StringSystem._PHONE, "");
		_deviceID = preferences.getString(StringSystem._DEVICE_ID, "");
		_deviceName = preferences.getString(StringSystem._DEVICE_NAME, "");
		_about = preferences.getString(StringSystem._ABOUT, "");
		_login = preferences.getBoolean(StringSystem._LOGIN, false);
		_version = preferences.getInteger(StringSystem._VERSION, 1);
		_sessionID = preferences.getInteger(StringSystem._SESSION_ID, -1);

		size_listHistory = preferences.getInteger(SIZE_HISTORY);
		historySearch.clear();
		for (int i = 0; i < size_listHistory; i++) {
			historySearch.add(preferences.getString(
					HISTORY + Integer.toString(i), ""));
		}
	}

	public void saveUserInformation() {
		preferences.putString(StringSystem._TITLE, _title);
		preferences.putString(StringSystem._FACEBOOK_ID, _facebookID);
		preferences.putInteger(StringSystem._USER_ID, _userID);
		preferences.putString(StringSystem._AVATAR, _avatar);
		preferences.putString(StringSystem._BIRTHDAY, _birthday);
		preferences.putInteger(StringSystem._GENDER, _gender);
		preferences.putString(StringSystem._EMAIL, _email);
		preferences.putString(StringSystem._PHONE, _phone);
		preferences.putString(StringSystem._ABOUT, _about);
		preferences.putBoolean(StringSystem._LOGIN, _login);
		preferences.flush();
	}

	public void saveUserDevice() {
		preferences.putString(StringSystem._DEVICE_ID, _deviceID);
		preferences.putString(StringSystem._DEVICE_NAME, _deviceName);
		preferences.flush();
	}

	public void saveSessionID() {
		preferences.putInteger(StringSystem._SESSION_ID, _sessionID);
		preferences.flush();
	}

	public void saveVersion() {
		preferences.putInteger(StringSystem._VERSION, _version);
		preferences.flush();
	}

	public void saveHistory() {
		preferences.putInteger(SIZE_HISTORY, size_listHistory);
		for (int i = 0; i < size_listHistory; i++) {
			preferences.putString(HISTORY + Integer.toString(i),
					historySearch.get(i));
		}
		preferences.flush();
	}

	public void resetHistory() {
		for (int i = 0; i < size_listHistory; i++) {
			preferences.remove(HISTORY + Integer.toString(i));
		}
		size_listHistory = 0;
		preferences.remove(SIZE_HISTORY);
		historySearch.clear();
		preferences.flush();
	}

	public void saveNewHistory(String history) {
		historySearch.add(history);
		size_listHistory++;
		saveHistory();
	}

	public void frontHistory(String history) {
		for (int i = 0; i < historySearch.size(); i++) {
			if (historySearch.get(i).equals(history)) {
				historySearch.remove(i);
				break;
			}
		}
		historySearch.add(history);
		saveHistory();

	}

	public String loadHistory(int index) {
		if (index < size_listHistory) {
			return historySearch.get(index);
		}
		return null;
	}

	public void saveSize(int size) {
		size_listHistory = size;
		preferences.putInteger(SIZE_HISTORY, size);
	}

	public void debug() {
		System.out.println("size : " + preferences.getInteger(SIZE_HISTORY));
	}

	public Preferences getPreferences() {
		return preferences;
	}
}
