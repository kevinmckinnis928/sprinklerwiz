package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;

public class CookieEncoder {

	private static byte[] COOKIE_SECRET = "cookieSecr3t".getBytes();

	public static boolean check(Cookie cookie) throws InvalidKeyException,
			NoSuchAlgorithmException {

		String value = cookie.getValue();
		String s = value.split("\\|")[0];
		return value.equals(s + "|" + Crypto.encode(s, COOKIE_SECRET));
	}

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
				try {
					if (name.equals(c.getName())  && CookieEncoder.check(c))
					{
						return CookieEncoder.decode(c);
					}
				} catch (InvalidKeyException e) {
				 break;
				 } catch (NoSuchAlgorithmException e) {
				 break;
				 }
			}
		}
		return null;
	}
}
