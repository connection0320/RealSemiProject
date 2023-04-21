<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="board_css/board.css">
</head>
<body>
	<%@include file="header.jsp"%>
	<div id="board_head"><span class="sp-title">고객센터 게시판</span></div>
	<div align="center">
		<br>
		<a href="<%=request.getContextPath() %>/qna_list.so?id=<%=(String)session.getAttribute("UserId")%>">[전체 Q&A 게시물 목록]</a>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>