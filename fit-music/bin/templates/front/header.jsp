<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${sessionScope.loginId == null}">
		<i>로그인 해주세요</i>
	</c:if>
	<c:if test="${sessionScope.loginId != null}">
		<c:out value="${sessionScope.loginName}님 환영합니다!"></c:out>
	</c:if>
	<a href="./login">Login</a>
</body>
</html>