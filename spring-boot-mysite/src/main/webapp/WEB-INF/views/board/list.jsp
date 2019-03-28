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
	
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${page.kwd}">
					<input type="submit" value="찾기">
				</form>
				
				<table class="tbl-ex">
				
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>	
		
					

					<c:set var="totalboard" value="${page.totalboardcount}"/>
					<c:set var="pageNo" value="${page.pageNo }" />	
					<c:set var="manyboard" value="${page.manyboard }" />	
					
					<c:forEach items="${list}" var="vo" varStatus="status">				
						<tr>
						<c:if test="${vo.depth == 0}" >						
							<td>${ totalboard - ((pageNo * manyboard) - (manyboard-status.index)) }</td>
						</c:if>			
						<c:if test="${vo.depth > 0}" >
							<td>${ totalboard - ((pageNo * manyboard) - (manyboard-status.index)) }<sup style="color: tomato"><strong>re</strong></sup></td>
						</c:if>			
							<td style="padding-left:${15*vo.depth}px">
								<c:if test="${vo.depth > 0}" >
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.servletContext.contextPath }/board/view?pageNo=${page.pageNo }&no=${vo.no}&kwd=${page.kwd}">
									${ vo.title }<span style="color: red">(${ vo.commentCount })</span>
								</a>
							</td>
						
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.writeDate }</td>
							<td>
								<a href="${pageContext.servletContext.contextPath }/board/delete?pageNo=${page.pageNo }&no=${vo.no}&kwd=${page.kwd}" class="del">
									삭제
								</a>
							</td>
						</tr>
	
					</c:forEach>
					
				</table>
				
				
				<c:if test="${!empty authuser }">
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/write?pageNo=${page.pageNo}&kwd=${page.kwd}" id="new-book">글쓰기</a>
					</div>			
				</c:if>
				
				
				<!-- 페이지네이션 -->
				<c:choose>
					<c:when test="${page.endPage < page.totalpage }">					
						<c:set var="limitnumber" value="${page.endPage }"/>		
					</c:when>	
					<c:otherwise>							
						<c:set var="limitnumber" value="${page.totalpage }"/>		
					</c:otherwise>
				</c:choose>		
						
					<div class="pager">
		               <ul>	            
		               
						<li><a href="${pageContext.servletContext.contextPath }/board?pageNo=1&kwd=${page.kwd}">◁◁</a></li>   
						<li><a href="${pageContext.servletContext.contextPath }/board?pageNo=${page.pageNo-1}&kwd=${page.kwd}">◀</a></li>		                  
						  <c:forEach var="i" begin="${page.startPage }" end="${limitnumber}" step="1">	  							  	
						  	<c:choose>				
						  		<c:when test="${page.pageNo == i}">
								  	 <li class="selected">
					                  	<a href="${pageContext.servletContext.contextPath }/board?pageNo=${i}&kwd=${page.kwd}">
					                  		${i}
					                  	</a>
				                  	 </li>
						  		</c:when>
						  		<c:otherwise>
						  			 <li>
					                  	<a href="${pageContext.servletContext.contextPath }/board?pageNo=${i}&kwd=${page.kwd}">
					                  		${i}
					                  	</a>
				                  	 </li>
						  		</c:otherwise>
						  	</c:choose>				           
						  </c:forEach>					           
		                 <li><a href="${pageContext.servletContext.contextPath }/board?pageNo=${page.pageNo+1}&kwd=${page.kwd}">▶</a></li>
		                 <li><a href="${pageContext.servletContext.contextPath }/board?pageNo=${page.totalpage}&kwd=${page.kwd}">▷▷</a></li>	               
		               </ul>
		            </div>
		         <!--  --> 
   
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