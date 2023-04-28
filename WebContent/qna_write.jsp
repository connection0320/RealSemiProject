<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String loginId = (String)session.getAttribute("UserId");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO mdto = mdao.contentById(loginId);
	String nick = mdto.getMember_nick();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="board_css/board.css">
<script type="text/javascript">
	function check() {
		if(f.field.value == "") {
			alert("말머리를 선택하세요.");
			f.field.focus();
			return false;
		}
		if(f.title.value == "") {
			alert("글 제목을 입력하세요.");
			f.title.focus();
			return false;
		}
		if(f.cont.value == "") {
			alert("글 내용을 입력하세요.");
			f.cont.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<%@include file="header.jsp"%>
	<div id="board_head"><span class="sp-title">고객센터 게시판 글쓰기</span></div>
	<div align = "center">
		<br>
		<form method = "post" name="f" action = "<%=request.getContextPath() %>/qna_write_ok.so" enctype = "multipart/form-data" onsubmit="return check()">
			<table class="table">
				<tr>
				<th id="contTh">말머리</th>
					<td id="contTd">
						<select name = "field">
							<option value="">말머리 선택</option>
							<option value = "propose">건의</option>
							<option value = "account">계정</option>
							<option value = "report">신고</option>
							<option value = "else">기타</option>
						</select>
					</td>
				</tr>
				<tr>
					<th id="contTh">작성자</th>
					<td id="contTd"><input type = "text" value = "<%=nick %>" name = "nick" readonly></td>
				</tr>
				<tr>
					<th id="contTh">제목</th>
					<td id="contTd"><input name = "qna_title"></td>
				</tr>
				<tr>
					<th id="contTh">내용</th>
					<td id="contTd"><textarea rows = "7" cols = "25" name = "qna_cont"></textarea>
				</tr>
				<tr>
					<th id="contTh">첨부파일</th>
					<td id="contTd"><input type = "file" name = "qna_file"></td>
				</tr>
				<tr>
					<th id="contTh">글 비밀번호</th>
					<td id="contTd"><input type = "password" name = "qna_pwd"></td>
				</tr>
			</table>
			<br>
			<input id="cont_bt" type = "submit" value = "글쓰기">&nbsp;&nbsp;
			<input id="cont_bt" type = "reset" value = "다시작성">
		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>