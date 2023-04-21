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
	<div align = "center">
		<div id="board_head"><span class="sp-title">고객센터 작성 게시글 삭제 페이지</span></div>
		<br>
		<form method = "post" action = "<%=request.getContextPath() %>/qna_delete_ok.so">
			<input type = "hidden" name = "qna_num" value = "${param.no}">
			<table class="table">
				<tr>
					<th id="contTh">삭제할 게시글<br> 비밀번호</th>
					<td id="contTd"><input type = "password" name = "pwd"></td>
				</tr>
			</table>
			<br>
			<div>
				<input id="cont_bt" type = "submit" value = "글삭제">&nbsp;&nbsp;
				<input id="cont_bt" type = "reset" value = "다시작성">
			</div>
		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>