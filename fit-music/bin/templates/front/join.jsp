<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Join</title>
</head>
<body>
	<form action="/join_action" method="post">
		<label for="inputId">ID : </label>
		<input type="text" id="inputId" name="inputId" >
		<label for="inputName">Name : </label>
		<input type="text" id="inputName" name="inputName">
		<label for="inputPass">Password : </label>
    <input type="password" id="inputPass" name="inputPass">
    <br>
    <button type="submit">Join</button>
	</form>
</body>
</html>