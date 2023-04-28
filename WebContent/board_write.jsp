<%@page import="com.MapBoard.model.MapBoardDTO"%>
<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
   String location = request.getParameter("location"); 
   String UserId = (String)session.getAttribute("UserId");
   MemberDAO mdao = MemberDAO.getInstance();
   MemberDTO dto = mdao.contentById(UserId);
   String nick = dto.getMember_nick();
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
    <div align="center">
	<div id="board_head"><span class="sp-title"><%=location %> 게시판 </span></div>
	<br>
	<form method="post" name="f" action="<%=request.getContextPath() %>/board_write_ok.go"
		enctype="multipart/form-data" onsubmit="return check()">
		<input type="hidden" name="location" value="<%=location%>">
		
		<table class="table">
			<tr>
				<th id="contTh">말머리</th>
					<td id="contTd">
						<select name="field">
							<option value="">말머리 선택</option>
							<option value="dona">나눔</option>
							<option value="boast">자랑</option>
							<option value="share">정보 공유</option>
							<option value="free">자유글</option>
						</select>
					</td>
			</tr>
			
			<tr>
				<th id="contTh">작성자</th>
					<td id="contTd">
						<input type="text" value="<%=nick %>" name="nick" readonly>
					</td>
			</tr>
			
			<tr>
				<th id="contTh">글제목</th>
					<td id="contTd">
						<input type="text" name="title">
					</td>
			</tr>
			
			<tr>
				<th id="contTh">글내용</th>
					<td id="contTd">
						<textarea rows="7" cols="25" name="cont" style="width:189px; margin-top: 4px;"></textarea>
					</td>
			</tr>
			
			<tr>
				<th id="contTh">첨부 파일</th>
					<td id="contTd">
						<input type="file" name="file">
					</td>
			</tr>
			
		</table>
		<br>
		
		<div class="submit1">
			<input id="cont_bt" type="submit" value="글쓰기">&nbsp;&nbsp;
			<input id="cont_bt" type="reset" value="다시 작성">

		</div>
	</form>
	<%@include file="footer.jsp"%>
	</div>

</body>
</html>