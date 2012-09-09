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

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private static final Logger logger = Logger
			.getLogger(RegistrationServlet.class.getName());

	private final static String USERNAME_ERROR_MESSAGE = "That's not a valid username.";
	private final static String USERNAME_TAKEN_MESSAGE = "That user name has already been taken";
	private final static String PASSWORD_ERROR_MESSAGE = "That's not a valid password. " +
			"Passwords must be 6-15 characters long, must contain a lower case and an upper case letter," +
			" a digit, and a character that is neither a letter or a digit.";
	private final static String VERIFY_ERROR_MESSAGE = "Your passwords didn't match";
	private final static String EMAIL_ERROR_MESSAGE = "That's not a valid email.";

	public final static String USERNAME_PARAM = "username";
	public final static String PASSWORD_PARAM = "password";
	public final static String VERIFY_PARAM = "verify";
	public final static String EMAIL_PARAM = "email";
	public final static String USERNAME_ATTRIBUTE = "username";
	public final static String EMAIL_ATTRIBUTE = "email";
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
					Cookie cookie = new Cookie(User.COOKIE_NAME, "" + id);
					CookieEncoder.encode(cookie);
					cookie.setPath("/");
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

	private String validateUsername(String username) {
		if (User.isValidUsername(username)) {
			return null;
		} else {
			return USERNAME_ERROR_MESSAGE;
		}
	}

	private String validatePassword(String password) {
		if (User.isValidPassword(password)) {
			return null;
		} else {
			return PASSWORD_ERROR_MESSAGE;
		}
	}

	private String validateVerify(String password, String verify) {
		// Ensure that password is not null
		if (!password.equals(verify)) {
			return VERIFY_ERROR_MESSAGE;
		} else {
			return null;
		}
	}

	private String validateEmail(String email) {
		if (User.isValidEmail(email)) {
			return null;
		} else {
			return EMAIL_ERROR_MESSAGE;
		}
	}
}
