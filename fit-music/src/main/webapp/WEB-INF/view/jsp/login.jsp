<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<form action="login/action" method="post">
		<input type="text" id="inputId" name="inputId" >
    <input type="password" id="inputPass" name="inputPass">
    <button type="submit">Login</button>
    <a href="./join/view">Join</a>
	</form>
</body>
</html>