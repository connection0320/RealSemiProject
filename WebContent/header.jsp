<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel ="stylesheet" href="s.css/head.css">
</head>
<body>
	<header id="head">
	
<!-- 	<img src ="s.image/house.png">
 -->	
	 <div  class="head_logo">
	<a href="main.jsp">
	 	<img src="s.image/r_logo_s.png">
	</a>
	 </div>
	<% 
		if(session.getAttribute("UserId") == null){
	%>
	<a href="m_myinformation.jsp" class="qna">내 정보 </a>
	<% 
		}else{
	%>
	<a href="content.member.do?id=<%=(String)session.getAttribute("UserId")%>" class="qna">내 정보</a>

	<%	
		}
	%>

	<a href="qna_main.jsp" class="qna">고객센터</a>

	<%
		if(session.getAttribute("UserId") == null){
	%>
	<a href="m_login.jsp" class="qna">로그인</a>

	<% 
		}else{
			%>
			<div class="qna">
			<% 	
			String userId = (String)session.getAttribute("UserId");
			out.println("<a href='m_logout.jsp'>로그아웃</a>");
			%>
			</div>
		<%

		}
		
	%>

	</header>
</body>
</html>