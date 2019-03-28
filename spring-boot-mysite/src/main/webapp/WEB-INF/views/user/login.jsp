<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var FormValidator = {
	init:function(){
		$("#login-form").submit(this.onFormSubmit.bind(this));
	},
	onFormSubmit:function(){
		if($("#email").val() == ""){
			alert("이메일을입력하세요");
			$("#email").focus();
			return false;
		}
		if($("input[type='password']").val() == ""){
			alert("비밀번호를 입력하세요");
			$("input[type='password']").focus();
			return false;
		}
	}
}

$(function(){
	FormValidator.init();
});
</script>
</head>
<body>
	<div id="container">
	
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		
		<div id="content">
			<div id="user">
				<form id="login-form" name="loginform" method="post" action="${pageContext.servletContext.contextPath }/user/auth">
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="">
						<c:if test="${param.result eq 'fail'}">
							<p>
								로그인이 실패 했습니다.
							</p>
						</c:if>
						
					<input type="submit" value="로그인">
				</form>
			</div>
		</div>
		
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>	
		</div>
		
	</div>
</body>
</html>