<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="delete-form">
				<form method="post" action="${pageContext.request.contextPath }/board/delete/${no}">
					<input type='hidden' name="userNo" value="${authUser.no }">
					<label>비밀번호</label>
					<input type="password" name="password">
					<input type="submit" value="확인">
				</form>
				<c:if test="${status == 'fail' }">
					<pre>비밀번호가 틀렸습니다.</pre>
				</c:if>
				<a href="${pageContext.request.contextPath }/board">게시판 메인으로</a>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>