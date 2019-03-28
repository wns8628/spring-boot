<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  

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
		
		<c:choose>
			<c:when test="${!empty authuser }">
				<div id="content">
					<div id="board">
						<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/commentreply">
							<input type='hidden' name="pageNo" value="${param.pageNo }">
							<input type='hidden' name="boardNo" value="${param.boardNo }">
							<input type='hidden' name="kwd" value="${param.kwd }">							
							<input type = "hidden" name = "gNo" value="${vo.gNo }">
							<input type = "hidden" name = "oNo" value="${vo.oNo }">
							<input type = "hidden" name = "depth" value="${vo.depth }">
						
							<table class="tbl-ex">
								<tr>
									<th colspan="2">대댓글</th>
								</tr>
		
								<tr>
									<td class="label">닉네임</td>
									<td>${vo.name }</td>
								</tr>
								<tr>
									<td class="label">내용</td>
									<td>
										<textarea id="content" name="contents"></textarea>
									</td>
								</tr>
							</table>
							<div class="bottom">
								<a href="${pageContext.servletContext.contextPath }/board/view?pageNo=${param.pageNo}&no=${param.boardNo}&kwd=${param.kwd}">취소</a>
								<input type="submit" value="대댓글달기">
							</div>
						</form>				
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div id="content">
					<div id="board">
						<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/commentreply">
							<input type='hidden' name="pageNo" value="${param.pageNo }">
							<input type='hidden' name="boardNo" value="${param.boardNo }">
							<input type='hidden' name="kwd" value="${param.kwd }">							
							<input type = "hidden" name = "gNo" value="${vo.gNo }">
							<input type = "hidden" name = "oNo" value="${vo.oNo }">
							<input type = "hidden" name = "depth" value="${vo.depth }">
						
							<table class="tbl-ex">
								<tr>
									<th colspan="2">대댓글</th>
								</tr>
		
								<tr>
									<td class="label">닉네임</td>
									<td><input type="text" name="name" value="" /></td>
								</tr>
								<tr>
									<td class="label">비번</td>
									<td><input type="password" name="password" value=""></td>
								</tr>
								<tr>
									<td class="label">내용</td>
									<td>
										<textarea id="content" name="contents">
										</textarea>
									</td>
								</tr>
							</table>
							<div class="bottom">
								<a href="${pageContext.servletContext.contextPath }/board/view?pageNo=${param.pageNo}&no=${param.boardNo}&kwd=${param.kwd}">취소</a>
								<input type="submit" value="대댓글달기">
							</div>
						</form>				
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
		
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