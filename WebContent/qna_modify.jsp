<%@page import="com.qna.model.QnaDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	QnaDTO cont = (QnaDTO)request.getAttribute("Modify");
	int pagee = Integer.parseInt(request.getParameter("page"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="board_css/board.css">
<script type="text/javascript">

	onload =()=>{
		let op = document.getElementsByClassName("op");
		for(let i=0; i<op.length; i++) {
			if(op[i].text == "<%=cont.getQna_head()%>") {
				op[i].setAttribute("selected", true);
			}
		}
	}

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
	<div align = "center">
		<c:set var = "dto" value = "${Modify}" />
		<div id="board_head"><span class="sp-title">${Nick}님 게시물 수정 페이지</span></div>
		<br>
		<form method = "post" enctype="multipart/form-data" action = "<%=request.getContextPath() %>/qna_modify_ok.so" onsubmit="return check()">
			<input type = "hidden" name = "num" value = "${dto.getQna_num()}">
			<input type = "hidden" name = "qna_writer" value = "${dto.getQna_writer()}">
			<input type = "hidden" name = "page" value = "${page}">
			<table class="table">
				<tr>
					<th id="contTh">말머리</th>
						<td id="contTd">	
						<select name = "qna_field">
							<option class="op" value = "propose">건의</option>
							<option class="op" value = "account">계정</option>
							<option class="op" value = "report">신고</option>
							<option class="op" value = "else">기타</option>
						</select>
					</td>
				</tr>
				<tr>
					<th id="contTh">작성자</th>
					<td id="contTd"><input type = "text" value = "${Nick}" name = "qna_nick" readonly></td>
				</tr>
				<tr>
					<th id="contTh">제목</th>
					<td id="contTd"><input type = "text" name = "qna_title" value = "${dto.getQna_title()}"></td>
				</tr>
				<tr>
					<th id="contTh">내용</th>
					<td id="contTd"><textarea rows = "7" cols = "25" name = "qna_content">${dto.getQna_content()}</textarea></td>
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
				<div>
					<input id="cont_bt" type="submit" value="수정">&nbsp;&nbsp;
					<input id="cont_bt" type = "reset" value = "다시작성">&nbsp;&nbsp;
					<input id="cont_bt" type="button" value="목록" onclick= "location.href='qna_list.so?id=${User}&page=<%=pagee%>'">
				</div>
		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>