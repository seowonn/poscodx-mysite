<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	$(function() {
		var el = $("#btn-check");
		el.click(function() {
			var email = $("#email").val();
			if(email == "") {
				return;
			}
		
			$.ajax({
				url: "${pageContext.request.contextPath}/api/user/checkemail?email=" + email,
				type: "get",
				dataType: "json",
				success: function(response) {
					if(response.result != "success") {
						console.error(reponse.message);
						return;
					}
					
					if(response.data.exists) {
						alert("이메일이 존재합니다. 다른 이메일을 사용해 주세요.");
						$("#email").val("");
						$("#email").focus();
						
						return;
					}
					
					$("#img-check").show();
					$("#btn-check").hide();
				},
				error: function(xhr, status, err) {
					console.error(err);
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form
					modelAttribute="userVo"
					action="${pageContext.request.contextPath}/user/join"
					method="post">
					
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<p style="padding: 5px 0; margin:0; color:#f00">	
						<form:errors path="name" />
					</p>
					
					<label class="block-label" for="email"><spring:message code="user.join.label.email" /></label>
					<form:input path="email" />
					<input id="btn-check" type="button" value="중복 확인" style="display:;">
					<img id="img-check" src="${pageContext.request.contextPath}/assets/images/check.png" 
					style="vertical-align:bottom; width:24px; display: none">
					<p style="padding: 5px 0; margin:0; color:#f00">	
						<form:errors path="email" />
					</p>
					
					<label class="block-label">패스워드</label>
					<form:password path="password" />
					<p style="padding: 5px 0; margin:0; color:#f00">	
						<form:errors path="password" />
					</p>
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>