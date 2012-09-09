package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/**
	 * Error message to display if the user types an invalid user name or
	 * password or both
	 */
	private static final String ERROR_MESSAGE = "Invalid login!";

	/**
	 * The name of the parameter that holds the user name.
	 */
	public static final String USERNAME_PARAM = "username";
	/**
	 * The name of the parameter that holds the password.
	 */
	public static final String PASSWORD_PARAM = "password";
	/**
	 * The name of an attribute used to hold the user name.
	 */
	public static final String USERNAME_ATTRIBUTE = "username";
	/**
	 * The name of an attribute used to hold the error message.
	 */
	public static final String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute(ERROR_ATTRIBUTE, "");
		RequestDispatcher view = request
				.getRequestDispatcher("/view/login.jsp");
		response.setContentType("text/html");
		view.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String userName = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		String errorMessage = null;
		if ((errorMessage = validatePassword(password)) == null
				&& (errorMessage = validateUsername(userName)) == null) {
			long id = UserDataAccess.getUserId(userName, password);
			try {
				if (id > 0) {
					Cookie cookie = new Cookie(User.COOKIE_NAME, "" + id);
					CookieEncoder.encode(cookie);
					cookie.setPath("/");
					response.addCookie(cookie);
					response.sendRedirect("/");
					return;
				}
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			}
			errorMessage = ERROR_MESSAGE;
		}
		response.setContentType("text/html");
		request.setAttribute(USERNAME_ATTRIBUTE, userName);
		request.setAttribute(ERROR_ATTRIBUTE, errorMessage);

		RequestDispatcher view = request
				.getRequestDispatcher("/view/login.jsp");
		view.forward(request, response);
	}

	/**
	 * Checks if a user name is valid, and if it is not return an error message.
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
			return ERROR_MESSAGE;
		}
	}

	/**
	 * Checks if a password is valid, and if it is not return an error message.
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
			return ERROR_MESSAGE;
		}
	}
}
