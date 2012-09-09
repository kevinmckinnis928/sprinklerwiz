<%@page import="org.wintrisstech.erik.sprinkler.LoginServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.wintrisstech.erik.sprinkler.LoginServlet"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Login</title>
</head>
<body>
	<%
		pageContext.setAttribute("username",
				request.getAttribute(LoginServlet.USERNAME_ATTRIBUTE));
		pageContext.setAttribute("error",
				request.getAttribute(LoginServlet.ERROR_ATTRIBUTE));
	%>
	<h1>Sign In</h1>
	<form action="login" method="post">
		<table>
			<tr>
				<td align="right"><b>Username</b></td>
				<td><input name="<%=LoginServlet.USERNAME_PARAM %>"
					value="${fn:escapeXml(username)}"></td>
			</tr>
			<tr>
				<td align="right"><b>Password</b></td>
				<td><input name="<%=LoginServlet.PASSWORD_PARAM%>"
					type="password"></td>
			</tr>
			<tr>
				<td colspan="2"><div class="error">${error}</div></td>
			</tr>
		</table>
		<input type="submit" value="Submit">
	</form>
</body>
</html>