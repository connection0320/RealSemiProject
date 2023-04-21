<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

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
		<c:set var = "dto" value = "${Content}" />
		<div id="board_head"><span class="sp-title">${Nick}님 고객센터 상세내역</span></div>
		<br>
		<table class="table">
			<tr>
				<th id="contTh">글 No.</th>
				<td id="contTd">${dto.getQna_num()}</td>
			</tr>
			<tr>
				<th id="contTh">말머리</th>
				<td id="contTd">${dto.getQna_head()}</td>
			</tr>
			<tr>
				<th id="contTh">작성자</th>
				<td id="contTd">${Nick}</td>
			</tr>
			<tr>
				<th id="contTh">글 제목</th>
				<td id="contTd">${dto.getQna_title()}</td>
			</tr>
			<tr>
				<th id="contTh">글 내용</th>
				<td id="contTd">${dto.getQna_content()}</td>
			</tr>
			<tr>
				<th id="contTh">첨부파일</th>
				<c:if test="${!empty dto.getQna_file()}">
					<td id="contTd"><a href = "<%=request.getContextPath()%>/qnaFileUpload/${dto.getQna_file()}" target = "_blank">${dto.getQna_file()}</a></td>
				</c:if>
				<c:if test="${empty dto.getQna_file()}">
				<td id="contTd"></td>
				</c:if>
			</tr>
			<tr>
				<th id="contTh">조회 수</th>
				<td id="contTd">${dto.getQna_hit()}</td>
			</tr>
			<tr>
				<th id="contTh">작성 날짜</th>
				<td id="contTd">${dto.getQna_regdate()}</td>
			</tr>
			<tr>
				<th id="contTh">수정 날짜</th>
				<c:if test="${empty dto.getQna_update()}">
					<td id="contTd">${dto.getQna_update()}</td>
				</c:if>
				<c:if test="${!empty dto.getQna_update()}">
					<td id="contTd">${dto.getQna_update()}</td>
				</c:if>
			</tr>
			<tr>
				<th id="contTh">글 비밀번호</th>
				<td id="contTd">
					<c:if test="${!empty dto.getQna_pwd()}">
						<c:forEach begin="1" end="${dto.getQna_pwd().length()}">
							*
						</c:forEach>
					</c:if>
				</td>
			</tr>
			<%-- 데이터가 없는 경우 --%>
			<c:if test="${empty dto}">
				<tr>
					<td id = "xboard" colspan = "2" align = "center">
						<h3>조회된 게시물이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br>
		<input id="cont_bt2" type = "button" value = "수정" onclick = "location.href='qna_modify.so?no=${dto.getQna_num()}&page=${page}'">&nbsp;
		<input id="cont_bt2" type = "button" value = "삭제" onclick = "if(confirm('게시글을 삭제 하시겠습니까?')) {
			location.href='qna_delete.so?no=${dto.getQna_num()}&page=${page}'
			} else { return; }">&nbsp;
		<input id="cont_bt2" type = "button" value = "목록" onclick = "location.href='qna_list.so?id=${User}&page=${page}'">
		<br><br>
		
		
		<%-- 댓글 폼 영역입니다. --%>
		<form method ="post"  action = "qna_reply_insert_ok.so">
			<div>
				<input type="hidden" name="qno" value="${dto.getQna_num()}">
				<input type="hidden" name="page" value="${page}">
				<table class="table">
					<tr>
						<th id="contTh">작성자</th>
						<td id="contTd"><input type = "text" name = "re_writer" id = "re_writer"></td>
					</tr>
					<tr>
						<th id="contTh">댓글내용</th>
						<td id="contTd"><input type = "text" name = "re_content" id = "re_content"></td>
					</tr>
				</table>
				<br>
				<div>
					<button id="cont_bt" type="submit">댓글작성</button>
				</div>
			</div>
		</form>
		
		<br>
		<h3>댓글 목록</h3>
		<div>
			<table class="table">
			<tr>
				<th id="contTh2">작성자</th>
				<th id="contTh1">댓글내용</th>
			</tr>
			
			<c:if test="${!empty Qlist}">
				<c:forEach items="${Qlist}" var="dto">
					<tr>
						<td id="contTd2">${dto.getQna_rewriter()}</td>
						<td id="contTd">${dto.getQna_recont()}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty Qlist}">
				<tr>
					<td id = "xboard" colspan="7" align="center">
						<h3>작성된 댓글이 없습니다.</h3>
				</tr>
			</c:if>
		</table>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>