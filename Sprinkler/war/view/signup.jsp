<%@page import="org.wintrisstech.erik.sprinkler.RegistrationServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.wintrisstech.erik.sprinkler.RegistrationServlet"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Sign Up</title>
</head>
<body>
	<%
		pageContext.setAttribute("username", request
				.getAttribute(RegistrationServlet.USERNAME_ATTRIBUTE));
		pageContext.setAttribute("email",
				request.getAttribute(RegistrationServlet.EMAIL_ATTRIBUTE));
		pageContext.setAttribute("error",
				request.getAttribute(RegistrationServlet.ERROR_ATTRIBUTE));
	%>
	<h1 class="main-title" >Sign Up</h1>
	<form method="post">
		<table>
			<tr>
				<td align="right"><b>Username</b></td>
				<td><input name="<%= RegistrationServlet.USERNAME_PARAM%>"
					value="${fn:escapeXml(username)}"></td>
			</tr>
			<tr>
				<td align="right"><b>Password</b></td>
				<td><input name="<%=RegistrationServlet.PASSWORD_PARAM%>"
					type="password"></td>
			</tr>
			<tr>
				<td align="right"><b>Verify Password</b></td>
				<td><input name="<%=RegistrationServlet.VERIFY_PARAM%>"
					type="password"></td>
			</tr>
			<tr>
				<td align="right"><b>Email</b></td>
				<td><input name="<%=RegistrationServlet.EMAIL_PARAM %>"
					value="${fn:escapeXml(email)}"></td>
			</tr>
			<tr>
				<td colspan="2"><div class="error">${error}</div></td>
			</tr>

		</table>
		<input type="submit" value="Submit">

	</form>

</body>
</html>