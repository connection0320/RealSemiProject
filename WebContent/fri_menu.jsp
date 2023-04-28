<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
</head>
<body>
<%@include file="header.jsp" %>
<div align="center">
	<table>
		<th>
			<td>
			<a href ="fri_list.to?id=<%=(String)session.getAttribute("UserId")%>">[친구 목록]</a>
			<a href ="fri_add.to?id=<%=(String)session.getAttribute("UserId")%>">[친구 추가]</a>
			</td>
		</th>
	</table>
</div>
<%@inclue file="footer.jsp" %>
</body>
</html>