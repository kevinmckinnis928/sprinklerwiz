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

	private static final String ERROR_MESSAGE = "Invalid login!";
	
	public static final String USERNAME_PARAM = "username";
	public static final String PASSWORD_PARAM = "password";
	public static final String USERNAME_ATTRIBUTE = "username";
	public static final String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("error", "");
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
		request.setAttribute("error", errorMessage);

		RequestDispatcher view = request
				.getRequestDispatcher("/view/login.jsp");
		view.forward(request, response);
	}

	private String validateUsername(String username) {
		if (User.isValidUsername(username)) {
			return null;
		} else {
			return ERROR_MESSAGE;
		}
	}

	private String validatePassword(String password) {
		if (User.isValidPassword(password)) {
			return null;
		} else {
			return ERROR_MESSAGE;
		}
	}
}
