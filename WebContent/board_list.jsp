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
<style type="text/css">
.pagination {
	justify-content: center;
}
</style>

</head>
<body>
	<%@include file="header.jsp"%>
		<div id="board_head"><span class="sp-title"><%=location %> 게시판</span></div>
	<div align="center">
		<br>
		<table class="table">
			<tr>
				<td id="count-td" colspan="6" align="right">전체 게시물 수 : ${totalRecord}개
			</tr>
			<tr>
				<th>No.</th>
				<th>말머리</th>
				<th>글 제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th id="writeDate-th">작성 일자</th>
			</tr>
			<c:set var="list" value="${List}" />
			<c:if test="${!empty list}">
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.getBoard_num()}</td>
						<td>${dto.getBoard_head()}</td>
						<td><a href="<%=request.getContextPath() %>/board_content.go?no=${dto.getBoard_num() }&page=${page}">${dto.getBoard_title() }</a></td>
						<td>${dto.getNick() }</td>
						<td>${dto.getBoard_hit()}</td>
						<td id="writeDate-td">${dto.getBoard_regdate().substring(0, 10)}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list}">
				<tr>
					<td id="xboard" colspan="7" align="center">
						<h4>전체 게시물 리스트가 없습니다.</h4>
				</tr>
			</c:if>
		</table>
		<br>
		
		<% 
		if(session.getAttribute("UserId") == null){
		%>
		<h5>로그인 후 이용해주세요.</h5>
		<%
		}else{
		%>
		
		<input id="write_bt" type="button" value="글 쓰기" onclick="location.href='board_write.jsp?location=<%=location %>'">
													
		<%	
			}
		%>
		
		<br>
		<br>
		
		<form method="post"
			action="<%=request.getContextPath() %>/board_search.go?location=<%=location %>">
			<select name="field">
				<option value="head">말머리</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="cont">내용</option>
			</select>
			<input type="text" name="keyword">&nbsp;&nbsp; <input id="search_bt" type="submit" value="검색">
		</form>
		<br>
		<br>
		
		<nav id="pageNo">
		  <ul class="page">
			  <li>
				    <a class="page-link" href="board_list.go?location=<%=location %>&page=1" style="color:#000;">
				    	<span aria-hidden="true">&laquo;</span>
				    </a>
			  <c:if test="${page != 1}">  
				   <li>
				   		<a class="page-link" href="board_list.go?location=<%=location %>&page=${page - 1}" style="color:#000;">
				   			<span aria-hidden="true">&lsaquo;</span>
				   		</a>
				   </li>
			  </c:if>  	   
			  <c:forEach begin="${startBlock}" end="${endBlock}" var="i">
					<c:if test="${i == page}">
						<li class="page-item" aria-current="page">
							<a class="page-link" href="board_list.go?location=<%=location %>&page=${i}" style="color:#000; font-weight: 700;">${i}</a>
						</li>
					</c:if>
					<c:if test="${i != page}">
						<li class="page-item">
							<a class="page-link" href="board_list.go?location=<%=location %>&page=${i}" style="color:#000;">${i}</a>
						</li>
					</c:if>
				</c:forEach>
			  <c:if test="${page != endBlock}"> 
			  <li>
				<a class="page-link" href="board_list.go?location=<%=location %>&page=${page + 1}" style="color:#000;">
			   			<span aria-hidden="true">&rsaquo;</span>
			   	</a>
			  </li>
			  </c:if>
			  <li>
			    <a class="page-item" href="board_list.go?location=<%=location %>&page=${allpage}">
			    	<span aria-hidden="true">&raquo;</span>
			    </a>
			 </li>
		 </ul>
		 </nav>

		<%@include file="footer.jsp"%>
	</div>
</body>
</html>