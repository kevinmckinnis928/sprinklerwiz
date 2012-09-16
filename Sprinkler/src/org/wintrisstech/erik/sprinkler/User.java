package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

/**
 * A utility class particularly related to a user.
 * 
 * @author ecolban
 * 
 */
public class User {

	private final static Pattern USERNAME_PATTERN = Pattern
			.compile("^\\w{3,15}$");

	public final static String USERNAME_ERROR_MESSAGE = "That's not a valid username. "
			+ "User names must be 3 to 15 characters long and may only contain letters, "
			+ "digits and underscores.";

	private final static Pattern PASSWORD_PATTERN = Pattern
			.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})$");

	public final static String PASSWORD_ERROR_MESSAGE = "That's not a valid password. "
			+ "Passwords must be 6-15 characters long, must contain at least one lower case letter, "
			+ "one upper case letter, and one digit.";

	private final static Pattern EMAIL_PATTERN = Pattern
			.compile("^\\w+(\\.\\w+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public final static String EMAIL_ERROR_MESSAGE = "That's not a valid email.";

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
		String value = CookieEncoder.getCookieValue(cookies, COOKIE_NAME);
		if (value != null) {
			try {
				long id = Long.parseLong(value);
				return UserDataAccess.getUserName(id);
			} catch (NumberFormatException ex) {
				return null;
			}
		}
		return null;
	}

	public static Cookie getCookie(long id)
			throws InvalidKeyException, NoSuchAlgorithmException {
		Cookie cookie = new Cookie(User.COOKIE_NAME, "" + id);
		CookieEncoder.encode(cookie);
		cookie.setPath("/");
		return cookie;
	}

}
