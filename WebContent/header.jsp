<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="main.jsp"> 메인페이지</a>
	<a href="myinformation.jsp">내 정보</a>
	<a href="qna.jsp">QnA</a>
	
	<%
		if(session.getAttribute("UserId") == null){
	%>
			<a href="login.jsp">로그인</a>
	
	<% 
		}else{
			String userId = (String)session.getAttribute("UserId");
			out.println(userId + "님 반갑습니다." );
			out.println("<a href='logout.jsp'>로그아웃</a>" );
		}
		
	%>  
	
	
	<hr>
</body>
</html>