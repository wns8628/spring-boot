<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<a href="http://naver.com" target="_blank"><img src= "${pageContext.servletContext.contextPath }/assets/images/sp.svg" style="width:100px" ></a>
	<h1>MySite</h1>
	<ul>
			<!-- 
			UserVo authUser = (UserVo)session.getAttribute("authuser");
			if(null == authUser){
			
			이거없이 el jstl쓰자 
			-->	
			<c:choose>
				<c:when test="${empty authuser}">
					<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a><li>
					<li><a href="${pageContext.servletContext.contextPath }/user/join">회원가입</a><li>
				</c:when>
				<c:otherwise>
					<c:if test="${authuser.role eq 'ADMIN'}">
						<li><a href="${pageContext.servletContext.contextPath }/admin">관리자페이지</a><li>   
					</c:if>
					<li><a href="${pageContext.servletContext.contextPath }/user/modify">회원정보수정</a><li>
					<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a><li>
					<li>${authuser.name }님 안녕하세요 ^^;</li>				
				</c:otherwise>
			</c:choose>
	</ul>