package com.aia.hichef.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonChecker {
	public static boolean validEmail(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean validDate(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean isNumeric(String str) {
		return str.matches("\\d+");
	}

	public static boolean validPhone(String phoneNumber) {
		if (!(phoneNumber.startsWith("0") || phoneNumber.startsWith("84") || phoneNumber
				.startsWith("+84")))
			return false;
		String phone = "";
		if (phoneNumber.startsWith("+84"))
			phone = phoneNumber.substring(3);
		if (phoneNumber.startsWith("0"))
			phone = phoneNumber.substring(1);
		if (phoneNumber.startsWith("84"))
			phone = phoneNumber.substring(2);

		System.out.println("Phone : " + phone);
		if (phone.length() == 9 || phone.length() == 10) {
			return isNumeric(phone);
		}
		return false;

	}
}
