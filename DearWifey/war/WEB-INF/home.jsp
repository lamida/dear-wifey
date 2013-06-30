<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${user != null}">
			<p>
				Welcome, ${user.email}! You can <a href="${logoutUrl}">sign out</a>.
			</p>
		</c:when>
		<c:otherwise>
			<p>
				Welcome! <a href="${loginUrl}">Sign in or register</a> to customize.
			</p>
		</c:otherwise>
	</c:choose>
	<p>The time is: ${currentTime}</p>
</body>
</html>