package org.wintrisstech.erik.sprinkler;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.ShortBlob;

public class UserDataAccess {

	private static final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private static final Logger logger = Logger.getLogger(UserDataAccess.class
			.getName());

	private static final Random random = new Random();
	private final static String USER_KIND = "user_kind";
	private final static String USERNAME_PROPERTY = "username";
	private final static String PASSWORD_PROPERTY = "password";
	private final static String SALT_PROPERTY = "salt";
	private final static String EMAIL_PROPERTY = "email";

	public static long addUser(String username, String password, String email)
			throws UnsupportedEncodingException {
		Entity userAlreadyInDatastore = findUser(username);
		if (userAlreadyInDatastore != null) {
			return 0L;
		}
		Entity entity = new Entity(USER_KIND);
		entity.setProperty(USERNAME_PROPERTY, username);
		byte[] salt = generateSalt();
		try {
			entity.setProperty(PASSWORD_PROPERTY,
					new ShortBlob(Crypto.computeHmac(password, salt)));
		} catch (InvalidKeyException e) {
			return 0L;
		} catch (NoSuchAlgorithmException e) {
			return 0L;
		}
		entity.setProperty(SALT_PROPERTY, new ShortBlob(salt));
		entity.setProperty(EMAIL_PROPERTY, email);
		Key key = datastore.put(entity);
		return key.getId();
	}

	private static byte[] generateSalt() {
		byte[] result = new byte[5];
		random.nextBytes(result);
		return result;
	}

	private static Entity findUser(String username) {
		logger.log(Level.INFO, "Querying the datastore for {0}.", username);
		Query query = new Query(USER_KIND);
		query.setFilter(new FilterPredicate(USERNAME_PROPERTY, FilterOperator.EQUAL,
				username));
		List<Entity> users = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));
		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public static long getUserId(String userName, String password) {
		Entity user = findUser(userName);
		if (user != null) {
			boolean passwordOK;
			try {
				passwordOK = Crypto.verifyHmac(password,
						((ShortBlob) user.getProperty(SALT_PROPERTY)).getBytes(),
						((ShortBlob) user.getProperty(PASSWORD_PROPERTY)).getBytes());
				if (passwordOK) {
					return user.getKey().getId();
				}
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			} catch (IllegalStateException e) {
			}
		}
		return 0L;
	}

	public static String getUserName(long id) {
		logger.log(Level.INFO, "Querying the datastore for user id = {0}", id);
		Key key = KeyFactory.createKey(USER_KIND, id);
		Entity user = null;
		try {
			user = datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
		if (user != null) {
			return (String) user.getProperty(USERNAME_PROPERTY);
		} else {
			return null;
		}
	}
}
