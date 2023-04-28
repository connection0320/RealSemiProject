<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@page import="com.MapBoard.model.MapBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%
	MapBoardDTO cont = (MapBoardDTO)request.getAttribute("Modify");
	String location = request.getParameter("location"); 
	String UserId = (String)session.getAttribute("UserId");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO dto = mdao.contentById(UserId);
	String nick = dto.getMember_nick();
	int pagee = Integer.parseInt(request.getParameter("page"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="board_css/board.css">
<script type="text/javascript">

	onload =()=>{
		let op = document.getElementsByClassName("op");
		for(let i=0; i<op.length; i++) {
			if(op[i].text == "<%=cont.getBoard_head()%>") {
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
	<div align="center">
		<c:set var="dto" value="${Modify }" />
		<div id="board_head"><span class="sp-title"><%=nick %>님 게시물 수정 페이지</span></div>
		<br><br><br>
		
		<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/board_modify_ok.go?&page=<%=pagee%>" onsubmit="return check()">
		<input type="hidden" name="no" value="${dto.getBoard_num() }">
		
			<table class="table">
				<tr>
					<th id="contTh">말머리</th>
						<td id="contTd">	
							<select name="field">
								<option class="op" value="dona">나눔</option>
								<option class="op" value="boast">자랑</option>
								<option class="op" value="share">정보 공유</option>
								<option class="op" value="free">자유글</option>
							</select>
						</td>
				</tr>
			
				<tr>
					<th id="contTh">작성자</th>
						<td id="contTd">
							<input name="writer" value="<%=nick %>" readonly>
						</td>
				</tr>
				
				<tr>
					<th id="contTh">제목</th>
						<td id="contTd">
							<input name="title" value="${dto.getBoard_title() }">
						</td>
				</tr>
				
				<tr>
					<th id="contTh">내용</th>
						<td id="contTd">
							<textarea rows="7" cols="25" name="cont" style="width:189px; margin-top: 4px;">${dto.getBoard_text() }</textarea>
						</td>
				</tr>
				
				<tr>
					<th id="contTh">첨부파일</th>
						<td id="contTd">
							<input type="file" name="file">
						</td>
				</tr>
				
				</table>
				<br>
					
				<div>
					<input id="cont_bt2" type="submit" value="수정">&nbsp;&nbsp;
					<input id="cont_bt2" type="button" value="목록" onclick="location.href='board_list.go?location=<%=cont.getBoard_area()%>&page=<%=pagee%>'">
				</div>
			
		</form>
	<%@include file="footer.jsp"%>		
	</div>

</body>
</html>