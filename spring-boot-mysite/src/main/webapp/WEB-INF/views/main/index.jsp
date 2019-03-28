<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${siteVo.title }</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
 					 <img id="profile"
						 onerror="this.src='${pageContext.request.contextPath }/assets/images/defaultman.png'" 
						 src="${pageContext.servletContext.contextPath }${siteVo.profile}" style="width:100px">
					<h2>${siteVo.welcome }</h2>
					<p>
						${ siteVo.description } <br><br>
						<a href="${pageContext.servletContext.contextPath }/guestbook?a=list">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
				<c:param name="menu" value="main"/>
			</c:import>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>	
		</div>
		
	</div>
</body>
</html>