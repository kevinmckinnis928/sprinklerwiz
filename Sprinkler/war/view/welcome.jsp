<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel=stylesheet href="/stylesheet/main.css" type="text/css">
<title>Welcome</title>
</head>
<body>
	<%
		if (request.getAttribute("username") != null) {
			/* pageContext.setAttribute("username",
					request.getAttribute("username")); */
	%>
	<div align="right">
		${username} (<a class="login-link" href="logout">logout</a>)
	</div>
	<%
		} else {
	%>
	<div align="right">
		<a class="login-link" href="login">login</a>, <a class="login-link"
			href="register">register</a>
	</div>
	<%
		}
	%>
	<div class="main-title">Welcome</div>

</body>
</html>