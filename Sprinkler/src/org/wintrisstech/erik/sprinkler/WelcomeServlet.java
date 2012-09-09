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
public class WelcomeServlet extends HttpServlet {


//	private static final Logger logger = Logger.getLogger(WelcomeServlet.class
//			.getName());
	
	/**
	 * The name of an attribute used to hold the user name.
	 */
	public final static String USERNAME_ATTRIBUTE = "username";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		String idAsString = CookieEncoder
				.getCookieValue(cookies, "user");
		if (idAsString != null) {
			String userName = null;
			try {
				long id = Long.parseLong(idAsString);
				userName = UserDataAccess.getUserName(id);
				request.setAttribute(USERNAME_ATTRIBUTE, "" + userName);
			} catch (NumberFormatException ex) {
			}
		}
		RequestDispatcher view = request
				.getRequestDispatcher("/view/welcome.jsp");
		response.setContentType("text/html");
		view.forward(request, response);

	}
}
