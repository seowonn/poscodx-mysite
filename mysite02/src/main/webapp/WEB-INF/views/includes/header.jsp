<%@page import="mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSP에는 내부적으로 HttpSession session = request.getSession(true); 가 삽입되어 있다. -->
<%
	UserVo authUser = (UserVo) session.getAttribute("authUser");	
%>
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<%
					if(authUser == null) {
				%>
					<li><a href="<%=request.getContextPath() %>/user?a=loginform">로그인</a><li>
					<li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a><li>
				<%
					} else {
				%>
					<li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a><li>
					<li><a href="<%=request.getContextPath() %>/user?a=logoutform">로그아웃</a><li>
					<li><%=authUser.getName() %>님 안녕하세요 ^^;</li>
				<%
					}
				%>
			</ul>
		</div>