package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
//import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * A servlet that handles registration requests.
 * 
 * @author ecolban
 * 
 */
@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private static final Logger logger = Logger
			.getLogger(RegistrationServlet.class.getName());

	private final static String USERNAME_TAKEN_MESSAGE = "That user name has already been taken";
	private final static String VERIFY_ERROR_MESSAGE = "Your passwords didn't match";

	/**
	 * The name of the parameter that holds the user name.
	 */
	public final static String USERNAME_PARAM = "username";
	/**
	 * The name of the parameter that holds the password.
	 */
	public final static String PASSWORD_PARAM = "password";
	/**
	 * The name of the parameter that holds the verified password.
	 */
	public final static String VERIFY_PARAM = "verify";
	/**
	 * The name of the parameter that holds the email.
	 */
	public final static String EMAIL_PARAM = "email";
	/**
	 * The name of an attribute used to hold the user name.
	 */
	public final static String USERNAME_ATTRIBUTE = "username";
	/**
	 * The name of an attribute used to hold the email.
	 */
	public final static String EMAIL_ATTRIBUTE = "email";
	/**
	 * The name of an attribute used to hold the error message.
	 */
	public final static String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher view = request
				.getRequestDispatcher("/view/signup.jsp");
		response.setContentType("text/html");
		view.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String userName = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		String verify = request.getParameter(VERIFY_PARAM);
		String email = request.getParameter(EMAIL_PARAM);

		logger.log(Level.INFO, "email is {0}", email);

		String errorMessage = null;
		if ((errorMessage = validatePassword(password)) == null
				&& (errorMessage = validateUsername(userName)) == null
				&& (errorMessage = validateVerify(password, verify)) == null
				&& (errorMessage = validateEmail(email)) == null) {
			try {
				long id = UserDataAccess.addUser(userName, password, email);
				if (id > 0L) {
					Cookie cookie = User.getCookie(id);
					response.addCookie(cookie);
					response.sendRedirect("/");
					return;
				} else {
					errorMessage = USERNAME_TAKEN_MESSAGE;
				}
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			}
		}
		response.setContentType("text/html");
		request.setAttribute(USERNAME_ATTRIBUTE, userName);
		request.setAttribute(EMAIL_ATTRIBUTE, email);
		request.setAttribute(ERROR_ATTRIBUTE, errorMessage);
		RequestDispatcher view = request
				.getRequestDispatcher("/view/signup.jsp");
		view.forward(request, response);
	}

	/**
	 * Checks if a user name is valid, and if it is not, returns an error message.
	 * 
	 * @param username
	 *            the user name to check.
	 * @return an error message if the user name is not valid, or null if it is
	 *         valid.
	 */
	private String validateUsername(String username) {
		if (User.isValidUsername(username)) {
			return null;
		} else {
			return User.USERNAME_ERROR_MESSAGE;
		}
	}

	/**
	 * Checks if a password is valid, and if it is not, returns an error message.
	 * 
	 * @param password
	 *            the password to check.
	 * @return an error message if the password is not valid, or null if it is
	 *         valid.
	 */
	private String validatePassword(String password) {
		if (User.isValidPassword(password)) {
			return null;
		} else {
			return User.PASSWORD_ERROR_MESSAGE;
		}
	}

	/**
	 * Checks if the two passwords provided are equal
	 * @param password the password
	 * @param verify the password verification
	 * @return true if the two passwords are equal
	 */
	private String validateVerify(String password, String verify) {
		// Ensure that password is not when this method is called.
		if (!password.equals(verify)) {
			return VERIFY_ERROR_MESSAGE;
		} else {
			return null;
		}
	}

	/**
	 * Checks if an email is valid, and if it is not, returns an error message.
	 * 
	 * @param email
	 *            the email to check.
	 * @return an error message if the email is not valid, or null if it is
	 *         valid.
	 */
	private String validateEmail(String email) {
		if (User.isValidEmail(email)) {
			return null;
		} else {
			return User.EMAIL_ERROR_MESSAGE;
		}
	}
}
