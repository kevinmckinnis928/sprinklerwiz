package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;

/**
 * Utility class to encode and decode cookies, and retrieving an encoded cookie
 * value.
 * 
 * @author ecolban
 * 
 */
public class CookieEncoder {

	private static byte[] COOKIE_SECRET = "cookieSecr3t".getBytes(); // It

	// would have been smarter to have this value regenerated randomly after
	// some time.

	/**
	 * Checks that a cookie is valid.
	 * 
	 * @param cookie
	 *            the given cookie
	 * @return true if the cookie is valid
	 */
	public static boolean check(Cookie cookie) {

		String value = cookie.getValue();
		String s = value.split("\\|")[0];
		try {
			return value.equals(s + "|" + Crypto.encode(s, COOKIE_SECRET));
		} catch (InvalidKeyException e) {
			return false;
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}

	/**
	 * Encodes a cookie. The result consists of the original cookie value
	 * followed by a HMAC.
	 * 
	 * @param cookie
	 *            the cookie to encode
	 * @throws NoSuchAlgorithmException
	 *             if the HMAC computation fails
	 * @throws InvalidKeyException
	 *             if the HMAC computation fails
	 */
	public static void encode(Cookie cookie) throws NoSuchAlgorithmException,
			InvalidKeyException {
		String value = cookie.getValue();
		cookie.setValue(value + "|" + Crypto.encode(value, COOKIE_SECRET));
	}

	public static String decode(Cookie cookie) {
		return cookie.getValue().split("\\|")[0];
	}

	public static String getCookieValue(Cookie[] cookies, String name) {
		if (name == null) {
			return null;
		}
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName()) && CookieEncoder.check(c)) {
					return CookieEncoder.decode(c);
				}
			}
		}
		return null;
	}
}
