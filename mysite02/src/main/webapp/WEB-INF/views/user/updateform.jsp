<%@page import="mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserVo vo = (UserVo) request.getAttribute("vo");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="<%=request.getContextPath() %>/user">
					<input type="hidden" name="a" value="update">
					
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="<%=vo.getName() %>">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="<%=vo.getEmail() %>" readonly>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
					    <legend>성별</legend>
					    <label>여</label><input type="radio" name="gender" value="female" <%="female".equals(vo.getGender()) ? "checked" : "" %>>
					    
					    <label>남</label><input type="radio" name="gender" value="male" <%="male".equals(vo.getGender()) ? "checked" : "" %>>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>