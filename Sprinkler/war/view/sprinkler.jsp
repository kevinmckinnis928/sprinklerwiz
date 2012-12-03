<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.wintrisstech.erik.sprinkler.WelcomeServlet"%>
<%@page
	import="org.wintrisstech.erik.sprinkler.SprinklerRegistrationServlet"%>
<%@ page import="org.wintrisstech.erik.sprinkler.RegistrationServlet"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Sprinkler Registration</title>
</head>
<body>
	<%
		/* pageContext.setAttribute("username", request
				.getAttribute(WelcomeServlet.USERNAME_ATTRIBUTE)); */
		if (request.getAttribute(WelcomeServlet.USERNAME_ATTRIBUTE) != null) {
	%>
	<div align="right">
		${username} (<a class="login-link" href="/logout">logout</a>)
	</div>
	<%
		}
	%>
	<div class="main-title">Register you sprinkler</div>

	<form method="post">
		<table>
			<tr>
				<td align="right"><b>Sprinkler Name:</b></td>
				<td><input name="sprinkler_name"
					value="${fn:escapeXml(sprinkler_name)}"></td>
			</tr>

		</table>
		<input type="submit" value="Submit">
</html>