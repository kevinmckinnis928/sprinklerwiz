package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
//import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * A servlet that handles requests for the welcome page.
 * 
 * @author ecolban
 * 
 */
@SuppressWarnings("serial")
public class SprinklerRegistrationServlet extends HttpServlet {

	// private static final Logger logger =
	// Logger.getLogger(WelcomeServlet.class
	// .getName());

	/**
	 * The name of an attribute used to hold the user name.
	 */
	public final static String USERNAME_ATTRIBUTE = "username";
	public final static String SPRINKLER_NAME_ATTRIBUTE = "sprinklerName";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		long id = User.getId(cookies);
		String userName = UserDataAccess.getUserName(id);
		if (userName != null) {
			request.setAttribute(USERNAME_ATTRIBUTE, "" + userName);
		}
		String sprinklerName = UserDataAccess.getSpinklerName(id);
		if (sprinklerName != null) {
			request.setAttribute(SPRINKLER_NAME_ATTRIBUTE, "" + sprinklerName);
		}
		RequestDispatcher view;
		if (userName == null) {
			view = request.getRequestDispatcher("/view/welcome.jsp");
		} else {
			view = request.getRequestDispatcher("/view/sprinkler.jsp");
		}
		response.setContentType("text/html");
		view.forward(request, response);

	}
}
