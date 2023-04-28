<%@page import="com.qna.model.QnaDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String member_id = request.getParameter("Nick");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO mdto = mdao.contentById(member_id);
	String member_nick = mdto.getMember_nick();
	ArrayList<QnaDTO> list = (ArrayList<QnaDTO>)request.getAttribute("list");
	String id = (String)session.getAttribute("UserId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="board_css/board.css">
<style type="text/css">
.pagination {
	justify-content: center;
}
</style>
</head>
<body>
	<%@include file="header.jsp"%>
	<div id="board_head"><span class="sp-title">고객센터 게시판</span></div>
	<div align="center">
		<br>
		<table class="table">
			<tr>
				<td id="count-td" colspan="6" align="right">전체 게시물 수 : ${totalRecord}개
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
						<c:if test="${Num == dto.getQna_writer() or Num eq 999}">
							<td><a href="<%=request.getContextPath() %>/qna_content.so?num=${dto.getQna_num()}&page=${page}">
							${dto.getQna_title() }</a></td>				
						</c:if>
						<c:if test="${Num != dto.getQna_writer() and Num ne 999}">
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
		<!-- Button trigger modal -->
		<% 
		if(session.getAttribute("UserId") == null){
		%>
		<h5>로그인 후 이용해주세요.</h5>
		<% 
			}else{
		%>
		<input id="write_bt" type = "button" value = "글쓰기" onclick = "location.href='qna_write.so?'">
		<%	
			}
		%>
		
		<br>
		<br>
		<form method="post" action="<%=request.getContextPath() %>/qna_search.so?id=<%=id%>">
			<select name="field">
				<option value="head">말머리</option>
				<option value="title">제목</option>
				<option value="writer">작성자</option>
			</select> 
			<input type="text" name="keyword">&nbsp;&nbsp; <input type="submit" value="검색">
			<input type="hidden" name="nick" value ="${dto.getQna_nick()}">
		</form>
		<br>
		<br>
		<%-- 페이징 처리 영역 --%>
		<nav id = "pageNo">
			<ul class = "page">
				<li>
					<a class = "page-link" href = "qna_list.so?page=1"  style="color:#000;"><span aria-hidden="true">&laquo;</span></a>
				</li>
				<li>
					<a class = "page-link" href = "qna_list.so?page=${page - 1}"  style="color:#000;"><span aria-hidden="true">&lsaquo;</span></a>
				</li>
				<c:forEach begin="${startBlock}" end="${endBlock}" var="i">
					<c:if test="${i == page}">
						<li class = "page-item" aria-current="page">
							<a class = "page-link" href = "qna_list.so?page=${i}"  style="color:#000;">${i}</a>
						</li>
					</c:if>
					<c:if test="${i != page}">
						<li class = "page-item">
							<a class = "page-link" href = "qna_list.so?page=${i}" style="color:#000;">${i}</a>
						</li>
					</c:if>
				</c:forEach>
				<li>
					<a class = "page-link" href = "qna_list.so?page=${page + 1}" style="color:#000;"><span aria-hidden="true">&rsaquo;</span></a>
				</li>
				<li>
					<a class="page-item" href = "qna_list.so?page=${allpage}"><span aria-hidden="true">&raquo;</span></a>
				</li>
			</ul>
		</nav>
		<%@include file="footer.jsp"%>
	</div>
</body>
</html>