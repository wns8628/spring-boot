<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				 	 class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/reply">
					<input type = "hidden" name = "gNo" value="${boardVo.gNo }">
					<input type = "hidden" name = "oNo" value="${boardVo.oNo }">
					<input type = "hidden" name = "depth" value="${boardVo.depth }">
					<input type = "hidden" name = "pageNo" value="${param.pageNo }">
					<input type = "hidden" name = "no" value="${param.no }">
					<input type = "hidden" name = "kwd" value="${param.kwd }">
					
					
					
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
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
								<textarea name="contents" id="content"></textarea>
								<p style="margin:0; padding:0; color:red; text-aling:left">
									<form:errors path="contents"/>
								</p>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/view?pageNo=${param.pageNo}&no=${param.no}&kwd=${param.kwd}">취소</a>
						<input type="submit" value="등록">
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