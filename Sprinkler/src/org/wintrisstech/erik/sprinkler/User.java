package org.wintrisstech.erik.sprinkler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

public class User {

	private final static Pattern USERNAME_PATTERN = Pattern
			.compile("^\\w{3,15}$");

	private final static Pattern PASSWORD_PATTERN = Pattern
			.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})$");

	private final static Pattern EMAIL_PATTERN = Pattern
			.compile("^\\w+(\\.\\w+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public final static String COOKIE_NAME = "user";

	public static boolean isValidPassword(String password) {
		Matcher m = PASSWORD_PATTERN.matcher(password);
		return m.matches();
	}

	public static boolean isValidUsername(String username) {
		Matcher m = USERNAME_PATTERN.matcher(username);
		return m.matches();
	}

	public static boolean isValidEmail(String email) {
		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	public static String getUserName(Cookie[] cookies) {
		String idAsString = CookieEncoder.getCookieValue(cookies, COOKIE_NAME);
		if (idAsString != null) {
			try {
				long id = Long.parseLong(idAsString);
				return UserDataAccess.getUserName(id);
			} catch (NumberFormatException ex) {
				return null;
			}
		}
		return null;
	}

}
