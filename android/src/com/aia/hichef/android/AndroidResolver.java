package com.aia.hichef.android;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.aia.hichef.PlatformResolver;
import com.badlogic.gdx.graphics.Pixmap;

public class AndroidResolver implements PlatformResolver {
	HichefAndroid	hichefAndroid;

	public AndroidResolver(HichefAndroid hichefAndroid) {
		super();
		this.hichefAndroid = hichefAndroid;
	}

	@Override
	public Pixmap formatBitmap(InputStream in) {
		Pixmap pixmap = null;

		try {
			Bitmap myBitmap = BitmapFactory.decodeStream(in);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			myBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			pixmap = new Pixmap(outStream.toByteArray(), 0, outStream.toByteArray().length);
		} catch (Exception e) {
		}

		return pixmap;
	}

	@Override
	public String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return "Android-" + capitalize(model);
		} else {
			return "Android-" + capitalize(manufacturer) + " " + model;
		}
	}

	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	@Override
	public String getDeviceID() {
		return hichefAndroid.getDeviceId();
	}


}
