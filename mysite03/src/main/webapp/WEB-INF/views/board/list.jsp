<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="hidden" name="a" value="search"?>
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
					<c:set var="startNumber" value="${totalCount - (currentPage - 1) * pageSize}" />
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
							<td>${startNumber-status.index}</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px"> <!-- 여기 0자리엔 depth가 들어가함. -->
							    <c:if test="${vo.depth != 0}">
							        <img src="${pageContext.request.contextPath}/assets/images/reply.png"/>
							    </c:if>
							    <a href="${pageContext.request.contextPath}/board?a=view&id=${vo.id}">${vo.title}</a>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<c:if test="${not empty authUser and vo.userId == authUser.id}">
								<td><a href="${pageContext.request.contextPath}/board?a=delete&id=${vo.id}" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test="${currentPage > 1}">							
							<li><a href="?page=${currentPage -1}">◀</a></li>
						</c:if>
						
						<c:forEach begin="1" end="${totalPages}" var="page">						
							<li class="${page == currentPage ? 'selected' : ''}">
								<a href="?page=${page}">${page}</a>
							</li>
						</c:forEach>
						
						<c:if test="${currentPage < totalPages}">							
							<li><a href="?page=${currentPage + 1}">▶</a></li>
						</c:if>
						
					</ul>
				</div>	
				
				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>