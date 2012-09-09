package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypto {
	
	private static Logger logger = Logger.getLogger(Crypto.class.getName());

	public static byte[] computeHmac(String baseString, byte[] key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret = new SecretKeySpec(key, mac.getAlgorithm());
		mac.init(secret);
		return mac.doFinal(baseString.getBytes());
	}

	public static boolean verifyHmac(String baseString, byte[] key,
			byte[] expected) throws InvalidKeyException,
			NoSuchAlgorithmException, IllegalStateException {
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

	public static String encode(String baseString, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException {
//		Mac mac = Mac.getInstance("HmacSHA256");
//		SecretKeySpec secret = new SecretKeySpec(key, mac.getAlgorithm());
//		mac.init(secret);
//		byte[] hash = mac.doFinal(baseString.getBytes());
		String result = Base64.encodeBase64String(computeHmac(baseString, key));
		logger.log(Level.INFO, "encoded string = {0}", result);
		return result;
	}
	

}
