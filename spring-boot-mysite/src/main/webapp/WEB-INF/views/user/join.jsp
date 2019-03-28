<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var FormValidator = {

		$imageCheck: null,
		$buttonCheckEmail: null,
		$inputTextEmail: null,
		
		
		init: function(){		
			this.$imageCheck = $( "#img-checkemail" );
			this.$buttonCheckEmail = $( "#btn-checkemail" );
			this.$inputTextEmail = $( "#email" );
			
			this.$inputTextEmail.keypress(this.onEmailInputTextKeypressd.bind(this));
			this.$buttonCheckEmail.click(this.onAjaxCheckEmailButtonClicked.bind(this));			
			$("#join-form").submit(this.onFormSubmit.bind(this));
		},
			
		onFormSubmit: function(){
			//1.이름체크
			if($("#name").val() == ""){
				alert("이름을입력하세요");
				$("#name").focus();
				return false;
			}

			//2-1.이메일체크
			if($("#email").val() == ""){
				alert("이메일을입력하세요");
				$("#email").focus();
				return false;
			}
			//2-2 이메일 중복체크
 			if(this.$imageCheck.is(":visible") == false){
				alert("이메일 중복체크를 해야합니다.");
				return false;
			} 
			
			//3. 비밀번호 확인
			if($("input[type='password']").val() == ""){
				alert("비밀번호를 입력하세요");
				$("input[type='password']").focus();
				return false;
			}
			
			//4. 약관동의
			if( $("#agree-prov").is(":checked")== false){
				alert("약관동의를 체크해야 회원가입이 됩니다.");
				return false;
			}
			//인증됨!
			return true;
		},
		
		onEmailInputTextKeypressd: function(){
			this.$buttonCheckEmail.show();
			this.$imageCheck.hide();
		},
		
		onAjaxCheckEmailButtonClicked:function(){
			var email =$("#email").val();
			if(email == ""){
				return;
			}
			$.ajax({
				url:"${pageContext.servletContext.contextPath }/user/api/checkemail?email=" + email,
				type:"get",
				dataType:"json",
				//data:"",
				success: this.onCheckEmailAjaxSuccess.bind(this),
				error: this.onCheckEmailAjaxError.bind(this)
			});
		},
		
		onCheckEmailAjaxSuccess : function(response){
			//통신실패
			if(response.result == "fail"){
				console.error(response.message);
				return;
			}
			
			if(response.data  == true){
				alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요");
				$("#email").val("").focus();
				return;
			}else{
				//사용가능한 이메일 
				this.$buttonCheckEmail.hide();
				this.$imageCheck.show();			
			}

		},
		
		onCheckEmailAjaxError:function(xhr, status, e){
			console.error(status+":"+e);
		}
} 	

/*
$(function(){
	FormValidator.init();
});
 */
</script>
</head>
<body>
	<div id="container">
	
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		
		<div id="content">
			<div id="user">

				<form:form
					 modelAttribute="userVo"
					 id="join-form" 
					 name="joinForm" 
					 method="post" 
					 action="${pageContext.servletContext.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${userVo.name }">
						<spring:hasBindErrors name="userVo">
						   <c:if test="${errors.hasFieldErrors('name') }">
						   		<p style="text-align:left; color:red">
						  	      <strong>
						  	      	<spring:message 
						  	     		code="${errors.getFieldError( 'name' ).codes[0] }"
						  	     		text="${errors.getFieldError( 'name' ).defaultMessage }"/>
						  	      	</strong>
						        </p>
						   </c:if>
						</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<%-- 
					<input path="email" id="email" name="email" type="text" value="${userVo.email }">
					 --%>					 
					<form:input path="email"/>
					<p style="margin:0; padding:0; color:red; text-aling:left">
						<form:errors path="email"/>
					</p>
					
					<input id="btn-checkemail" type="button" value="이메일체크">
					<img id="img-checkemail"  style="width:20px; display:none" src="${pageContext.servletContext.contextPath }/assets/images/check.png"/>
						
					
					<label class="block-label">패스워드</label>
					<form:password path="password"/>
					
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
		
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
			</c:import>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>	
		</div>
		
	</div>
</body>
</html>