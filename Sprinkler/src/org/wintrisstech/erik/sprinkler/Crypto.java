package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Utility class used for message authentication code generation and validation.
 * 
 * @author ecolban
 * 
 */
public class Crypto {

	// private static Logger logger = Logger.getLogger(Crypto.class.getName());

	/**
	 * Computes the Hash-based Message Authentication Code (HMAC) for a given
	 * string and a given key. The HMAC computation is based on SHA256.
	 * 
	 * @param baseString
	 *            The string for which the HMAC is computed
	 * @param key
	 *            a byte array containing the secret key
	 * @return a byte array containing the computed HMAC
	 * @throws NoSuchAlgorithmException
	 *             if the server does not support the hashing algorithm
	 * @throws InvalidKeyException
	 *             if the given key is inappropriate
	 */
	public static byte[] computeHmac(String baseString, byte[] key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret = new SecretKeySpec(key, mac.getAlgorithm());
		mac.init(secret);
		return mac.doFinal(baseString.getBytes());
	}

	/**
	 * Verifies that the HMAC computed for a given string and a given key
	 * results in an expected value.
	 * 
	 * @param baseString
	 *            the given string
	 * @param key
	 *            the key used for the HMAC computation
	 * @param expected
	 *            the expected result
	 * @return true if the computed HMAC matches the expected result
	 * @throws InvalidKeyException
	 *             if the given key is inappropriate
	 * @throws NoSuchAlgorithmException
	 *             if the server does not support the hashing algorithm
	 */
	public static boolean verifyHmac(String baseString, byte[] key,
			byte[] expected) throws InvalidKeyException,
			NoSuchAlgorithmException {
		byte[] encoded = computeHmac(baseString, key);
		if (encoded == null || expected == null
				|| encoded.length != expected.length) {
			return false;
		}

		for (int i = 0; i < expected.length; i++) {
			if (encoded[i] != expected[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Computes the HMAC for a given string and a given key and returns the
	 * result as a Base64-encoded string. Does the same a computeHmac(), except
	 * that it performs base64 encoding at the end.
	 * 
	 * @param baseString
	 *            the given string
	 * @param key
	 *            the key used for the HMAC computation
	 * @return the base64 encoded HMAC
	 * @throws InvalidKeyException
	 *             if the given key is inappropriate
	 * @throws NoSuchAlgorithmException
	 *             if the server does not support the hashing algorithm
	 * 
	 * @see #computeHmac(String, byte[])
	 */
	public static String encode(String baseString, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException {
		String result = Base64.encodeBase64String(computeHmac(baseString, key));
		// logger.log(Level.INFO, "encoded string = {0}", result);
		return result;
	}

}
