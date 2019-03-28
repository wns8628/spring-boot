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
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		
		<div id="content">
			<div id="board">
				<form:form 
					modelAttribute="boardVo" 
				    class="board-form" 
				    method="post" 
				    action="${pageContext.servletContext.contextPath }/board/modify">

					<input type="hidden" name="pageNo" value="${param.pageNo}">					
					<input type="hidden" name="no" value="${boardVo.no}">
					<input type="hidden" name="kwd" value="${param.kwd}">					
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td>
								<form:input path="title"/>
								<p style="margin:0; padding:0; color:red; text-aling:left">
									<form:errors path="title"/>
								</p>
							</td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<form:textarea path="contents" id="content"/>
								<p style="margin:0; padding:0; color:red; text-aling:left">
									<form:errors path="contents"/>
								</p>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/view?pageNo=${param.pageNo}&no=${boardVo.no}&kwd=${param.kwd}">취소</a>
						<input type="submit" value="수정">
					</div>
				</form:form>				
			</div>
		</div>
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
				<c:param name="menu" value="board"/>
			</c:import>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>	
		</div>
	</div>
</body>
</html>