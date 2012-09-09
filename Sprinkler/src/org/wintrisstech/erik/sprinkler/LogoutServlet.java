package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

;

/**
 * A servlet that handles a logout request.
 * 
 * 
 * @author ecolban
 *
 */
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie cookie = new Cookie(User.COOKIE_NAME, "");
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect("/"); 
	}
}
