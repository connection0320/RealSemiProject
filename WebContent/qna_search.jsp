<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="board_css/board.css">
</head>
<body>
	<%@include file="header.jsp"%>
	<div id="board_head"><span class="sp-title">고객센터 게시판</span></div>
	<div align = "center">
		<br>
		<table class="table">
			<tr>
				<td id="count-td" colspan="6"></td>
			</tr>
			<tr>
				<th>No.</th>
				<th>말머리</th>
				<th>글제목</th>
				<th>작성자</th>
				<th id="writeDate-th">작성 일자</th>
			</tr>
			<c:set var="list" value="${List}" />
			<c:if test="${!empty list}">
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.getQna_num()}</td>
						<td>${dto.getQna_head()}</td>
						<c:if test="${Num == dto.getQna_writer()}">
							<td><a href="<%=request.getContextPath() %>/qna_content.so?num=${dto.getQna_num()}">${dto.getQna_title() }</a></td>				
						</c:if>
						<c:if test="${Num != dto.getQna_writer()}">
							<td>${dto.getQna_title()}</td>	
						</c:if>
						<td>${dto.getQna_nick()}</td>
						<td id="writeDate-td">${dto.getQna_regdate().substring(0, 10)}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list}">
				<tr>
					<td id = "xboard" colspan="7" align="center">
						<h3>전체 게시물 리스트가 없습니다.</h3>
				</tr>
			</c:if>
		</table>
			<c:forEach items="${NickList}" var="ndto">
				<h4>${ndto}</h4>
			</c:forEach>
		<br>
		<% 
		if(session.getAttribute("UserId") == null){
		%>
		<h5>로그인 후 이용해주세요.</h5>
		<% 
			}else{
		%>
		<input id="write_bt" type = "button" value = "글쓰기" onclick = "location.href='qna_write.so?'">
		<input id="cont_bt" type = "button" value = "목록" onclick = "location.href='qna_list.so?id=${User}'">
		<%	
			}
		%>
		<br>
		<form method = "post" action ="<%=request.getContextPath() %>/qna_search.so">
			<select name = "field">
				<option value = "head">말머리</option>
				<option value = "title">제목</option>
				<option value = "writer">작성자</option>
			</select>
			<input type = "text" name = "keyword">&nbsp;&nbsp;
			<input type = "submit" value = "검색">
		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>