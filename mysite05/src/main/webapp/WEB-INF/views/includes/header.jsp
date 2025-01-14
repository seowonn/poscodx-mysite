<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSP에는 내부적으로 HttpSession session = request.getSession(true); 가 삽입되어 있다. -->
<script>
	// 브라우저가 내장한 객체로, JS에서 접근 가능하다.
	console.log("hello");
	
	// HTML 문서는 위에서 아래 방향으로 실행된다. 따라서 이 값은 아직 body 태그가 나오기 전이라 null로 나온다.
	console.log(document.body);
	
	// 브라우저에 이벤트를 걸 수 있다. 
	// DOM뿐만 아니라 모든 리소스(HTML, CSS, 이미지 등)가 다 로딩된 후에 실행된다.
	window.addEventListener("load", function f() {
		console.log(document.body); // 이제 정상적으로 출력됨
		
		// id가 languages인 영역 중 a 태그를 nodeList 형태로 반환
		anchors = document.querySelectorAll('#languages a');
		
		anchors.forEach(function(el) {
			el.addEventListener("click", function() {
				// click이 나오고 바로 사라짐.
				// 이유: a 태그가 링크 이동이라 바로 이동하기 때문
				event.preventDefault(); // 따라서 a 태그의 기본동작을 막아 리로딩을 막음
				console.log("click!!!");
				console.log(this.getAttribute('data-lang'));
				
				document.cookie = 
					"lang=" + this.getAttribute('data-lang') + ";" +
					"path=" + "${pageContext.request.contextPath}" + ";" + // EL은 서버에서 미리 처리돼서 전달됨
					"max-age=" + (30*24*60*60);
				
				// 새 cookie 값을 기반으로 페이지를 다시 (현재 페이지를) 렌더링 함.
				location.reload();
			})
		})
	}); // call back 함수: 바로 실행되지 않고 호출이 되면 실행되는 함수
</script>
	<div id="header">
		<!-- ${siteVo.title }: ServletContext의 site에 접근하는 경우 -->
		<h1>${site.title}</h1> <!-- JSP에서 Bean에 접근하여 정보를 가져오는 경우 -->
		<div id="languages">
			<c:choose>
				<c:when test="${lang == 'en' }">
					<!-- data-lang은 사용자 정의 속성 -->
					<a href="" data-lang="ko">KO</a><a href="" class="active" data-lang="en">EN</a>
				</c:when>
				<c:otherwise>
					<a href="" data-lang="ko" class="active">KO</a><a href="" data-lang="en">EN</a>
				</c:otherwise>
			</c:choose>
		</div>
		<ul>
			<c:choose>
				<c:when test="${empty authUser}">
					<li><a href="${pageContext.request.contextPath}/user/login"><spring:message code="header.gnb.login"/></a><li>
					<li><a href="${pageContext.request.contextPath}/user/join"><spring:message code="header.gnb.join"/></a><li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageContext.request.contextPath}/user/update"><spring:message code="header.gnb.greeting"/></a><li>
					<li><a href="${pageContext.request.contextPath}/user/logout"><spring:message code="header.gnb.logout"/></a><li>
					<li>${authUser.name}님 안녕하세요 ^^;</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>