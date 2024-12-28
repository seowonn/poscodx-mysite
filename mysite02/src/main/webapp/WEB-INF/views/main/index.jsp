<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<!--동적 include 방식으로 변수를 직접 접근, 공유할 수 없다. 즉 다시 호출해 와야 한다.   -->
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<c:choose>
						<c:when test="${empty authUser}">
							<img id="profile" src="${pageContext.request.contextPath}/assets/images/anonymous_user.jpeg">
							<h2>안녕하세요. mysite에 오신 것을 환영합니다.</h2>
						</c:when>
						<c:otherwise>
							<img id="profile" src="${pageContext.request.contextPath}/assets/images/myProfile.png">
							<h2>안녕하세요. ${authUser.name}의  mysite에 오신 것을 환영합니다.</h2>
						</c:otherwise>
					</c:choose>
					<p>
						이 사이트는  웹 프로그램밍 실습과제 예제 사이트입니다.<br>
						메뉴는  사이트 소개, 방명록, 게시판이 있구요. Python 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서
						만들어 놓은 사이트 입니다.<br><br>
						<a href="${pageContext.request.contextPath}/guestbook">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>