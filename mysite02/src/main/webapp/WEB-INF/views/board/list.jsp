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
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${vo.rownum }</td>
							<td style="text-align:left; padding-left:${vo.depth*10}px;">
							<c:if test="${vo.depth > 0 }">
								<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
							</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${vo.userNo == authUser.no }">
									<a href="${pageContext.request.contextPath }/board?a=deleteform&no=${vo.no }" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${startPage > pageBlock }">
							<li><a href="${pageContext.request.contextPath }/board?startPage=${startPage - pageBlock}">◀</a></li>
						</c:if>
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${currentPage == i }">
									<li><a href="${pageContext.request.contextPath }/board?p=${i}">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li class="selected">${i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pageCount > endPage }">
							<li><a href="${pageContext.request.contextPath }/board?startPage=${startPage + pageBlock}">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${not empty authUser }">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>