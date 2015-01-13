package com.bgate.app.hichef.desktop;

import java.util.HashMap;
import java.util.Map;

import com.aia.hichef.attribute.StringSystem;
import com.aia.hichef.networks.FacebookConnector;
import com.badlogic.gdx.files.FileHandle;

public class DesktopFBConnector implements FacebookConnector {

	@Override
	public void login(OnLoginListener listener) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("id", "361263394031805");
		info.put("name", "Hero Pham");
		info.put("first_name", "Hero");
		info.put("last_name", "Pham");
		info.put("avatar", "https://graph.facebook.com/361263394031805/picture?width=100&height=100");
		info.put(
				"access_token",
				"CAAFmqyqhgXcBAF5E6yqyvnoS4bpUsMZBKB0p7VBAZCJOLYMZByzWLcryG7zlL7pnWqZAZCmaLZCWIHeMnnk4ik7zGwqxgdGTZBgGrALnVDLK8vdaP8KYon3f9ZAKFTIUaxZBlQqQotLjfFjdlmHZCZAZAdcewq9GWgv8RycTKkM1lidMIfzeHsC15JMy1Lp6gJPo1aJm7aX0ovsDzSP6nFZB4Iq8e");
		info.put(StringSystem._EMAIL, "coder5560@gmail.com");
		listener.onComplete(info);
	}

	@Override
	public void logout(OnLogoutListener listener) {

	}

	@Override
	public void restorePreviousSession(OnLoginListener listener) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("id", "361263394031805");
		info.put("name", "Hero Pham");
		info.put("first_name", "Hero");
		info.put("last_name", "Pham");
		info.put("avatar", "https://graph.facebook.com/361263394031805/picture?width=100&height=100");
		info.put(
				"access_token",
				"CAAFmqyqhgXcBAF5E6yqyvnoS4bpUsMZBKB0p7VBAZCJOLYMZByzWLcryG7zlL7pnWqZAZCmaLZCWIHeMnnk4ik7zGwqxgdGTZBgGrALnVDLK8vdaP8KYon3f9ZAKFTIUaxZBlQqQotLjfFjdlmHZCZAZAdcewq9GWgv8RycTKkM1lidMIfzeHsC15JMy1Lp6gJPo1aJm7aX0ovsDzSP6nFZB4Iq8e");
		info.put(StringSystem._EMAIL, "coder5560@gmail.com");
		
		
		
		listener.onComplete(info);
	}

	@Override
	public void like(String link, OnActionListener listener) {

	}

	@Override
	public void share(String link, OnActionListener listener) {

	}

	@Override
	public void rate(String link, OnActionListener listener) {

	}

	@Override
	public void download(String link, OnActionListener listener) {

	}

	@Override
	public void share(FileHandle fileHandle) {

	}

	@Override
	public void share(String url, String name, String des) {
	}

}
